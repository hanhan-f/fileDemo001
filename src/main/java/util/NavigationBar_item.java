package util;

import javax.swing.*;
import java.awt.*;

public class NavigationBar_item extends JPanel {
    public NavigationBar_item(String text) {
//        this.setLayout(null);
//        this.setPreferredSize(new Dimension(text.length()*10,30));
        this.setOpaque(false);
        JLabel item=new JLabel(text+"\\",JLabel.CENTER);
//        item.setBounds(10,0,text.length()*10,30);
        this.add(item);
    }

}
