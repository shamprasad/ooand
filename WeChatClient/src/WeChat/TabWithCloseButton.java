package wechat;

/**
 * Created by ek2zqun on 11/30/2016.
 */

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;

class TabWithCloseButton extends JPanel implements ActionListener {
    private JTabbedPane pane;
    public TabWithCloseButton(JTabbedPane pane, int index) {
        this.pane = pane;
        setOpaque(false);
        add(new JLabel(
                pane.getTitleAt(index),
                pane.getIconAt(index),
                JLabel.LEFT));
        Icon closeIcon = new CloseIcon();
        JButton btClose = new JButton(closeIcon);
        btClose.setPreferredSize(new Dimension(
                closeIcon.getIconWidth(), closeIcon.getIconHeight()));
        add(btClose);
        btClose.addActionListener(this);
        pane.setTabComponentAt(index, this);
    }
    public void actionPerformed(ActionEvent e) {
        int i = pane.indexOfTabComponent(this);
        if (i != -1) {
            pane.remove(i);
        }
    }
}