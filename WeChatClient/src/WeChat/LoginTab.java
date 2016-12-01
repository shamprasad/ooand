package wechat;

/**
 * Created by ek2zqun on 11/30/2016.
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginTab extends JPanel implements ActionListener, IWeChat{
    private JTabbedPane pane;
    private JButton btLogin, btRegister;
    private Client client;
    private boolean connected = false;
    private JTextField txtUserName;
    private JPasswordField txtPassword;
    private JLabel labalStatus;

    public LoginTab(JTabbedPane pane, int index){

        this.pane = pane;
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

        pane.add(this, "Login");
    }


    public void actionPerformed(ActionEvent e) {
        Object o = e.getSource();

        if(!connected){
            try{
                client = new Client("localhost", 1501, this);
                connected = true;
                if(client.start()){
                    return ;
                }
            }
            catch (Exception e1){
                connected = false;
                connectionFailed();
            }
        }

        if(o == btLogin){
           // client.sendMessage(new ChatMessage(MessageType.LogIn, "UserName:" + txtUserName.getText() + ",Password" + txtPassword.getPassword() ));
            client.sendMessage(new ChatMessage(MessageType.LogIn, txtUserName.getText()));
        }

    }


    public void append(String message){
        labalStatus.setText(message);
    }

    public void connectionFailed(){
        labalStatus.setText("Connection Failed!");
    }

    public void loginSuccessful(){
        pane.remove(this);
        new MainTab(pane);
    }
}
