package wechat;

import javax.swing.*;

/**
 * Created by ek2zqun on 11/30/2016.
 */

import java.awt.*;

import static javax.swing.JTabbedPane.SCROLL_TAB_LAYOUT;

public class WeChat extends JFrame {
    public static void main(String args[]) {
        Runnable runner = new Runnable() {
            public void run() {
                JFrame frame = new JFrame("JTabbedPane");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                JTabbedPane jtp = new JTabbedPane();
                jtp.setTabLayoutPolicy(SCROLL_TAB_LAYOUT);
                frame.add(jtp, BorderLayout.CENTER);

              //  new LoginTab(, 0);
/*
                for (int i=0; i<4; i++) {
                    JButton button = new JButton("Card " + i);
                    jtp.add("Btn " + i, button);
                     new TabWithCloseButton(jtp, i);
                }
                */
                frame.setSize(400, 200);
                frame.setVisible(true);
            }
        };
        EventQueue.invokeLater(runner);
    }

}