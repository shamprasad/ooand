package wechat;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by ek2zqun on 11/30/2016.
 */
public class MainTab extends JPanel implements ActionListener, IWeChat {
    private JTabbedPane pane;
    private JList contacts;
    private String[] data = {"User1", "User2"};
    private DefaultListModel model = new DefaultListModel();

    public MainTab(JTabbedPane jtp){
        pane = jtp;

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
        pane.add(this, "Main");
    }


    public void actionPerformed(ActionEvent e) {
    }


    public void append(String message){
    }

    public void connectionFailed(){
    }

    public void loginSuccessful(){

    }
}
