package wechat;

import java.util.LinkedList;

/**
 * Created by qingjun wuon 11/15/2016.
 */
public class ContactList extends  Contact {
    private LinkedList<Contact> contacts;
    private String _name;
    private int _id;

    public int getId(){
        return _id;

    }

    public void setId(int id)
    {
        _id = id;
    }

    public String getName()
    {
        return _name;
    }

    public void setName(String name)
    {
        _name = name;
    }

    public void pushMessage() {
        for (Contact c: contacts
             ) {
            c.pushMessage();
        }
    }

    public void pullMessage() {
        for(Contact c: contacts){
            c.pullMessage();
        }
    }

    public void addContact(Contact contact){
        contacts.add(contact);
    }

    public void removeContact(String Name){
        contacts.removeIf(a -> a.getName() == Name);
    }
}
