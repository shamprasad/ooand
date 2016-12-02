package wechat;

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
        model.addElement(new ContactItem(1, "User1"));
        model.addElement(new ContactItem(2, "User2"));


        contacts = new JList(model);
        contacts.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                JList lsm = (JList)e.getSource();
                int i1 = lsm.getLeadSelectionIndex();
            }
        });

        this.add(contacts);
        this.frame.getTabbedPane().add(this, "Main");
    }


    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "ContactListChanged"){
            for(wechat.Contact c : this.frame.getContactList()){
                model.addElement(new ContactItem(c.getId(), c.getName()));
            }
        }
        this.updateUI();
    }


    public void append(String message){
    }

    public void connectionFailed(){
    }

    public void loginSuccessful(int userId){

    }

    public void setContactList(List<Contact> contactList){
        for(wechat.Contact c: contactList){
            model.addElement(new ContactItem(c.getId(), c.getName()));
        }
    }

}
