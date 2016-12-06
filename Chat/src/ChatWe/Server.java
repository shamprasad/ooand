package ChatWe;

import java.io.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * The server that can be run both as a console application or a GUI
 */
public class Server {
	// a unique ID for each connection
	// this is being primarily used by the server to decide which client the message is to be sent
	private static int uniqueId;
	// an ArrayList to keep the list of the Client
	// this is my observer class, it contains all the listeners on the server or rather all the observers
	private ArrayList<ClientThread> al;
	// for the graphics
	private ServerLog serverLog;
	// to display time
	private SimpleDateFormat dateFormat;
	// the port number to listen for connection
	private int port;
	// the boolean that will be turned of to stop the server
	private boolean serverStop;


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
				//add the observer t into the list of observers
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

	//notify all the observers
	//basically this is where it is decided that who is the receiver of the message
	//so if I am chatting with another user and i send message then only the other guy should receive
	//In case of group chat their are multiple valid observers
	//In case of peer to peer chat we have single valid observer
	private synchronized void broadcast(String message, String target, String targets[], String so) {
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
		for(int i = al.size(); --i >= 0;) {
			ClientThread ct = al.get(i);
			// try to write to the Client if it fails remove it from the list
			String h =ct.username;
			if(target!=null)
			{
				if(target.equals(h) || so.equals(h))
				{
					if(!ct.writeMsg(messageLf)) {
						al.remove(i);
						display("Disconnected Client " + h + " removed from list.");
					}
				}
			}
			else
			{
				if(find(targets,h)==1 || h.equals(so) )
				{
					if(!ct.writeMsg(messageLf)) {
						al.remove(i);
						display("Disconnected Client " + h + " removed from list.");
					}
				}
			}
		}
	}


	int find(String ss[], String s)
	{
		int r =0;
		for(int i=0;i<ss.length;i++)
		{
			if(ss[i].equals(s))
			{
				r=1;
			}

		}
		return r;
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
		int portNumber = 1500;
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

		// what will run forever
		public void run() {
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

				case ChatMessage.MESSAGE:
					broadcast(username + ": " + message,cm.getTargetC(),null, cm.getsourceC());
					break;

				case ChatMessage.LOGOUT:
					display(username + " disconnected with a LOGOUT message.");
					serverStop = false;
					break;

				case ChatMessage.GroupMessage:
					broadcast(username + ": " + message,cm.getTargetC(),cm.getTargetG(),"");
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
	}
}

