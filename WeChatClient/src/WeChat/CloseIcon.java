package wechat;

/**
 * Created by ek2zqun on 11/30/2016.
 */

import javax.swing.*;
import java.awt.*;

class CloseIcon implements Icon {
    public void paintIcon(Component c, Graphics g, int x, int y) {
        g.setColor(Color.RED);
        g.drawLine(6, 6, getIconWidth() - 7, getIconHeight() - 7);
        g.drawLine(getIconWidth() - 7, 6, 6, getIconHeight() - 7);
    }
    public int getIconWidth() {
        return 17;
    }
    public int getIconHeight() {
        return 17;
    }
}