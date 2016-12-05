package wechat;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.*;
import java.util.*;

/**
 * Created by ek2zqun on 12/1/2016.
 */
public class ChatTab  extends JPanel implements ActionListener{
    private JTextArea textAreaHistory;
    private JTextArea textArea;
    private JButton btnEnter;
    private WeChatFrame frame;
    private String userName;
    private int userId;
    private int type;


    public ChatTab(WeChatFrame frame, String userName, int userId, int type) {
        this.frame = frame;
        this.userName = userName;
        this.userId = userId;
        this.type = type;


        setLayout(new GridBagLayout());
        textAreaHistory = new JTextArea("", this.frame.getWidth(), this.frame.getHeight() / 2);
        textAreaHistory.setMinimumSize(new Dimension(this.frame.getWidth(), this.frame.getHeight() / 2));


        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        textAreaHistory.setBackground(Color.gray);
        textAreaHistory.setForeground(Color.green);
        textAreaHistory.setEditable(false);
        textAreaHistory.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(textAreaHistory, gbc);

        textArea = new JTextArea("", this.frame.getWidth(), this.frame.getHeight() / 4);
        textArea.setMinimumSize(new Dimension(this.frame.getWidth(), this.frame.getHeight() / 4));
        gbc.gridx = 0;
        gbc.gridy = 1;
        textArea.setBackground(Color.black);
        textArea.setForeground(Color.green);
        textArea.setEditable(true);
        textArea.setBorder(new EmptyBorder(10, 10, 10, 100));
        textArea.requestFocus();
        add(textArea, gbc);

        btnEnter = new JButton("Enter to Send");
        gbc.ipady = 10;
        gbc.gridy += 2;
        add(btnEnter, gbc);
        btnEnter.addActionListener(this);
        textArea.addKeyListener(new KeyListener() {
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

    public void receiveMessage(ChatMessage chatMessage){
        textAreaHistory.append(chatMessage.getFromUserName() + ": " + chatMessage.getMessage() + "\n");
    }

    protected void sendMessage(){
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setMessage(textArea.getText().trim());
        if(this.type == 2){
            chatMessage.setMessageType(MessageType.GroupMessage);
            chatMessage.setToGroupId(this.userId);
            chatMessage.setToGroupName(this.userName);
        }
        else{
            chatMessage.setMessageType(MessageType.IndividualMessage);
            chatMessage.setToContactId(this.userId);
            chatMessage.setToUserName(this.userName);
        }

        chatMessage.setFromContactId(this.frame.getCurrentUserId());
        chatMessage.setFromUserName(this.frame.getCurrentUserName());
        this.frame.getClient().sendMessage(chatMessage);
        textArea.setText("");
        textArea.setCaretPosition(0);
    }
}

