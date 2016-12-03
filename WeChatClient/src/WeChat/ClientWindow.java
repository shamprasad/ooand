package wechat;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


/*
 * The Client with its GUI
 */
public class ClientWindow extends JFrame implements ActionListener, IWeChat {

	// will first hold "Username:", later on "Enter message"
	private JLabel label;
	// to hold the Username and later on the messages
	private JTextField textField;
	// to hold the server address an the port number
	private JTextField ServerAddress, ServerPort;
	// to Logout and get the list of the users
	private JButton login, logout, OnlineUsers, register;
	// for the chat room
	private JTextArea TextArea;
	// if it is for connection
	private boolean connected;
	// the Client object
	private Client client;
	// the default port number
	private int defaultPort;
	private String defaultHost;

	// Constructor connection receiving a socket number
	ClientWindow(String host, int port) {

		super("Chat Client");
		defaultPort = port;
		defaultHost = host;
		
		// The NorthPanel with:
		JPanel northPanel = new JPanel(new GridLayout(3,1));
		northPanel.setBackground(Color.BLACK);
		// the server name anmd the port number
		JPanel serverAndPort = new JPanel(new GridLayout(1,5, 1, 3));
		// the two JTextField with default value for server address and port number
		ServerAddress = new JTextField(host);
		ServerPort = new JTextField("" + port);
		ServerPort.setBackground(Color.BLACK);
		ServerPort.setForeground(Color.WHITE);
		ServerAddress.setBackground(Color.BLACK);
		ServerAddress.setForeground(Color.WHITE);
		serverAndPort.setBackground(Color.BLACK);
		ServerPort.setHorizontalAlignment(SwingConstants.RIGHT);
		JLabel jl = new JLabel("Server Address:  ");
		jl.setForeground(Color.WHITE);
		serverAndPort.add(jl);
		serverAndPort.add(ServerAddress);
		JLabel jl1 = new JLabel("Server Port:  ");
		jl1.setForeground(Color.WHITE);
		serverAndPort.add(jl1);
		serverAndPort.add(ServerPort);
		serverAndPort.add(new JLabel(""));
		// adds the Server an port field to the GUI
		northPanel.add(serverAndPort);

		// the Label and the TextField
		label = new JLabel("Enter your username below", SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setBorder(new LineBorder(Color.WHITE));
		northPanel.add(label);
		textField = new JTextField("User Name: No authentication till now ");
		textField.setBackground(Color.BLACK);
		textField.setForeground(Color.WHITE);
		northPanel.add(textField);
		add(northPanel, BorderLayout.NORTH);

		// The CenterPanel which is the chat room
		TextArea = new JTextArea("Welcome\n", 80, 80);
		TextArea.setBackground(Color.BLACK);
		TextArea.setForeground(Color.WHITE);
		//will be empty, but as soon 
		//as user clicks on a user we need to display the most recent message
		JPanel centerPanel = new JPanel(new GridLayout(1,1));
		centerPanel.add(new JScrollPane(TextArea));
		TextArea.setEditable(false);
		add(centerPanel, BorderLayout.CENTER);

		// the 3 buttons
		login = new JButton("Login");
		login.setBackground(Color.BLACK);
		login.setForeground(Color.WHITE);
		login.setBorder(new LineBorder(Color.WHITE));
		login.addActionListener(this);
		logout = new JButton("Logout");
		logout.setBackground(Color.BLACK);
		logout.setForeground(Color.WHITE);
		logout.setBorder(new LineBorder(Color.WHITE));
		logout.addActionListener(this);
		logout.setEnabled(false);		// you have to login before being able to logout
		OnlineUsers = new JButton("Who is in");//this should be used to display the online users, all right now
		//needs to be restricted to the users contact list only
		//backend not implemented yet
		//this is going to take some time
		OnlineUsers.addActionListener(this);
		OnlineUsers.setEnabled(false);		// you have to login before being able to Who is in

		register = new JButton("Register");
		register.setBackground(Color.BLACK);
		register.setForeground(Color.WHITE);
		register.addActionListener(this);
		register.setEnabled(true);

		JPanel southPanel = new JPanel();
		southPanel.add(login);
		southPanel.add(logout);
		southPanel.add(OnlineUsers);
		southPanel.add(register);
		add(southPanel, BorderLayout.SOUTH);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
		textField.requestFocus();

	}

	// called by the Client to append text in the TextArea 
	public void append(String str) {
		TextArea.append(str);
		TextArea.setCaretPosition(TextArea.getText().length() - 1);
	}
	// called by the GUI is the connection failed
	// we reset our buttons, label, text field
	public void connectionFailed() {
		login.setEnabled(true);
		logout.setEnabled(false);
		OnlineUsers.setEnabled(false);
		label.setText("Enter your username below");
		textField.setText("Anonymous");
		// reset port number and host name as a construction time
		ServerPort.setText("" + defaultPort);
		ServerAddress.setText(defaultHost);
		// let the user change them
		ServerAddress.setEditable(true);
		ServerPort.setEditable(true);
		// don't react to a <CR> after the user name
		textField.removeActionListener(this);
		connected = false;
	}


	private void login()
	{
		// try creating a new Client with GUI
		//client = new Client(server, port, username, this);
		// test if we can start the Client
		//if(!client.start())
		//	return;
		textField.setText("");
		label.setText("Enter your message below");
		connected = true;

		// disable login button
		login.setEnabled(false);
		// enable the 2 buttons
		logout.setEnabled(true);
		login.addActionListener(this);

		register.setEnabled(false);

		OnlineUsers.setEnabled(true);
		// disable the Server and Port JTextField
		ServerAddress.setEditable(false);
		ServerPort.setEditable(false);
		// Action listener for when the user enter a message
		textField.addActionListener(this);
	}

	/*
	* Button or JTextField clicked
	*/
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();

		if(!connected && (o == login || o == register)){
			// ok it is a connection request
			String username = textField.getText().trim();

			if(username.length() == 0) return ;

			// empty serverAddress ignore it
			String server = ServerAddress.getText().trim();
			if(server.length() == 0)
				return;
			// empty or invalid port numer, ignore it
			String portNumber = ServerPort.getText().trim();
			if(portNumber.length() == 0)
				return;
			int port = 0;
			try {
				port = Integer.parseInt(portNumber);
			}
			catch(Exception en) {
				return;   // nothing I can do if port number is not valid
			}
			client = new Client(server, port, username, this);
			if(!client.start()) {
				return ;
			}
			connected = true;
		}

		if(o == login) {
			login();
			client.sendMessage(new ChatMessage(MessageType.LogIn, client.getUserName()));
			return;
		}
		// if it is the Logout button
		if(o == logout) {
			client.sendMessage(new ChatMessage(MessageType.LogOut, ""));
			connectionFailed();
			if(client != null){
				client.disconnect();
			}
			connected = false;
			login.setEnabled(true);
			register.setEnabled(true);
			return;
		}
		// if it the who is in button
		if(o == OnlineUsers) {
			client.sendMessage(new ChatMessage(MessageType.OnlineUsers, ""));
			return;
		}

		if(o == register)
		{
			client.sendMessage(new ChatMessage(MessageType.Register, textField.getText()));
			login();
			return ;
		}

		// ok it is coming from the JTextField
		if(connected) {
			// just have to send the message
			client.sendMessage(new ChatMessage(MessageType.GroupMessage, textField.getText()));
			textField.setText("");
			return;
		}
		


	}

	public void loginSuccessful(int userId){}
	public void setContactList(java.util.List<wechat.Contact> contactList){}
	public void receiveMessage(ChatMessage chatMessage){}

	// to start the whole thing the server
	public static void main(String[] args) {
		new ClientWindow("localhost", 1500);
	}

}

