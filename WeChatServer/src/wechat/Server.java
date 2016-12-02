package wechat;

import model.*;
import model.Contact;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * The server that can be run both as a console application or a GUI
 */
public class Server {
	// a unique ID for each connection
	private static int uniqueId;
	// an ArrayList to keep the list of the Client
	private ArrayList<ClientThread> al;
	// if I am in a GUI
	private ServerLog serverLog;
	// to display time
	private SimpleDateFormat dateFormat;
	// the port number to listen for connection
	private int port;
	// the boolean that will be turned of to stop the server
	private boolean serverStop;

	private ClassPathXmlApplicationContext context;

	/*
	 *  in console
	 */
	public Server(int port) {
		this(port, null);
	}
	
	public Server(int port, ServerLog serverLog) {
		// GUI or not
		this.serverLog = serverLog;
		// the port
		this.port = port;
		// to display hh:mm:ss
		dateFormat = new SimpleDateFormat("HH:mm:ss");
		// ArrayList for the Client list
		al = new ArrayList<ClientThread>();
	}
	
	public void start() {
		serverStop = true;
		/* create socket server and wait for connection requests */
		try 
		{
			// the socket used by the server
			ServerSocket serverSocket = new ServerSocket(port);

			// infinite loop to wait for connections
			while(serverStop) 
			{
				// format message saying we are waiting
				display("Server waiting for Clients on port " + port + ".");
				
				Socket socket = serverSocket.accept();  	// accept connection
				// if I was asked to stop
				if(!serverStop)
					break;
				ClientThread t = new ClientThread(socket);  // make a thread of it
				al.add(t);									// save it in the ArrayList
				t.start();
			}
			// I was asked to stop
			try {
				serverSocket.close();
				for(int i = 0; i < al.size(); ++i) {
					ClientThread tc = al.get(i);
					try {
					tc.sInput.close();
					tc.sOutput.close();
					tc.socket.close();
					}
					catch(IOException ioE) {
						// not much I can do
					}
				}
			}
			catch(Exception e) {
				display("Exception closing the server and clients: " + e);
			}
		}
		// something went bad
		catch (IOException e) {
            String msg = dateFormat.format(new Date()) + " Exception on new ServerSocket: " + e + "\n";
			display(msg);
		}
	}		
    /*
     * For the GUI to stop the server
     */
	protected void stop() {
		serverStop = false;
		// connect to myself as Client to exit statement 
		// Socket socket = serverSocket.accept();
		try {
			new Socket("localhost", port);
		}
		catch(Exception e) {
			// nothing I can really do
		}
	}
	/*
	 * Display an event (not a message) to the console or the GUI
	 */
	private void display(String msg) {
		String time = dateFormat.format(new Date()) + " " + msg;
		if(serverLog == null)
			System.out.println(time);
		else
			serverLog.appendEvent(time + "\n");
	}
	/*
	 *  to broadcast a message to all Clients
	 */
	private synchronized void broadcast(String message) {
		// add HH:mm:ss and \n to the message
		String time = dateFormat.format(new Date());
		String messageLf = time + " " + message + "\n";
		// display message on console or GUI
		if(serverLog == null)
			System.out.print(messageLf);
		else
			serverLog.appendRoom(messageLf);     // append in the room window
		
		// we loop in reverse order in case we would have to remove a Client
		// because it has disconnected

		ChatMessage chatMessage = new ChatMessage(MessageType.GroupMessage, messageLf);

		for(int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
			// try to write to the Client if it fails remove it from the list
			if(!ct.writeObject(chatMessage)) {
				al.remove(i);
				display("Disconnected Client " + ct.username + " removed from list.");
			}
		}
	}


	// for a client who logoff using the LOGOUT message
	synchronized void remove(int id) {
		// scan the array list until we found the Id
		for(int i = 0; i < al.size(); ++i) {
			ClientThread ct = al.get(i);
			// found it
			if(ct.id == id) {
				al.remove(i);
				return;
			}
		}
	}
	
	/*
	 *  To run as a console application just open a console window and: 
	 * > java Server
	 * > java Server portNumber
	 * If the port number is not specified 1500 is used
	 */ 
	public static void main(String[] args) {
		// start server on port 1500 unless a PortNumber is specified

		int portNumber = 1501;
		switch(args.length) {
			case 1:
				try {
					portNumber = Integer.parseInt(args[0]);
				}
				catch(Exception e) {
					System.out.println("Invalid port number.");
					System.out.println("Usage is: > java Server [portNumber]");
					return;
				}
			case 0:
				break;
			default:
				System.out.println("Usage is: > java Server [portNumber]");
				return;
				
		}
		// create a server object and start it
		Server server = new Server(portNumber);
		server.start();
	}

	/** One instance of this thread will run for each client */
	class ClientThread extends Thread {
		// the socket where to listen/talk
		Socket socket;
		ObjectInputStream sInput;
		ObjectOutputStream sOutput;
		// my unique id (easier for deconnection)
		int id;
		// the Username of the Client
		String username;
		// the only type of message a will receive
		ChatMessage cm;
		// the date I connect
		String date;

