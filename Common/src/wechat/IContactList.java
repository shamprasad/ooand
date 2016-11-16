package wechat;

/**
 * Created by ek2zqun on 11/16/2016.
 */
public interface IContactList {
    void addContact(Contact contact);
    void removeContact(String name);
    boolean containsContact(String name);
}
