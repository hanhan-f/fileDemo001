package views;

import Dao.User;
import util.addFile_item;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Path;

import static views.File_view.createTree;

public class Main_view extends JFrame {
    static JFrame jFrame;
    JSplitPane spane;
    static RightPane rightPane;
    static File rootFile;
    private User user;
    public Main_view(User user){
        this.user=user;
        jFrame=new JFrame("文件管理系统");
        jFrame.setSize(new Dimension(750,650));
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setLocationRelativeTo(null);
        spane = creatJsplitPane(jFrame);
        creatLeftPane(spane);
        creatRightPane(spane);
        jFrame.setVisible(true);

    }

    public JSplitPane creatJsplitPane(JFrame jFrame){
        JSplitPane splitPane = new JSplitPane();
        splitPane.setDividerLocation(100);
        splitPane.setDividerSize(4);
        jFrame.getContentPane().add(splitPane);
        return splitPane;
    }

    public void creatLeftPane(JSplitPane jSplitPane){
        rootFile=new File("src/main/resources/UserFiles/"+user.getId());
        FileTree tree = createTree(rootFile);
        //获取所选中文件的文件名，及对应下一级文件
        tree.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                DefaultMutableTreeNode treeNode=(DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                FileNode fileNode=(FileNode)treeNode.getUserObject();
                File file = fileNode.getFile();
                File[] files=file.listFiles();
                rightPane.resetDown_panel(files,file);
                rightPane.resetUP_panel(file);
                spane.repaint();
                super.mouseClicked(e);
            }
        });
        JScrollPane pane = new JScrollPane(tree);
        jSplitPane.setLeftComponent(pane);
    }

    public void creatRightPane(JSplitPane jSplitPane){
        JSplitPane rsplitpane=new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        jSplitPane.setRightComponent(rsplitpane);
        rightPane=new RightPane(rsplitpane);
    }
    public static RightPane getRightPane(){
        return rightPane;
    }

    public static JFrame getjFrame() {
        return jFrame;
    }

}