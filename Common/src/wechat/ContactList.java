package wechat;

import javax.print.attribute.standard.MediaSize;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * Created by qingjun wu  11/15/2016.
 */
public class ContactList extends  Contact implements IContactList {
    private LinkedList<Contact> contacts;
    private HashMap<Integer, Contact> _hmContactsId;
    private HashMap<String, Contact> _hmContactsName;
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
        _hmContactsId.put(contact.getId(), contact);
        _hmContactsName.put(contact.getName(), contact);
    }

    public void removeContact(String Name){
        if(_hmContactsName.containsKey(Name))
        {
            contacts.removeIf(a -> a.getName() == Name);
            _hmContactsId.remove(_hmContactsName.get(Name).getId());
            _hmContactsName.remove(Name);
        }
    }

    public boolean containsContact(String Name)
    {
        return _hmContactsName.containsKey(Name);
    }
}
