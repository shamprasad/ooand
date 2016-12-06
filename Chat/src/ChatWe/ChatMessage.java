package ChatWe;

import java.io.*;
/*
 * This class defines the different type of messages that will be exchanged between the
 * Clients and the Server.
 */
public class ChatMessage implements Serializable {

	// The different types of message sent by the Client
	// WHOISIN to receive the list of the users connected
	// MESSAGE an ordinary message
	// LOGOUT to disconnect from the Server
	static final int MESSAGE = 1, LOGOUT = 2, GroupMessage = 3;
	private int type;
	private String message;
	private String targetC;
	private String sourceC;
	private String targetG[];


	ChatMessage(int type, String message, String u, String[] s, String so) {
		this.type = type;
		this.message = message;
		this.targetC = u;
		this.targetG = s;
		this.sourceC = so;
	}

	// getters
	int getType() {
		return type;
	}
	String getMessage() {
		return message;
	}
	String getTargetC()
	{
		return targetC;
	}

	String[] getTargetG()
	{
		return targetG;
	}
	String getsourceC()
	{
		return sourceC;
	}
}


/*
 * The above code is from http://www.coc.qu.edu.sa/en/dr.husam.alhamad/documenthusam/gp/samples/332chattingfinalreport.pdf
 * We have modified the methods to enable:
 * 
 * Methods modified/added:
 * 1. added getters and setters for groupchat message
 * 2. added groupchat enum
 */