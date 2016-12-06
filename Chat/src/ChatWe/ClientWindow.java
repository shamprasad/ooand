package ChatWe;

import dao.RegistrationDetDAO;
import dao.UserContactDao;
import dao.UserDao;
import dao.UserGroupsDao;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;


/*
 * The Client with its GUI
 */
public class ClientWindow extends JFrame implements ActionListener {

	// will first hold "Username:", later on "Enter message"
	private JLabel label, jl, jl1;
	// to hold the Username and later on the messages
	private JTextField textField, textField1, textField2, textField3, textField4, chatMessage, groupcreate;
	// to hold the server address an the port number
	private JTextField ServerAddress, ServerPort, SearchA;
	// to Logout and get the list of the users
	private JButton login, logout, register, searchB, addB,addBG, startchat, startgchat, groupcreateb, refresh;
	// for the chat room
	private JTextArea TextArea;
	// if it is for connection
	private boolean connected;
	// the Client object
	private Client client;
	// the default port number
	private int defaultPort;
	private String defaultHost,selecteduser,selectedguser;
	private JPanel serverAndPort, northPanel, southPanel, southPanel0, southPanel10, 
	southPanel1, southPanel2, southPanel3,southPanel11, searchP1, searchP2, southPanel12;
	private int gchat = 0;

	private JComboBox<String> searchR, users, groups;

