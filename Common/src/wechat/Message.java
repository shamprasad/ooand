package wechat;

/**
 * Created by qingjun wu on 11/15/2016.
 */
public abstract class Message {
    abstract int getMessageId();
    abstract void setMessageId(int id);
    abstract int getFromContactId();
    abstract void setFromContactId(int id);
    abstract int getToContactId();
    abstract void setToContactId(int id);
    abstract MessageType getMessageType();
    abstract void setMessageType(MessageType type);
    abstract String getMessage();
    abstract void setMessage(String message);
}
