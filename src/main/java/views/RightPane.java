package views;

import util.addFile_item;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.ArrayList;
import util.*;

import views.Main_view;

public class RightPane extends JPanel {
    JPanel up_panel,down_panel;
    JLabel jLabel;
    JScrollPane down,up;
    JTextField jTextField;
    JSplitPane jSplitPane;
    public RightPane(JSplitPane jSplitPane){
        this.jSplitPane=jSplitPane;
        jSplitPane.setDividerLocation(45);
        up_panel=new JPanel();
        down_panel=new JPanel();
        up=new JScrollPane(up_panel);
        down = new JScrollPane(down_panel);
        up.setPreferredSize(new Dimension(4000,60));
        down.setPreferredSize(new Dimension(400,1000));
        setUp_panel();
        setDown_panel();
        jSplitPane.setLeftComponent(up);
        jSplitPane.setRightComponent(down);
        jSplitPane.getLeftComponent().setSize(650,500);
        jSplitPane.getRightComponent().setSize(650,500);
    }

    public void setUp_panel(){
        up_panel.setLayout(null);
        up_panel.setLayout(new FlowLayout(0));
    }
    public void resetUP_panel(File file){
        String[] fileList=file.getAbsolutePath().split("\\\\");
        up_panel=new JPanel();
        new add_NavItem(fileList,up_panel);
        up.setViewportView(up_panel);
    }

    public void setDown_panel(){
        down_panel.setLayout(null);
        down_panel.setLayout(new FlowLayout(0));
    }
    public void resetDown_panel(File[] files,File parent){
        int width=jSplitPane.getRightComponent().getWidth();
        down_panel=new JPanel();
        int height=(files.length/(width/120)+1)*140;
        Dimension dimension=new Dimension(width,height);
        down_panel.setPreferredSize(dimension);
        new addFile_item(down_panel,files,dimension,parent);
        down.setViewportView(down_panel);
    }

    public void open(File file){
        int width=jSplitPane.getRightComponent().getWidth();
        down_panel=new JPanel();
        int height=jSplitPane.getRightComponent().getHeight();
        Dimension dimension=new Dimension(width,height);
        down_panel.setPreferredSize(dimension);
        new open_file(file,down_panel,dimension);
        down.setViewportView(down_panel);
    }

}