		// Constructore
		ClientThread(Socket socket) {
			// a unique id
			id = ++uniqueId;
			this.socket = socket;
			/* Creating both Data Stream */
			System.out.println("Thread trying to create Object Input/Output Streams");
			try
			{
				// create output first
				sOutput = new ObjectOutputStream(socket.getOutputStream());
				sInput  = new ObjectInputStream(socket.getInputStream());
				// read the username
				username = (String) sInput.readObject();
				display(username + " just connected.");
			}
			catch (IOException e) {
				display("Exception creating new Input/output Streams: " + e);
				return;
			}
			// have to catch ClassNotFoundException
			// but I read a String, I am sure it will work
			catch (ClassNotFoundException e) {
			}
            date = new Date().toString() + "\n";
		}


		private void register(String message)
		{
			ContactDAO contactDAO = context.getBean(ContactDAO.class);

			String userName = message.trim();

			if(contactDAO.exists(userName)) {
				//writeMsg(userName + "is existing! Please use another user ame");
				writeObject(new ChatMessage(MessageType.RegisterReponse, userName + "is existing! Please use another user ame"));
			}
				else {
				contactDAO.save(new Contact(userName));
			}

		}

		private int login(String userName)
		{
			ContactDAO contactDAO = context.getBean(ContactDAO.class);

			Contact contact = contactDAO.get(userName);
			if(null != contact){
				return contact.getId();
			}
			return 0;
		}

		private List<wechat.Contact> getFriendList(int userId){
			ContactDAO contactDAO = context.getBean(ContactDAO.class);
			return contactDAO.listByUser(userId);
		}


		// what will run forever
		public void run() {
			context = new ClassPathXmlApplicationContext("Spring.xml");

			// to loop until LOGOUT
			boolean serverStop = true;
			while(serverStop) {
				// read a String (which is an object)
				try {
					cm = (ChatMessage) sInput.readObject();
				}
				catch (IOException e) {
					display(username + " Exception reading Streams: " + e);
					break;				
				}
				catch(ClassNotFoundException e2) {
					break;
				}
				// the messaage part of the ChatMessage
				String message = cm.getMessage();

				// Switch on the type of message receive
				switch(cm.getType()) {
				case Register:
					register(message);
					break;
				case GroupMessage:
					broadcast(username + ": " + message);
					break;
				case LogIn:
					ChatMessage chatMessage = new ChatMessage(MessageType.LoginReponse, "");
					int userId = login(message.trim());
					if(userId != 0){
						chatMessage.setStatus(Status.LoginSuccessful);
						chatMessage.setMessage("Login Successfully");
						chatMessage.setFromContactId(userId);
					}
					else{
						chatMessage.setStatus(Status.LoginFailed);
						chatMessage.setMessage("Failed to login user doesn't exist!");
					}
					writeObject(chatMessage);
					break;
				case FriendListRequest:
						List<wechat.Contact> friends = getFriendList(cm.getFromContactId());
						ChatMessage cm1 = new ChatMessage(MessageType.FriendListResponse, "Number of friends: " + friends.size());
						cm1.setContactList(friends);
						writeObject(cm1);

				case LogOut:
					display(username + " disconnected with a LOGOUT message.");
					serverStop = false;
					break;
				case OnlineUsers:
					//writeMsg("List of the users connected at " + dateFormat.format(new Date()) + "\n");
					ChatMessage cm = new ChatMessage(MessageType.GroupMessage, "List of the users connected at " + dateFormat.format(new Date()) + "\n");
					writeObject(cm);
					// scan al the users connected
					for(int i = 0; i < al.size(); ++i) {
						ClientThread ct = al.get(i);
						//writeMsg((i+1) + ") " + ct.username + " since " + ct.date);
						writeObject(new ChatMessage(MessageType.GroupMessage, (i+1) + ") " + ct.username + " since " + ct.date));
					}
					break;
				}
			}
			// remove myself from the arrayList containing the list of the
			// connected Clients
			remove(id);
			close();
		}
		
		// try to close everything
		private void close() {
			// try to close the connection
			try {
				if(sOutput != null) sOutput.close();
			}
			catch(Exception e) {}
			try {
				if(sInput != null) sInput.close();
			}
			catch(Exception e) {};
			try {
				if(socket != null) socket.close();
			}
			catch (Exception e) {}
		}

		/*
		 * Write a String to the Client output stream
		 */
		private boolean writeMsg(String msg) {
			// if Client is still connected send the message to it
			if(!socket.isConnected()) {
				close();
				return false;
			}
			// write the message to the stream
			try {
				sOutput.writeObject(msg);
			}
			// if an error occurs, do not abort just inform the user
			catch(IOException e) {
				display("Error sending message to " + username);
				display(e.toString());
			}
			return true;
		}

		private boolean writeObject(Object msg) {
			// if Client is still connected send the message to it
			if(!socket.isConnected()) {
				close();
				return false;
			}
			// write the message to the stream
			try {
				sOutput.writeObject(msg);
			}
			// if an error occurs, do not abort just inform the user
			catch(IOException e) {
				display("Error sending message to " + username);
				display(e.toString());
			}
			return true;
		}
	}
}