	// Constructor connection receiving a socket number
	ClientWindow(String host, int port) {

		//JFRAME
		super("Chat Client");

		defaultPort = port;
		defaultHost = host;

		//So the layout starts here
		// I have divided the layout into north, south and center panel. Eaach having there own grid
		// The NorthPanel with:
		northPanel = new JPanel(new GridLayout(3,1));


		// the server name anmd the port number
		serverAndPort = new JPanel(new GridLayout(1,5, 1, 3));


		// the two JTextField with default value for server address and port number
		ServerAddress = new JTextField(host);


		ServerPort = new JTextField("" + port);



		jl = new JLabel("Server Address:  ");




		jl1 = new JLabel("Server Port:  ");

		northPanel.setBackground(Color.BLACK);

		serverAndPort.setBackground(Color.BLACK);

		ServerAddress.setBackground(Color.BLACK);
		ServerAddress.setForeground(Color.WHITE);

		ServerPort.setBackground(Color.BLACK);
		ServerPort.setForeground(Color.WHITE);
		ServerPort.setHorizontalAlignment(SwingConstants.CENTER);

		jl.setForeground(Color.WHITE);

		jl1.setForeground(Color.WHITE);

		serverAndPort.add(jl);
		serverAndPort.add(ServerAddress);

		serverAndPort.add(new JLabel(""));

		serverAndPort.add(jl1);
		serverAndPort.add(ServerPort);


		// adds the Server an port field to the GUI
		northPanel.add(serverAndPort);



		searchP1 = new JPanel(new GridLayout(1,2));


		searchP2= new JPanel(new GridLayout(1,3));



		SearchA = new JTextField("Search");
		SearchA.setBackground(Color.BLACK);
		SearchA.setForeground(Color.WHITE);

		searchB = new JButton("Search");
		searchB.setBackground(Color.BLACK);
		searchB.setForeground(Color.WHITE);
		searchB.setBorder(new LineBorder(Color.WHITE));
		searchB.addActionListener(this);

		searchP1.add(SearchA);
		searchP1.add(searchB);




		addB = new JButton("Add User");
		addB.setBackground(Color.BLACK);
		addB.setForeground(Color.WHITE);
		addB.setBorder(new LineBorder(Color.WHITE));
		addB.addActionListener(this);
		
		refresh = new JButton("refresh");
		refresh.setBackground(Color.BLACK);
		refresh.setForeground(Color.WHITE);
		refresh.setBorder(new LineBorder(Color.WHITE));
		refresh.addActionListener(this);

		addBG = new JButton("Add User to Group");
		addBG.setBackground(Color.BLACK);
		addBG.setForeground(Color.WHITE);
		addBG.setBorder(new LineBorder(Color.WHITE));
		addBG.addActionListener(this);

		searchR = new JComboBox<String>();
		searchR.setBackground(Color.BLACK);
		searchR.setForeground(Color.WHITE);
		searchR.setBorder(new LineBorder(Color.WHITE));
		searchR.addActionListener(this);


		searchP2.add(searchR);
		searchP2.add(addB);
		searchP2.add(addBG);

		searchP1.setVisible(false);
		searchP2.setVisible(false);
		northPanel.add(searchP1);
		northPanel.add(searchP2);


		add(northPanel, BorderLayout.NORTH);

		// The CenterPanel which is the chat room
		TextArea = new JTextArea("Welcome\n", 80, 80);
		TextArea.setBackground(Color.BLACK);
		TextArea.setForeground(Color.WHITE);

		//will be empty, but as soon 
		//as user clicks on a user we need to display the most recent message
		JPanel centerPanel = new JPanel(new GridLayout(1,1));
		centerPanel.setBackground(Color.BLACK);
		centerPanel.add(new JScrollPane(TextArea));
		TextArea.setEditable(false);


		add(centerPanel, BorderLayout.CENTER);


		southPanel = new JPanel(new GridLayout(1,4));
		southPanel.setBackground(Color.BLACK);
		southPanel.setForeground(Color.WHITE);

		southPanel0 = new JPanel(new GridLayout(7,1) );
		southPanel0.setBackground(Color.BLACK);
		southPanel1 = new JPanel(new GridLayout(1,2));
		southPanel11 = new JPanel(new GridLayout(1,2));
		southPanel11.setBackground(Color.BLACK);
		southPanel2 = new JPanel(new GridLayout(1,3));
		southPanel12 = new JPanel(new GridLayout(1,1));
		southPanel12.add(refresh);
		southPanel12.setVisible(false);

		southPanel3 = new JPanel(new GridLayout(1,1));

		southPanel10 = new JPanel(new GridLayout(1,4));

		login = new JButton("Login");
		login.setBackground(Color.BLACK);
		login.setForeground(Color.WHITE);
		login.setBorder(new LineBorder(Color.WHITE));
		login.addActionListener(this);

		register = new JButton("Register");
		register.setBackground(Color.BLACK);
		register.setForeground(Color.WHITE);
		register.setBorder(new LineBorder(Color.WHITE));
		register.addActionListener(this);


		logout = new JButton("Logout");
		logout.setBackground(Color.BLACK);
		logout.setForeground(Color.WHITE);
		logout.setBorder(new LineBorder(Color.WHITE));
		logout.addActionListener(this);
		logout.setEnabled(false);		// you have to login before being able to logout


		chatMessage = new JTextField("Chat Message");
		chatMessage.setBackground(Color.BLACK);
		chatMessage.setForeground(Color.WHITE);
		chatMessage.addActionListener(this);
		southPanel3.add(chatMessage);

		groupcreate = new JTextField("group name");
		groupcreate.setBackground(Color.BLACK);
		groupcreate.setForeground(Color.WHITE);
		southPanel11.add(groupcreate);

		groupcreateb = new JButton("create group");
		groupcreateb.setBackground(Color.BLACK);
		groupcreateb.setForeground(Color.WHITE);
		groupcreateb.setBorder(new LineBorder(Color.WHITE));
		groupcreateb.addActionListener(this);
		southPanel11.add(groupcreateb);


		textField = new JTextField("User Name");
		textField.setBackground(Color.BLACK);
		textField.setForeground(Color.WHITE);
		southPanel1.add(textField);

		textField1 = new JTextField("Password");
		textField1.setBackground(Color.BLACK);
		textField1.setForeground(Color.WHITE);
		southPanel1.add(textField1);


		textField2 = new JTextField("email");
		textField2.setBackground(Color.BLACK);
		textField2.setForeground(Color.WHITE);
		southPanel2.add(textField2);

		textField3 = new JTextField("phone");
		textField3.setBackground(Color.BLACK);
		textField3.setForeground(Color.WHITE);
		southPanel2.add(textField3);

		textField4 = new JTextField("full name");
		textField4.setBackground(Color.BLACK);
		textField4.setForeground(Color.WHITE);
		southPanel2.add(textField4);


		southPanel.add(login);
		southPanel.add(register);
		southPanel.add(logout);



		startchat = new JButton("Chat");
		startchat.setBackground(Color.BLACK);
		startchat.setForeground(Color.WHITE);
		startchat.setBorder(new LineBorder(Color.WHITE));
		startchat.addActionListener(this);


		users = new JComboBox<String>();
		users.setBackground(Color.BLACK);
		users.setForeground(Color.WHITE);
		users.setBorder(new LineBorder(Color.WHITE));
		users.addActionListener(this);
		southPanel10.add(users);
		southPanel10.add(startchat);

		startgchat = new JButton("GroupChat");
		startgchat.setBackground(Color.BLACK);
		startgchat.setForeground(Color.WHITE);

		startgchat.addActionListener(this);


		groups = new JComboBox<String>();
		groups.setBackground(Color.BLACK);
		groups.setForeground(Color.WHITE);
		groups.setBorder(new LineBorder(Color.WHITE));
		groups.addActionListener(this);
		
		
		southPanel10.add(groups);
		southPanel10.add(startgchat);
		southPanel12.setBackground(Color.BLACK);
		southPanel0.add(southPanel10);
		southPanel3.setVisible(false);
		southPanel0.add(southPanel3);
		southPanel11.setVisible(false);
		southPanel0.add(southPanel11);
		southPanel0.add(southPanel12);
		southPanel0.add(southPanel);
		southPanel10.setVisible(false);
		southPanel1.setVisible(false);
		southPanel2.setVisible(false);
		southPanel0.add(southPanel1);
		southPanel0.add(southPanel2);

		add(southPanel0, BorderLayout.SOUTH);


		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 600);
		setVisible(true);
		textField.requestFocus();

	}

	void getsearch(String s)
	{
		searchR.setEditable(true);
		for(int i=0;i<searchR.getItemCount();i++)
		{
			searchR.removeItemAt(i);;
		}
		UserDao ud = new UserDao();
		String ss[] = ud.toStringSearchUser(s);
		if(ss!=null)
		{
			for(int i=0;i<ss.length;i++)
			{
				searchR.addItem(ss[i]);
			}

		}

	}

	void getContact(String User)
	{
		users.setEditable(true);
		for(int i=0;i<users.getItemCount();i++)
		{
			users.removeItemAt(i);;
		}
		UserContactDao ud = new UserContactDao();
		String ss[] = ud.toStringUsersUser(User);
		if(ss!=null)
		{
			for(int i=0;i<ss.length;i++)
			{
				users.addItem(ss[i]);
			}

		}

	}


	void getusergroups(String User)
	{
		groups.setEditable(true);
		for(int i=0;i<groups.getItemCount();i++)
		{
			groups.removeItemAt(i);;
		}
		UserGroupsDao ud = new UserGroupsDao();
		String ss[] = ud.toStringUserGroups(User);
		if(ss!=null)
		{
			for(int i=0;i<ss.length;i++)
			{
				groups.addItem(ss[i]);
			}

		}

	}

	// called by the Client to append text in the TextArea 
	void append(String str) {
		TextArea.append(str);
		TextArea.setCaretPosition(TextArea.getText().length() - 1);
	}
	// called by the GUI is the connection failed
	// we reset our buttons, label, text field
	void connectionFailed() {
		login.setEnabled(true);
		logout.setEnabled(false);

		label.setText("Enter your username below");
		textField.setText("Connection Failed");
		// reset port number and host name as a construction time
		ServerPort.setText("" + defaultPort);
		ServerAddress.setText(defaultHost);
		// let the user change them
		ServerAddress.setEditable(false);
		ServerPort.setEditable(false);
		// don't react to a <CR> after the user name
		textField.removeActionListener(this);
		connected = false;
	}

	/*
	 * Button or JTextField clicked
	 */
	public void actionPerformed(ActionEvent e) {
		Object o = e.getSource();
		// if it is the Logout button
		if(o == logout) {
			client.sendMessage(new ChatMessage(ChatMessage.LOGOUT, "","",null,""));
			textField.setVisible(true);
			textField1.setVisible(true);
			textField2.setVisible(true);
			textField3.setVisible(true);
			textField4.setVisible(true);
			southPanel1.setVisible(false);
			southPanel3.setVisible(false);
			southPanel2.setVisible(false);
			southPanel0.setVisible(false);
			login.setText("Login");
			register.setText("Register");
			return;
		}
		// if it the who is in button


		else if(o == addB) {
			UserContactDao ucd = new UserContactDao();	
			String s= textField.getText().trim();

			ucd.insertUserContact(s, selecteduser);
			getContact(s);
			return;
		}
		else if(o == addBG) {
			UserContactDao ucd = new UserContactDao();	
			String s= textField.getText().trim();

			ucd.insertUserContactG(selectedguser, selecteduser);

			return;
		}

		else if(o == searchR) {

			selecteduser = searchR.getSelectedItem().toString();


		}
		else if(o == refresh) {
			String username = textField.getText().trim();
			getContact(username);
			getusergroups(username);
			//getsearch(SearchA.getText().trim());


		}

		else if(o == users) {

			selecteduser = users.getSelectedItem().toString();


		}
		else if(o == groups) {

			selectedguser = groups.getSelectedItem().toString();


		}

		else if(o == searchB) {
			getsearch(SearchA.getText().trim());
			return;
		}

		else if(o == startchat) {
			TextArea.setText("");
			chatMessage.setEnabled(true);
			chatMessage.setText("Type message and press enter to send");
			return;
		}
		else if(o == groupcreateb) {
			UserGroupsDao ug = new UserGroupsDao();
			String username = textField.getText().trim();
			String gname = groupcreate.getText().trim();
			ug.insertUser(username, gname);
			getusergroups(username);
			return;
		}

		else if(o == startgchat) {
			gchat = 1;
			TextArea.setText("");	
			chatMessage.setEnabled(true);
			chatMessage.setText("Type group message and press enter to send");
			return;
		}


		// ok it is coming from the JTextField
		else if(connected && o!= searchR && o != users && o!=groups) {
			// just have to send the message
			String username = textField.getText().trim();
			if(gchat==0)
			{
				client.sendMessage(new ChatMessage(ChatMessage.MESSAGE, chatMessage.getText(),selecteduser,null,username));	
			}
			else
			{
				UserGroupsDao ud = new UserGroupsDao();
				String ss[] = ud.toStringgroupusers(selectedguser);

				client.sendMessage(new ChatMessage(ChatMessage.GroupMessage, chatMessage.getText(),null,ss,username));	
			}
			chatMessage.setText("");
			return;
		}


		else if(o == login) {

			if(login.getText() == "Login")
			{
				login.setText("Submit");
				southPanel1.setVisible(true);
				register.setText("Register");
				southPanel2.setVisible(false);
			}
			else
			{
				// ok it is a connection request
				String username = textField.getText().trim();
				// empty username ignore it
				if(username.length() == 0)
					return;
				String password = textField1.getText().trim();
				if(password.length() == 0)
					return;
				// empty serverAddress ignore it
				String server = ServerAddress.getText().trim();
				if(server.length() == 0)
					return;
				// empty or invalid port number, ignore it
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

				UserDao d = new UserDao();

				if(d.listEvents(username, password)>0)
				{

					getContact(username);
					getusergroups(username);
					// try creating a new Client with GUI
					client = new Client(server, port, username, this);
					// test if we can start the Client
					if(!client.start()) 
						return;

					connected = true;
					southPanel3.setVisible(true);
					southPanel11.setVisible(true);
					southPanel12.setVisible(true);
					chatMessage.setText("Select a user and click on chat");
					chatMessage.setEnabled(false);
					// disable login button
					login.setEnabled(false);
					register.setEnabled(false);
					// enable the 2 buttons
					logout.setEnabled(true);

					// disable the Server and Port JTextField
					ServerAddress.setEditable(false);
					ServerPort.setEditable(false);

					textField1.setText("");
					textField2.setText("");
					textField3.setText("");
					textField4.setText("");
					textField.setVisible(false);
					textField1.setVisible(false);
					textField2.setVisible(false);
					textField3.setVisible(false);
					textField4.setVisible(false);			
					searchP1.setVisible(true);
					searchP2.setVisible(true);
					southPanel1.setVisible(false);
					southPanel10.setVisible(true);
					southPanel3.setVisible(true);
					TextArea.setText(username);

				}
			}
		}

		else if(o == register) {
			if(register.getText() == "Register")
			{
				register.setText("Submit");
				southPanel1.setVisible(true);
				southPanel2.setVisible(true);
				login.setText("Login");
			}
			else
			{

				String username = textField.getText().trim();

				if(username.length() == 0)
					return;

				String password = textField1.getText().trim();

				if(password.length() == 0)
					return;

				String email = textField2.getText().trim();

				if(email.length() == 0)
					return;

				String phone = textField3.getText().trim();

				if(phone.length() == 0)
					return;

				String fullname = textField4.getText().trim();

				if(fullname.length() == 0)
					return;

				String server = ServerAddress.getText().trim();
				if(server.length() == 0)
					return;

				String portNumber = ServerPort.getText().trim();
				if(portNumber.length() == 0)
					return;
				int port = 0;
				try {
					port = Integer.parseInt(portNumber);
				}
				catch(Exception en) {
					return;  
				}

				RegistrationDetDAO d = new RegistrationDetDAO();

				try {
					d.insertUser(username,email,fullname,phone,password);
				}
				catch(Exception en) {
					return;  
				}
				southPanel12.setVisible(true);
				southPanel1.setVisible(false);
				southPanel2.setVisible(false);
				southPanel10.setVisible(true);
				southPanel3.setVisible(true);
				searchP1.setVisible(true);
				searchP2.setVisible(true);
				// try creating a new Client with GUI
				client = new Client(server, port, username, this);
				// test if we can start the Client
				if(!client.start()) 
					return;

				connected = true;
				chatMessage.setVisible(true);
				// disable login button
				login.setEnabled(false);
				register.setEnabled(false);
				// enable the 2 buttons
				logout.setEnabled(true);

				// disable the Server and Port JTextField
				ServerAddress.setEditable(false);
				ServerPort.setEditable(false);
				TextArea.setText(username);
				southPanel11.setVisible(true);
			}
		}
	}

	// to start the whole thing the server
	public static void main(String[] args) {
		new ClientWindow("localhost", 1500);
	}

}

/*
 * The above code is from http://www.coc.qu.edu.sa/en/dr.husam.alhamad/documenthusam/gp/samples/332chattingfinalreport.pdf and 
 * http://www.dreamincode.net/forums/topic/259777-a-simple-chat-program-with-clientserver-gui-optional/
 * We have modified the methods to enable:
 * 1. Peer to peer chat
 * 2. group chat
 * 3. functionalities like login, logout, search, add user, add group user, etc
 * 
 * Methods modified/added:
 * 1. actionPerformed()
 * 2. constructor for UI
 * 3. getsearch()
 * 4. getcontact()
 * 5. getusersgroup()
 */
