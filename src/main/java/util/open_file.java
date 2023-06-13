package util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.*;

public class open_file extends JPanel {
    final int isPicture=1;
    final int isTxt=2;
    final int other=3;
    public open_file(File file,JPanel jPanel,Dimension dimension){
        if(checkFile(file)==isPicture){
            try {
                BufferedImage image= ImageIO.read(file);
                PictureView pic=new PictureView();
                pic.setImage(image);
                pic.setPreferredSize(dimension);
                jPanel.add(pic);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else if (checkFile(file)==isTxt) {
            JTextArea textField=new JTextArea(readTxt(file));
            textField.addKeyListener(new KeyAdapter() {
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.isControlDown()&&e.getKeyCode()==KeyEvent.VK_S){
                        System.out.println(textField.getText());
                        saveTxt(textField.getText(),file);
                    }
                    super.keyPressed(e);
                }
            });
            textField.setLineWrap(true);
            JScrollPane scroo=new JScrollPane(textField);
            scroo.setPreferredSize(dimension);
            jPanel.add(scroo);
        }else if(checkFile(file)==other){
            JOptionPane.showMessageDialog(null,"暂不支持的文件类型","tips",JOptionPane.PLAIN_MESSAGE);
        }
    }
    public int checkFile(File file){
        String name= file.getName();
        if(name.endsWith(".png")||name.endsWith(".jpg")||name.endsWith(".jpeg")||name.endsWith(".gif")) {
            return isPicture;
        }else if(name.endsWith(".txt")){
            return isTxt;
        } else{
            return other;
        }
    }
    public String readTxt(File file){
        BufferedReader br=null;
        StringBuilder text=new StringBuilder();
        try {
            String n;
            br=new BufferedReader(new FileReader(file));
            while ((n= br.readLine())!=null){
                text.append(n+"\n");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                br.close();
            }catch (Exception e){

            }
        }
        return text.toString();
    }

    public void saveTxt(String txt,File file){
        BufferedWriter br=null;
        try {
            br=new BufferedWriter(new FileWriter(file));
            br.write(txt);
            br.flush();
        }catch (Exception e){

        }finally {
            try {
                br.close();
            }catch (Exception e){

            }
        }
    }
}
