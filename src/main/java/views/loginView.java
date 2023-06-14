package views;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import Dao.User;
import Dao.user_dao;

public class loginView extends JFrame {

    JTextField userTest;
    JTextField pwd;
    JButton log, newUser;
    JLabel ul, pwdl;

    public void lView() {
        JFrame frame = new JFrame("login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(new Dimension(400, 300));
        frame.setLocationRelativeTo(null);
        JPanel root = new JPanel();
        frame.setContentPane(root);
        root.setLayout(null);
        ul = new JLabel("用户名");
        userTest = new JTextField();
//        userTest.setPreferredSize(new Dimension(45, 15));

        pwdl = new JLabel("密码");
        pwd = new JPasswordField();
//        pwd.setPreferredSize(new Dimension(45, 15));
        log = new JButton("登录");
        newUser = new JButton("创建新用户");

        ul.setBounds(80,40,50,20);
        userTest.setBounds(130,40,150,20);
        pwdl.setBounds(80,70,50,20);
        pwd.setBounds(130,70,150,20);
        log.setBounds(120,100,140,30);
        newUser.setBounds(260,110,90,20);
        newUser.setFont(new Font("Serif", Font.BOLD,10));

        //登录事件
        log.addActionListener(e -> {
            String user = userTest.getText();
            String pwdText = pwd.getText();
            if (new user_dao().query(user, pwdText)) {
                frame.setVisible(false);
                new Main_view(new User(user, pwdText));
                System.out.println(true);
            }
        });

        //创建新用户事件
        newUser.addActionListener(e -> {
            frame.setVisible(false);
            new newUser_view().nView();
        });


        root.add(ul);
        root.add(userTest);
        root.add(pwdl);
        root.add(pwd);
        root.add(log);
        root.add(newUser);


        frame.setVisible(true);
    }
}
