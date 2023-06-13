package views;

import util.PictureView;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;

public class attribute_view extends JDialog {
    BufferedImage image;
    File img;
    String name;
    JLabel fileName, attribute, fileAddress, fileSize, fileTime_creat, fileTime_change, fileTime_visit;
    JTextField fileName_t, fileAddress_t, fileSize_t, fileTime_creat_t, fileTime_change_t, fileTime_visit_t;

    public attribute_view(JFrame parent, File file) {
        super(parent, "Custom Dialog", true);
        this.setSize(260, 420);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);


        JPanel panel = new JPanel();
        panel.setLayout(null);
        this.getContentPane().add(panel, BorderLayout.CENTER);
        name = file.getName();
        if (file.isFile()) {
            if (name.endsWith(".png") || name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".gif")) {
                img = new File("src/main/resources/picture.png");
            } else if (name.endsWith(".txt")) {
                img = new File("src/main/resources/txt.png");
            } else if (name.endsWith(".docx") || name.endsWith(".doc")) {
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
            image = ImageIO.read(img);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        PictureView p = new PictureView();

        p.setImage(image);

        fileName = new JLabel("文件名：");
        fileAddress = new JLabel("位置：");
        fileSize = new JLabel("大小：");
        fileTime_creat = new JLabel("创建时间：");
        fileTime_change = new JLabel("修改时间：");
        fileTime_visit = new JLabel("访问时间：");
        attribute = new JLabel("属性：");

        p.setBounds(20, 10, 70, 70);
        fileName.setBounds(20, 100, 70, 20);
        fileAddress.setBounds(20, 120, 70, 20);
        fileSize.setBounds(20, 140, 70, 20);
        fileTime_creat.setBounds(20, 160, 70, 20);
        fileTime_change.setBounds(20, 180, 70, 20);
        fileTime_visit.setBounds(20, 200, 70, 20);
        attribute.setBounds(20, 240, 70, 20);

        panel.add(p);
        panel.add(fileName);
        panel.add(fileAddress);
        panel.add(fileSize);
        panel.add(fileTime_creat);
        panel.add(fileTime_change);
        panel.add(fileTime_visit);
        panel.add(attribute);

        //详细信息
        Path path = file.toPath();
        String creatTime = null;
        String modifiedTime = null;
        String visitTime = null;
        try {
            //获取文件创建时间
            long creationTime = Files.readAttributes(path, BasicFileAttributes.class).creationTime().toMillis();
            //获取文件最后一次修改时间
            long lastModifiedTime = Files.getLastModifiedTime(path).toMillis();
            //获取文件最后一次访问时间
            long lastAccessTime = Files.readAttributes(path, BasicFileAttributes.class).lastAccessTime().toMillis();
            creatTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(creationTime));
            modifiedTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(lastModifiedTime));

            visitTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(lastAccessTime));
        } catch (Exception e) {
            e.printStackTrace();
        }
        double fileSize_byte = getFilesize(file);
        double fileSize_kb=fileSize_byte/1024;
        fileName_t = new JTextField(file.getName());
        fileAddress_t = new JTextField(file.getAbsolutePath());
        fileSize_t = new JTextField(String.valueOf(fileSize_kb)+"KB");
        fileTime_creat_t = new JTextField(creatTime);
        fileTime_change_t = new JTextField(modifiedTime);
        fileTime_visit_t = new JTextField(visitTime);

        fileName_t.setBounds(100, 100, 140, 20);
        fileAddress_t.setBounds(100, 120, 140, 20);
        fileSize_t.setBounds(100, 140, 140, 20);
        fileTime_creat_t.setBounds(100, 160, 140, 20);
        fileTime_change_t.setBounds(100, 180, 140, 20);
        fileTime_visit_t.setBounds(100, 200, 140, 20);


        panel.add(p);
        panel.add(fileName_t);
        panel.add(fileAddress_t);
        panel.add(fileSize_t);
        panel.add(fileTime_creat_t);
        panel.add(fileTime_change_t);
        panel.add(fileTime_visit_t);


        this.setVisible(true);
    }

    //    遍历获取文件大小
    public double getFilesize(File file) {
        double s = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File file1 : files) {
                    s+=getFilesize(file1);
                }
            }
        } else if (file.isFile()) {
            s += file.length();
        }
        return s;
    }


}