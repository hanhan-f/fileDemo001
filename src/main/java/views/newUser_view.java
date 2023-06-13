package views;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.Properties;
import java.util.Set;
import Dao.user_dao;

public class newUser_view extends JFrame {
    JLabel ul,pwdl,rpwdl;
    JTextField userTest,pwd,rpwd;
    JButton new_user;
    Boolean canCreak=true;
    public void nView() {
        JFrame frame = new JFrame("CreatNewUser");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(400, 300));
        frame.setLocationRelativeTo(null);
        JPanel root = new JPanel();
        frame.setContentPane(root);

        ul = new JLabel("用户名");
        userTest = new JTextField();
        userTest.setPreferredSize(new Dimension(45, 15));

        pwdl = new JLabel("密码");
        pwd = new JPasswordField();
        pwd.setPreferredSize(new Dimension(45, 15));

        rpwdl=new JLabel("请再次输入密码");
        rpwd=new JPasswordField();
        rpwd.setPreferredSize(new Dimension(45, 15));
        new_user = new JButton("创建");

        new_user.addActionListener(e -> {
            String id=userTest.getText();
            String p1=pwd.getText();
            String p2=rpwd.getText();
            if(id==null||p1==null||p2==null){
                JOptionPane.showMessageDialog(null,"输入不能为空","tips",JOptionPane.PLAIN_MESSAGE);
            } else if (!p1 .equals(p2)) {
                JOptionPane.showMessageDialog(null,"两次密码输入不一致","tips",JOptionPane.PLAIN_MESSAGE);
            }else {
                Properties user=new Properties();
                InputStream inputStream=null;
                FileWriter fw=null;
                try {
                    inputStream = new FileInputStream("user_map.properties");
                    user=new Properties();
                    user.load(inputStream);
                }catch (Exception exception){
//            e.printStackTrace();
                    if(exception.getMessage().equals("user_map.properties (系统找不到指定的文件。)")){
                        user.setProperty(id,p1);
                        try {
                            fw=new FileWriter("user_map.properties",true);
                            user.store(fw,null);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }finally {
                            try {
                                fw.close();
                            }catch (Exception e1){

                            }
                        }
                        JOptionPane.showMessageDialog(null,"创建成功","tips",JOptionPane.PLAIN_MESSAGE);
                        File file=new File("src/main/resources/UserFiles/"+id);
                        file.mkdir();
                        frame.setVisible(false);
                        new loginView().lView();
                    }
                }finally {
                    try {
                        inputStream.close();
                    } catch (IOException exception) {
                        throw new RuntimeException(exception);
                    }
                }
                //对返回的user进行处理
                Set<String> user_set = user.stringPropertyNames();
                for(String user_id:user_set){
//            user.getProperty(user_id);
                    if(id.equals(user_id)){
                        JOptionPane.showMessageDialog(null,"用户名已存在","tips",JOptionPane.PLAIN_MESSAGE);
                        canCreak=false;
                        break;
                    }
                }
                if (canCreak) {
                    frame.setVisible(false);
                    new user_dao().add_user(id,p1);
                    new loginView().lView();
                }
            }
        });

        root.add(ul);
        root.add(userTest);
        root.add(pwdl);
        root.add(pwd);
        root.add(rpwdl);
        root.add(rpwd);
        root.add(new_user);

        frame.setVisible(true);
    }
}
