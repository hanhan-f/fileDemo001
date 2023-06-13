package util;

import views.Main_view;
import views.RightPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;

public class add_NavItem {
    Color color=new Color(88,82,82);
    public add_NavItem(String[] fileList, JPanel jPanel){
        File superFile=new File("src/main/resources/UserFiles");
        Boolean show=false;
        String filePath="";
        for (String name:fileList) {
            File fin;
            if(name.equals(fileList[0])){
                filePath+=name;
                fin=new File(filePath+"\\");
            }else {
                filePath+=("\\"+name);
                fin=new File(filePath);
            }
            NavigationBar_item item=new NavigationBar_item(name);
            item.setVisible(show);
            if(filePath.equals(superFile.getAbsolutePath()))
                show=true;
            item.addMouseListener(new MouseAdapter() {
                public void mouseClicked(MouseEvent e) {
                    if(fin.isDirectory()&&e.getButton()==1){
                        File[] file1=fin.listFiles();
                        Main_view.getRightPane().resetUP_panel(fin);
                        Main_view.getRightPane().resetDown_panel(file1,null);
                    }
                    super.mouseClicked(e);
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    item.setBackground(color);
                    item.setOpaque(true);
                    item.repaint();
                    super.mouseEntered(e);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    item.setOpaque(false);
                    item.repaint();
                    super.mouseExited(e);
                }
            });
            jPanel.add(item);
        }
        jPanel.repaint();
    }
}
