package wechat;

import java.io.*;
/*
 * This class defines the different type of messages that will be exchanged between the
 * Clients and the Server.
 * When talking from a Java Client to a Java Server a lot easier to pass Java objects, no
 * need to count bytes or to wait for a line feed at the end of the frame
 */
public class ChatMessage extends Message implements Serializable {

    // The different types of message sent by the Client
    // WHOISIN to receive the list of the users connected
    // MESSAGE an ordinary message
    // LOGOUT to disconnect from the Server
    static final int OnlineUsers = 0, MESSAGE = 1, LOGOUT = 2;
    private int type;
    MessageType _messageType;
    private String message;

    // constructor
    ChatMessage(int type, String message) {
        this.type = type;
        this.message = message;
    }

    // getters
    int getType() {
        return type;
    }

    MessageType getMessageType(){
        return _messageType;
    }

    void setMessageType(MessageType type)
    {
        _messageType = type;
    }

    String getMessage() {
        return message;
    }

    void setMessage(String m)
    {
        message = m;
    }
}
