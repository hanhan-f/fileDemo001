package util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;

public class file_item extends JPanel {
    JLabel filename;
    JTextField rename;
    BufferedImage image;
    File img,file;
    String name;

    public file_item(){
        this.setPreferredSize(new Dimension(800,800));
//        this.setPreferredSize(new Dimension(500,400));
        JLabel nu=new JLabel("null",JLabel.CENTER);
        this.setBackground(Color.red);
        this.add(nu);
    }
    public file_item(File file){
        this.file=file;
        this.setLayout(null);
        this.setPreferredSize(new Dimension(100,130));
        this.setOpaque(false);
        name=file.getName();
        if(file.isFile()){
            if(name.endsWith(".png")||name.endsWith(".jpg")||name.endsWith(".jpeg")||name.endsWith(".gif")) {
                img = new File("src/main/resources/picture.png");
            }else if(name.endsWith(".txt")){
                img = new File("src/main/resources/txt.png");
            } else if (name.endsWith(".docx")||name.endsWith(".doc")) {
                img = new File("src/main/resources/doc.png");
            } else if (name.endsWith(".ppt") || name.endsWith(".pptx")) {
                img = new File("src/main/resources/PPT.png");
            } else if (name.endsWith(".xls") || name.endsWith(".xlsx")) {
                img = new File("src/main/resources/xls.png");
            } else {
                img = new File("src/main/resources/file.png");
            }
        } else if (file.isDirectory()) {
            img = new File("src/main/resources/FileDirectory.png");
        }
        try {
            image=ImageIO.read(img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PictureView pic = new PictureView();
        pic.setImage(image);
        pic.setPreferredSize(new Dimension(80,80));

        filename=new JLabel(name,JLabel.CENTER);
        filename.setVisible(true);

        rename=new JTextField(name);
        rename.setVisible(false);
        pic.setBounds(10,10,80,80);
        filename.setBounds(10,100,80,20);
        rename.setBounds(10,100,80,20);

        rename.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    saveName();
                }
                super.keyPressed(e);
            }
        });

        this.add(pic);
        this.add(filename);
        this.add(rename);

    }

    public void setRename(){
        filename.setVisible(false);
        rename.setVisible(true);
    }

    public void saveName(){
        System.out.println(file.getParentFile());
        File newFile=new File(file.getParentFile(),rename.getText());
        file.renameTo(newFile);
        filename.setText(rename.getText());
        filename.setVisible(true);
        rename.setVisible(false);
    }
}
