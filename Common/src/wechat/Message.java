package wechat;

/**
 * Created by qingjun wu on 11/15/2016.
 */
public abstract class Message {
    public abstract int getMessageId();
    public abstract void setMessageId(int id);
    public abstract int getFromContactId();
    public abstract void setFromContactId(int id);
    public abstract int getToContactId();
    public abstract void setToContactId(int id);
    public abstract MessageType getMessageType();
    public abstract void setMessageType(MessageType type);
    public abstract String getMessage();
    public abstract void setMessage(String message);
    public abstract int getFromGroupId();
    public abstract void setFromGroupId(int id);
    public abstract int getToGroupId();
    public abstract void setToGroupId(int id);
    public abstract void setFromGroupName(String name);
    public abstract String getFromGroupName();
    public abstract void setToGroupName(String name);
    public abstract String getToGroupName();
}
