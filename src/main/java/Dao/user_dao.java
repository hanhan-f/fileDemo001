package Dao;

import javax.swing.*;
import java.io.*;
import java.util.Properties;
import java.util.Set;

public class user_dao {
    Properties user;
    InputStream inputStream;
    FileWriter writer;

    public void add_user(String name,String pwd){
        try{
            writer=new FileWriter("user_map.properties",true);
            user=new Properties();
            user.setProperty(name, pwd);
            user.store(writer,null);
            File file=new File("src/main/resources/UserFiles/"+name);
            file.mkdir();
        }catch (Exception e){

        }finally {
            try {
                writer.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public Boolean query(String name,String pwd){
        user=new Properties();
        try {
            inputStream = new FileInputStream("user_map.properties");
            user=new Properties();
            user.load(inputStream);
        }catch (Exception e){
//            e.printStackTrace();
            if(e.getMessage().equals("user_map.properties (系统找不到指定的文件。)"))
                JOptionPane.showMessageDialog(null,"暂无用户信息，请先创建用户","tips",JOptionPane.PLAIN_MESSAGE);
        }finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        //对返回的user进行处理
        Set<String> user_set = user.stringPropertyNames();
        for(String user_id:user_set){
//            user.getProperty(user_id);
            if(name.equals(user_id)&&pwd.equals(user.getProperty(user_id))){
                return true;
            }else if(name.equals(user_id)&&(pwd.equals(user.getProperty(user_id))==false)){
                JOptionPane.showMessageDialog(null,"用户名或密码错误","tips",JOptionPane.PLAIN_MESSAGE);
                return false;
            }
        }
        JOptionPane.showMessageDialog(null,"用户名不存在","tips",JOptionPane.PLAIN_MESSAGE);
        return false;
    }
}
