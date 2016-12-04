package wechat;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by ek2zqun on 11/30/2016.
 */
public class MainTab extends JPanel implements ActionListener, IWeChat {
    private WeChatFrame frame;
    private JList contacts;
    private DefaultListModel model = new DefaultListModel();

    public MainTab(WeChatFrame frame){
        this.frame = frame;
  //      model.addElement(new ContactItem(1, "User1"));
  //      model.addElement(new ContactItem(2, "User2"));


        contacts = new JList(model);
        contacts.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList lsm = (JList)e.getSource();
                int i1 = lsm.getLeadSelectionIndex();
                ContactItem item = (ContactItem)lsm.getModel().getElementAt(i1);
                frame.addChatTab(item.getId(), item.getName(), item.getType());
            }
        });

        this.add(contacts);
        this.frame.getTabbedPane().add(this, "Main");
    }


    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "ContactListChanged"){
            List<List<Contact>> lists = this.frame.getContactList();
            setContactList(lists.get(0), lists.get(1));
        }
        this.updateUI();
    }


    public void append(String message){
    }

    public void connectionFailed(){
    }

    public void loginSuccessful(int userId){

    }

    public void setContactList(List<Contact> contactList, List<Contact> groupList){
        for(wechat.Contact c: contactList){
            model.addElement(new ContactItem(c.getId(), c.getName(), 1));
        }
        for(wechat.Contact c: groupList){
            model.addElement(new ContactItem(c.getId(), c.getName(), 2));
        }
    }

    public void receiveIndividualMessage(ChatMessage chatMessage){
        throw new NotImplementedException();
    }
    public void receiveGroupMessage(ChatMessage chatMessage){
        throw new NotImplementedException();
    }

}
