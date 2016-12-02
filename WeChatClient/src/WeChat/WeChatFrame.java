package wechat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.List;

import static javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT;

/**
 * Created by ek2zqun on 12/1/2016.
 */
public class WeChatFrame extends JFrame implements IWeChat {
    private JTabbedPane tabbedPane;
    private Client client;
    private boolean connected;
    private int currentUserId;
    private String currentUserName;
    private List<wechat.Contact> contactList;

    public WeChatFrame(String title) {
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
        this.add(tabbedPane, BorderLayout.CENTER);

        new LoginTab(this);
        setSize(400, 200);
        setVisible(true);
    }

    public void append(String message){
        ((IWeChat) this.tabbedPane.getSelectedComponent()).append(message);
    }

    public void connectionFailed(){
        ((IWeChat) this.tabbedPane.getSelectedComponent()).connectionFailed();
    }

    public void loginSuccessful(int userId){
        ((IWeChat) this.tabbedPane.getSelectedComponent()).loginSuccessful(userId);
    }

    public void setContactList(java.util.List<wechat.Contact> contactList){
        this.contactList = contactList;
        ActionEvent event = new ActionEvent(this, 1, "ContactListChanged");
        java.awt.Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(event);
        ((ActionListener) this.tabbedPane.getSelectedComponent()).actionPerformed(event);
//        this.dispatchEvent(event);
//        event.notifyAll();
    }

    public List<wechat.Contact> getContactList(){
        return this.contactList;
    }

    public void setClient(Client client){
        this.client = client;
    }

    public Client getClient(){
        return this.client;
    }

    public void setConnected(boolean connected){
        this.connected = connected;
    }

    public boolean getConnected(){
        return this.connected;
    }

    public JTabbedPane getTabbedPane(){
        return this.tabbedPane;
    }

    public void setCurrentUserId(int currentUserId){
        this.currentUserId = currentUserId;
    }

    public int getCurrentUserId(){
        return this.currentUserId;
    }

    public void setCurrentUserName(String currentUserName){
        this.currentUserName = currentUserName;
    }

    public String getCurrentUserName(){
        return this.currentUserName;
    }

    public static void main(String args[]){
        Runnable runner = new Runnable() {
            @Override
            public void run() {
               WeChatFrame frame = new WeChatFrame("WeChat Client");
            }
        };

        EventQueue.invokeLater(runner);
    }
}
