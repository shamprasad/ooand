package wechat;

/**
 * Created by ek2zqun on 11/30/2016.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginTab extends JPanel implements ActionListener, IWeChat{
    private JButton btLogin, btRegister;
    private WeChatFrame frame;
  //  private Client client;
  //  private boolean connected = false;
    private JTextField txtUserName;
    private JPasswordField txtPassword;
    private JLabel labalStatus;

    public LoginTab(WeChatFrame frame){

        this.frame = frame;
        setOpaque(false);

        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;

        add(new Label("User Name"), gbc);
        gbc.gridx++;
        txtUserName = new JTextField(10);
        add(txtUserName, gbc);

        gbc.gridy++;
        gbc.gridx--;
        add(new Label("Password"), gbc);

        gbc.gridx++;
        txtPassword = new JPasswordField(10);
        add(txtPassword, gbc);

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
                this.frame.setClient(new Client("localhost", 1501, this));
                this.frame.setConnected(true);
                if(this.frame.getClient().start()){
                    return ;
                }
            }
            catch (Exception e1){
                this.frame.setConnected(false);
                connectionFailed();
            }
        }

        if(o == btLogin){
           // client.sendMessage(new ChatMessage(MessageType.LogIn, "UserName:" + txtUserName.getText() + ",Password" + txtPassword.getPassword() ));
            this.frame.getClient().sendMessage(new ChatMessage(MessageType.LogIn, txtUserName.getText()));
        }

    }


    public void append(String message){
        labalStatus.setText(message);
    }

    public void connectionFailed(){
        labalStatus.setText("Connection Failed!");
    }

    public void loginSuccessful(){
        this.frame.getTabbedPane().remove(this);
        new MainTab(this.frame.getTabbedPane());
    }
}
