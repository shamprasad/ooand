package wechat;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * Created by ek2zqun on 11/30/2016.
 */
public class MainTab extends JPanel implements ActionListener{
    private WeChatFrame frame;
    private JList contacts;
    private JButton btnAddContact;
    private JScrollPane panelContactList;
    private JPanel panelButton;
    private JButton btnJoinGroup;
    private DefaultListModel model = new DefaultListModel();

    public MainTab(WeChatFrame frame){
        super(new BorderLayout());
        this.frame = frame;
        contacts = new JList(model);
        contacts.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(e.getValueIsAdjusting() == false){
                    JList lsm = (JList)e.getSource();
                    int i1 = lsm.getLeadSelectionIndex();
                    ContactItem item = (ContactItem)lsm.getModel().getElementAt(i1);
                    frame.addChatTab(item.getId(), item.getName(), item.getType());
                }
            }
        });

        contacts.setVisibleRowCount(10);
        this.panelContactList = new JScrollPane(contacts);
        this.add(panelContactList, BorderLayout.CENTER);

        this.btnAddContact = new JButton("Add");

        this.btnAddContact.addActionListener(this);

        this.btnJoinGroup = new JButton("Join");

        this.panelButton = new JPanel();
        panelButton.setLayout(new BoxLayout(this.panelButton, BoxLayout.LINE_AXIS));
        panelButton.add(Box.createHorizontalStrut(5));
        panelButton.add(this.btnAddContact);
        panelButton.add(Box.createHorizontalStrut(5));
        panelButton.add(this.btnJoinGroup);
        panelButton.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

        this.add(panelButton, BorderLayout.PAGE_END);

        this.frame.getTabbedPane().add(this, "Main");
    }


    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand() == "ContactListChanged"){
            List<List<Contact>> lists = this.frame.getContactList();
            setContactList(lists.get(0), lists.get(1));
            this.contacts.repaint();
        }
        else if(e.getSource() == btnAddContact){
            this.frame.getTabbedPane().setSelectedComponent(this.frame.getAddContactTab());
        }
    }

    public void setContactList(List<Contact> contactList, List<Contact> groupList){
        for(wechat.Contact c: contactList){
            model.addElement(new ContactItem(c.getId(), c.getName(), 1));
        }
        for(wechat.Contact c: groupList){
            model.addElement(new ContactItem(c.getId(), c.getName(), 2));
        }
    }
}
