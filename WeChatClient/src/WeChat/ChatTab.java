package wechat;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;

/**
 * Created by ek2zqun on 12/1/2016.
 */
public class ChatTab  extends JPanel implements ActionListener, IWeChat {
    private JTextArea textArea;
    private JButton btnEnter;
    private WeChatFrame frame;
    private String userName;
    private int userId;

    public ChatTab(WeChatFrame frame, String userName, int userId) {
        this.frame = frame;
        this.userName = userName;
        this.userId = userId;


        setLayout(new GridBagLayout());
        textArea = new JTextArea("Welcome", this.frame.getSize().width, 80);
        textArea.setMinimumSize(new Dimension(this.frame.getWidth(), this.frame.getHeight() - 100));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        textArea.setBackground(Color.black);
        textArea.setForeground(Color.green);
        textArea.setEditable(true);
        textArea.setBorder(new EmptyBorder(10, 10, 10, 100));
        add(textArea, gbc);

        btnEnter = new JButton("Enter to Send");
        gbc.ipady = 10;
        gbc.gridy += 2;
        add(btnEnter, gbc);
        btnEnter.addActionListener(this);
        btnEnter.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {

            }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER){
                    sendMessage();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) {

            }
        });
        frame.getTabbedPane().add(this, userName);
    }

    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == btnEnter){
            sendMessage();
        }
    }
    public void append(String message){
    }

    protected void sendMessage(){
        ChatMessage chatMessage = new ChatMessage(MessageType.IndividualMessage, textArea.getText());
        chatMessage.setFromContactId(this.frame.getCurrentUserId());
        chatMessage.setFromUserName(this.frame.getCurrentUserName());
        chatMessage.setToUserName(this.userName);
        chatMessage.setToContactId(this.userId);
        this.frame.getClient().sendMessage(chatMessage);
        textArea.setText("");
    }

    public void connectionFailed(){
    }

    public void loginSuccessful(int userId){
    }
    public void setContactList(java.util.List<wechat.Contact> contactList){
        throw new NotImplementedException();
    }

    public void receiveMessage(ChatMessage chatMessage){
        textArea.setText(chatMessage.getMessage());
    }
}

