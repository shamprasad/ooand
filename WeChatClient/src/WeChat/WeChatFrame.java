package wechat;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import static javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT;

/**
 * Created by ek2zqun on 12/1/2016.
 */
public class WeChatFrame extends JFrame implements IWeChat {
    private JTabbedPane tabbedPane;
    private Client client;
    private boolean connected;

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

    public void loginSuccessful(){
        ((IWeChat) this.tabbedPane.getSelectedComponent()).loginSuccessful();
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
