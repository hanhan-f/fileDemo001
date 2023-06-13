package util;

import views.Main_view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import views.*;



public class addFile_item extends JLabel {

    Color color = new Color(88, 82, 82);

    /*
    遍历获取到的子文件数组，通过循环创建文件的显示块，
    并分别添加鼠标点击事件，鼠标移入移除事件的处理
     */
    public addFile_item(JPanel jPanel, File[] files, Dimension dimension,File parent) {
        if (files.length == 0) {
            file_item nn = new file_item();
            nn.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getButton() == 3) {
                        showManu(e,parent);
                    }
                    super.mouseClicked(e);
                }
            });
            jPanel.add(nn);
        } else {
            for (File file : files) {
                file_item item = new file_item(file);
                item.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (file.isDirectory() && e.getButton() == 1 && e.getClickCount() == 2) {
                            File[] file1 = file.listFiles();
                            Main_view.getRightPane().resetUP_panel(file);
                            Main_view.getRightPane().resetDown_panel(file1,file);
                        } else if (file.isFile() && e.getButton() == 1 && e.getClickCount() == 2) {
                            Main_view.getRightPane().open(file);
                        } else if (e.getButton() == 3) {
                            showManu(e, file, item);
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
        }
        jPanel.repaint();
    }

    private static void showManu(MouseEvent e,File parent) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem item1 = new JMenuItem("新建");
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent en) {
                creatMenu(e,parent,true);
            }
        });
        menu.add(item1);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

    private static void showManu(MouseEvent me, File file, file_item item) {
        JPopupMenu menu = new JPopupMenu();
        JMenuItem item1 = new JMenuItem("打开");
        JMenuItem item2 = new JMenuItem("重命名");
        JMenuItem item3 = new JMenuItem("删除");
        JMenuItem item4 = new JMenuItem("新建");
        JMenuItem item5 = new JMenuItem("查看文件属性");


        //鼠标右键打开事件
        item1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (file.isDirectory()) {
                    File[] file1 = file.listFiles();
                    Main_view.getRightPane().resetUP_panel(file);
                    Main_view.getRightPane().resetDown_panel(file1,file);
                } else if (file.isFile()) {
                    Main_view.getRightPane().open(file);
                }
            }
        });

        //重命名事件
        item2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                item.setRename();
            }
        });

        //删除事件
        item3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File parent=file.getParentFile();
                ChangeFile_item.delete(file);
                Main_view.getRightPane().resetDown_panel(parent.listFiles(),parent);
            }
        });
        //新建文件
        item4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                creatMenu(me,file,false);
            }
        });
        //查看文件详情
        item5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new attribute_view(Main_view.getjFrame(), file);

            }
        });

        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menu.add(item4);
        menu.add(item5);
        menu.show(me.getComponent(), me.getX(), me.getY());

    }
    private static void creatMenu(MouseEvent e,File file,Boolean isNull){
        JPopupMenu menu = new JPopupMenu();
        JMenuItem item_F = new JMenuItem("文件夹");
        JMenuItem item_t = new JMenuItem("txt文件");
        JMenuItem item_d = new JMenuItem("doc文档");
        JMenuItem item_p = new JMenuItem("ppt演示");
        File parent;
        if(isNull){
            parent=file;
        }else {
            parent=file.getParentFile();
        }
        item_F.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeFile_item.creatFile(parent,"新建文件夹",true);
            }
        });
        //新建txt
        item_t.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeFile_item.creatFile(parent,".txt");
            }
        });

        //新建doc
        item_d.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeFile_item.creatFile(parent,".doc");
            }
        });
        //新建ppt
        item_p.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ChangeFile_item.creatFile(parent,".ppt");
            }
        });
        menu.add(item_F);
        menu.add(item_t);
        menu.add(item_d);
        menu.add(item_p);
        menu.show(e.getComponent(), e.getX(), e.getY());
    }

}
