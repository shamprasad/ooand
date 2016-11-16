package wechat;

/**
 * Created by qingjun wu on 11/15/2016.
 */
public abstract class Message {
    abstract MessageType getMessageType();
    abstract void setMessageType(MessageType type);
    abstract String getMessage();
    abstract void setMessage(String message);
}
