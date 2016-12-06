package ChatWe;

import javax.swing.*;
import javax.swing.border.LineBorder;

import java.awt.*;
import java.awt.event.*;

/*
 * GUI for the server activity tracking.
 * The message goes to the server and is then propagated to the receiver/group of receivers
 * The logging takes place asynchronously into the db
 * The  ServerLog class is a creator of the server class 
 */
public class ServerLog extends JFrame implements ActionListener, WindowListener {


	// Stop and start buttons to shutdown/start the server
	//Now this doesn't have to be that way.This is just for demo. Closing the gui sholdn't shut down the server.
	private JButton serverStartStop;

	// Text Area for the chat log 
	//text Area for events like logging in, connection request activity, etc
	private JTextArea logSpace, eventSpace;

	// The server port number/right now hardcoded to 1500
	private JTextField SPort;

	//Our instance of server can be generated. This allows us to create multiple servers.
	private Server server;


	// We are passing the hard coded port number 1500
	//Calls the super constructor and creates new jframe with specified title
	ServerLog(int port) {
		super("Server Activity");
		getContentPane().setBackground(Color.BLACK);

		// in the NorthPanel the PortNumber the Start and Stop buttons
		JPanel top = new JPanel();
		JLabel jl = new JLabel("Server Port Number: ");
		jl.setForeground(Color.WHITE);
		top.add(jl);
		SPort = new JTextField("  " + port);
		SPort.setForeground(Color.WHITE);
		SPort.setBackground(Color.BLACK);
		top.add(SPort);
		top.setBackground(Color.BLACK);

		//init server
		server = null;
		//"Start" button
		serverStartStop = new JButton("Start");
		serverStartStop.addActionListener(this);
		serverStartStop.setToolTipText("Start/Stop Server");
		serverStartStop.setForeground(Color.WHITE);
		serverStartStop.setBackground(Color.BLACK);
		serverStartStop.setBorder(new LineBorder(Color.WHITE));
		top.add(serverStartStop);
		add(top, BorderLayout.NORTH);

		// the eventSpace and chatspace
		JPanel center = new JPanel(new GridLayout(2,1));
		logSpace = new JTextArea(80,80);
		logSpace.setEditable(false);
		center.setBackground(Color.BLACK);
		logSpace.setForeground(Color.WHITE);
		logSpace.setBackground(Color.BLACK);
		appendRoom("Logs..... \n");
		JScrollPane jSc1 = new JScrollPane(logSpace);
		jSc1.setBackground(Color.BLACK);
		jSc1.setBorder(new LineBorder(Color.WHITE));
		center.add(jSc1);
		eventSpace = new JTextArea(80,80);
		eventSpace.setEditable(false);
		eventSpace.setForeground(Color.WHITE);
		eventSpace.setBackground(Color.BLACK);
		appendEvent("Events.....\n");
		JScrollPane jSc = new JScrollPane(eventSpace);
		jSc.setBackground(Color.BLACK);
		jSc.setBorder(new LineBorder(Color.WHITE));
		center.add(jSc);	
		add(center);

		// user clicks the close button on the frame
		addWindowListener(this);
		setSize(600, 600);
		setVisible(true);
	}		

	// append message to the two JTextArea
	// position at the end
	void appendRoom(String str) {
		logSpace.append(str);
		logSpace.setCaretPosition(logSpace.getText().length() - 1);
	}
	void appendEvent(String str) {
		eventSpace.append(str);
		eventSpace.setCaretPosition(logSpace.getText().length() - 1);

	}

	// start or stop where clicked
	public void actionPerformed(ActionEvent e) {
		
		// if running we have to stop
		if(server != null) {
			server.stop();
			server = null;
			SPort.setEditable(true);
			serverStartStop.setText("Start");
			return;
		}
		
		// OK start the server	
		int port;
		try {
			port = Integer.parseInt(SPort.getText().trim());
		}
		catch(Exception er) {
			appendEvent("Invalid port number");
			return;
		}

		// ceate a new Server
		server = new Server(port, this);
		
		// and start it as a thread
		new ServerRunning().start();
		serverStartStop.setText("Stop");
		SPort.setEditable(false);
	}

	// entry point to start the Server
	public static void main(String[] arg) {
		
		// start server default port 1500
		new ServerLog(1500);
	}

	/*
	 * If the user click the X button to close the application
	 * I need to close the connection with the server to free the port
	 */
	public void windowClosing(WindowEvent e) {
		// if my Server exist
		if(server != null) {
			try {
				server.stop();			// ask the server to close the conection
			}
			catch(Exception eClose) {
			}
			server = null;
		}
		// dispose the frame
		dispose();
		System.exit(0);
	}
	// I can ignore the other WindowListener method
	public void windowClosed(WindowEvent e) {}
	public void windowOpened(WindowEvent e) {}
	public void windowIconified(WindowEvent e) {}
	public void windowDeiconified(WindowEvent e) {}
	public void windowActivated(WindowEvent e) {}
	public void windowDeactivated(WindowEvent e) {}

	/*
	 * A thread to run the Server
	 */
	class ServerRunning extends Thread {
		public void run() {
			
			// should execute until if fails
			server.start();         
			
			// if the server failed
			serverStartStop.setText("Start");
			SPort.setEditable(true);
			appendEvent("Server crashed\n");
			server = null;
		}
	}

}


