package wechat;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

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
    private List<wechat.Contact> groupList;
    private List<wechat.Contact> allContactList;
    private HashMap<Integer, ChatTab> indivadualChatTabs;
    private HashMap<Integer, ChatTab> groupChatTabs;
    private AddContactTab addContactTab;
    private wechat.Contact pendingRequest;

    public WeChatFrame(String title) {
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        tabbedPane = new JTabbedPane();
        tabbedPane.setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
        this.add(tabbedPane, BorderLayout.CENTER);

        new LoginTab(this);
        setSize(300, 600);
        setMinimumSize(new Dimension(300, 600));

        setVisible(true);
        indivadualChatTabs = new HashMap<Integer, ChatTab>();
        groupChatTabs = new HashMap<Integer, ChatTab>();
    }

    public void append(String message){}

    public void connectionFailed(){
        ((IWeChat) this.tabbedPane.getSelectedComponent()).connectionFailed();
    }

    public void loginSuccessful(int userId){
        ActionEvent event = new ActionEvent(this, 2, "LoginSuccessful");
        this.setCurrentUserId(userId);
        ((ActionListener) this.tabbedPane.getSelectedComponent()).actionPerformed(event);
    }

    public void setContactList(java.util.List<wechat.Contact> contactList, List<wechat.Contact> groupList){
        this.contactList = contactList;
        this.groupList = groupList;
        ActionEvent event = new ActionEvent(this, 1, "ContactListChanged");
        ((ActionListener) this.tabbedPane.getSelectedComponent()).actionPerformed(event);
    }

    public void setAllContactList(List<wechat.Contact> contactList){
        this.allContactList = contactList;
        ActionEvent event = new ActionEvent(this, 1, "AllContactListChanged");
        ((ActionListener) this.addContactTab).actionPerformed(event);
    }

    public void setPendingRequestList(List<wechat.Contact> pendingRequestList){
        throw new NotImplementedException();
    }

    public void addPendingRequest(ChatMessage chatMessage){
       this.pendingRequest = new wechat.Contact(chatMessage.getFromContactId(), chatMessage.getFromUserName());
        ActionEvent event = new ActionEvent(this, 1, "PendingRequest");
        ((ActionListener) this.getAddContactTab()).actionPerformed(event);
    }

    public wechat.Contact getPendingRequest(){
        return this.pendingRequest;
    }

    public List<wechat.Contact> getAllContactList(){
        return this.allContactList;
    }

    public void receiveIndividualMessage(ChatMessage chatMessage){
        addChatTab(chatMessage.getFromContactId(), chatMessage.getFromUserName(), 1);
        (indivadualChatTabs.get(chatMessage.getFromContactId())).receiveMessage(chatMessage);
    }

    public void receiveGroupMessage(ChatMessage chatMessage){
        addChatTab(chatMessage.getFromGroupId(), chatMessage.getFromGroupName(), 2);
        (groupChatTabs.get(chatMessage.getFromGroupId())).receiveMessage(chatMessage);
    }

    public void addChatTab(int userId, String userName, int type){
        HashMap<Integer, ChatTab> tabs = type == 1? indivadualChatTabs : groupChatTabs;
        if(!tabs.containsKey(userId)){
            tabs.put(userId, new ChatTab(this, userName, userId, type));
            this.getTabbedPane().add(tabs.get(userId), userName);
        }
        this.getTabbedPane().setSelectedComponent(tabs.get(userId));
    }

    public List<List<wechat.Contact>> getContactList(){
        List<List<wechat.Contact> > contactLists = new ArrayList<>();
        contactLists.add(this.contactList);
        contactLists.add(this.groupList);
        return contactLists;
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

    public AddContactTab getAddContactTab(){
        if(this.addContactTab == null){
           this.addContactTab = new AddContactTab(this);
        }
        this.getTabbedPane().add(this.addContactTab, "Adding");
        return this.addContactTab;
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
