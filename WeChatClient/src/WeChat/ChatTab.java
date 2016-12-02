package wechat;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;

/**
 * Created by ek2zqun on 12/1/2016.
 */
public class ChatTab  extends JPanel implements ActionListener, IWeChat {
    private JTextArea textArea;
    private JButton btnEnter;
    private JTabbedPane pane;
    private String userName;
    private int userId;

    public ChatTab(JTabbedPane pane, String userName, int userId) {
        this.pane = pane;
        this.userName = userName;
        this.userId = userId;

        setLayout(new GridBagLayout());
        textArea = new JTextArea();
        GridBagConstraints gbc = new GridBagConstraints();
        add(textArea, gbc);

        btnEnter = new JButton("Enter to Send");
        gbc.gridy++;
        add(btnEnter, gbc);
        pane.add(this, userName);
    }

    public void actionPerformed(ActionEvent e) {

    }
    public void append(String message){
    }

    public void connectionFailed(){
    }

    public void loginSuccessful(int userId){
    }
    public void setContactList(java.util.List<wechat.Contact> contactList){
        throw new NotImplementedException();
    }
}

