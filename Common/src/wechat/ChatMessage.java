package wechat;

import java.io.*;
import java.util.*;

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
    private int _fromContactId, _toContactId, _messageId, _fromGroupId, _toGroupId;
    private  MessageType _messageType;
    private Status _status;
    private String message;
    private String fromUserName, toUserName;
    private String fromGroupName, toGroupName;
    transient private List<Contact> contactList;
    private String contactListString;
    private String groupListString;
    private String allContactListString;
    private String pendingRequestList;
    // constructor

    ChatMessage(MessageType type, String message) {
        this._messageType = type;
        this.message = message;
    }

    ChatMessage(){}

    // getters
    MessageType getType() {
        return _messageType;
    }

    public int getFromContactId()
    {
        return _fromContactId;
    }

    public void setFromContactId(int id)
    {
        _fromContactId = id;
    }

    public int getToContactId()
    {
        return _toContactId;
    }

    public void setToContactId(int id)
    {
        _toContactId = id;
    }

    public int getMessageId()
    {
        return _messageId;
    }

    public void setMessageId(int id)
    {
        _messageId = id;
    }

    public MessageType getMessageType(){
        return _messageType;
    }

    public void setMessageType(MessageType type)
    {
        _messageType = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String m)
    {
        message = m;
    }
    
    public int getFromGroupId()
    {
    	return this._fromGroupId;
    }
    
    public void setFromGroupId(int id)
    {
    	this._fromGroupId= id;
    }

    public void setToGroupId(int toGroupId){
        this._toGroupId = toGroupId;
    }

    public int getToGroupId(){
        return this._toGroupId;
    }

    public void setFromUserName(String fromUserName){
        this.fromUserName = fromUserName;
    }

    public String getFromUserName(){
        return this.fromUserName;
    }

    public void setToUserName(String toUserName){
        this.toUserName = toUserName;
    }

    public String getToUserName(){
        return this.toUserName;
    }

    public void setFromGroupName(String name){
        this.fromGroupName = name;
    }

    public String getFromGroupName(){
        return this.fromGroupName;
    }

    public void setToGroupName(String name){
        this.toGroupName = name;
    }

    public String getToGroupName(){
        return this.toGroupName;
    }

    public Status getStauts()
    {
        return this._status;
    }

    public void setStatus(Status status)
    {
        this._status = status;
    }

    public void setContactList(List<Contact> contactList){
        contactListString = contactListToString(contactList);
    }

    public List<Contact> getContactList(){
       return getContactListFromString(contactListString);
    }

    public void setGroupList(List<Contact> groupList){
        groupListString = contactListToString(groupList);
    }

    public List<Contact> getGroupList(){
        return getContactListFromString(groupListString);
    }

    public void setAllContactList(List<Contact> contactList){
        allContactListString = contactListToString(contactList);
    }

    public List<Contact> getAllContactList(){
        return getContactListFromString(allContactListString);
    }

    public void setPendingRequestList(List<Contact> contactList){
        this.pendingRequestList = contactListToString(contactList);
    }

    public List<Contact> getPendingRequestList(){
        return getContactListFromString(this.pendingRequestList);
    }

    public String contactListToString(List<Contact> contactList) {
        String res = "";
        for (Contact c : contactList) {
            res += c.getId() + "," + c.getName() + ";";
        }
        return res;
    }

    public List<Contact> getContactListFromString(String listString){
        List<Contact> contactList = new ArrayList<Contact>();
        if(listString != null && !listString.isEmpty()){
            String[] contacts = listString.split(";");
            for(String c : contacts){
                String[] t = c.split(",");
                contactList.add(new Contact(Integer.parseInt(t[0]), t[1]));
            }
        }
        return contactList;
    }
}
