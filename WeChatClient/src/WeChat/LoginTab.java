package wechat;

/**
 * Created by ek2zqun on 11/30/2016.
 */

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class LoginTab extends JPanel implements ActionListener{
    private JButton btLogin, btRegister;
    private WeChatFrame frame;
    private JTextField txtUserName;
    private JPasswordField txtPassword;
    private JLabel labalStatus;

    public LoginTab(WeChatFrame frame){
        super(new BorderLayout());

        this.frame = frame;
        setOpaque(false);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        JLabel labelUserame = new JLabel("User Name");
        labelUserame.setOpaque(false);
        add(labelUserame, gbc);
        gbc.gridx++;
        txtUserName = new JTextField(10);
        txtUserName.requestFocus();
        add(txtUserName, gbc);

        gbc.gridy++;
        gbc.gridx--;
        JLabel labelPassword = new JLabel("Password");
        labelPassword.setOpaque(false);
        add(labelPassword, gbc);

        gbc.gridx++;
        gbc.ipady = 5;


        txtPassword = new JPasswordField(10);
        add(txtPassword, gbc);

        gbc.gridy++;
        JLabel labelPading = new JLabel();
        labelPading.setOpaque(false);
        add(labelPading, gbc);
        gbc.gridy++;
        add(labelPading, gbc);

        gbc.gridx = 0;
        gbc.gridy += 3;

        btLogin = new JButton("Log in");
        btLogin.addActionListener(this);
        add(btLogin, gbc);

        gbc.gridx += 1;

        btRegister = new JButton("Register");
        btRegister.addActionListener(this);
        add(btRegister, gbc);

        gbc.gridx = 0;
        gbc.gridy += 2;

        labalStatus = new JLabel();
        add(labalStatus, gbc);

        this.frame.getTabbedPane().add(this, "Login");
    }

    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if(!this.frame.getConnected()){
            try{
                this.frame.setClient(new Client("localhost", 1500, this.frame));
                this.frame.setConnected(true);
                if(!this.frame.getClient().start()){
                    return ;
                }
            }
            catch (Exception e1){
                this.frame.setConnected(false);
                connectionFailed();
            }
        }

        if(o == btLogin){
            this.frame.getClient().sendMessage(new ChatMessage(MessageType.LogIn, txtUserName.getText().trim()));
        }
        else if(o == btRegister){
            this.frame.getClient().sendMessage(new ChatMessage(MessageType.Register, txtUserName.getText().trim()));
        }
        else if(e.getActionCommand() == "RegisterSuccessful"){
            this.frame.getTabbedPane().remove(this);
            new MainTab(this.frame);
        }
        else if(e.getActionCommand() == "LoginSuccessful"){
            ChatMessage chatMessage = new ChatMessage(MessageType.FriendListRequest, "");
            this.frame.setCurrentUserName(txtUserName.getText().trim());
            chatMessage.setFromContactId(this.frame.getCurrentUserId());
            this.frame.getClient().sendMessage(chatMessage);
        }
        else if(e.getActionCommand() == "ContactListChanged"){
            this.frame.getTabbedPane().remove(this);
            MainTab tab = new MainTab(this.frame);
            tab.actionPerformed(e);
        }
    }

    public void connectionFailed(){
        labalStatus.setText("Connection Failed!");
    }
}
