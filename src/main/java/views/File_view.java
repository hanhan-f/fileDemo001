package views;

import javax.swing.*;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeWillExpandListener;
import javax.swing.filechooser.FileSystemView;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.ExpandVetoException;
import java.awt.*;
import java.io.File;

public class File_view {

    //传入根文件路径，返回一颗文件树
    public static FileTree createTree(File file) {

        //设置节点收起时的图标
        UIManager.put("Tree.collapsedIcon", new ImageIcon("src/main/resources/shut_file.png"));
        //设置节点展开时的图标
        UIManager.put("Tree.expandedIcon", new ImageIcon("src/main/resources/openedFile.png"));

        FileNode fileNode = new FileNode(file.getName(), file, FileSystemView.getFileSystemView().getSystemIcon(file));

        DefaultMutableTreeNode treeNode = new DefaultMutableTreeNode(fileNode);
        FileTree fileTree = new FileTree(treeNode);


        File[] files = fileNode.file.listFiles();

        for (int i = 0; i < files.length; i++) {
            if(files[i].isDirectory()){
                FileNode childFileNode = new FileNode(files[i].getName(), files[i], FileSystemView.getFileSystemView().getSystemIcon(files[i]));
                DefaultMutableTreeNode childTreeNode = new DefaultMutableTreeNode(childFileNode);
                treeNode.add(childTreeNode);
            }
        }

        fileTree.setCellRenderer(new FileTreeRenderer());

        return fileTree;
    }
}


//节点对象，保存节点名称，文件和节点图标
class FileNode {
    public String name;
    public File file;
    public Icon icon;

    public boolean isLode;//判断当前节点是否已加载


    public FileNode(String name, File file, Icon icon) {
        this.name = name;
        this.file = file;
        this.icon = icon;
    }

    public File getFile() {
        return file;
    }
    public String getfname(){
        return name;
    }
}

class FileTree extends JTree {

    public FileTree(DefaultMutableTreeNode treeNode) {
        super(treeNode);
        setRootVisible(true);
        setShowsRootHandles(true);


        //当树展开时触发事件，实现懒加载
        addTreeWillExpandListener(new TreeWillExpandListener() {
            @Override
            public void treeWillExpand(TreeExpansionEvent event) throws ExpandVetoException {
                DefaultMutableTreeNode lastPathComponent = (DefaultMutableTreeNode) event.getPath().getLastPathComponent();

                FileNode fileNode = (FileNode) lastPathComponent.getUserObject();

                //当前节点为加载的情况才需要加载
                if (!fileNode.isLode) {
                    lodeNext2Node(lastPathComponent);
                    fileNode.isLode = true;
                }

            }

            @Override
            public void treeWillCollapse(TreeExpansionEvent event) throws ExpandVetoException {

            }
        });
    }


    /**
     * 遍历参数节点的孩子节点，给每个孩子节点加载节点
     *
     * @param treeNode
     */

    public void lodeNext2Node(DefaultMutableTreeNode treeNode) {

        for (int i = 0; i < treeNode.getChildCount(); i++) {
            DefaultMutableTreeNode child = (DefaultMutableTreeNode) treeNode.getChildAt(i);
            FileNode childFileNode = (FileNode) child.getUserObject();//拿到节点对象
            File file = childFileNode.file;//通过节点对象获取对应的文件

            //遍历该文件的子文件，如果不为空则创建新的节点对象并加到当前节点
            File[] files = file.listFiles();
            if (files != null && files.length > 0) {
                for (File f : files) {
                    FileNode fileNode = new FileNode(f.getName(), f, FileSystemView.getFileSystemView().getSystemIcon(f));
                    if (f.isDirectory()) {
                        child.add(new DefaultMutableTreeNode(fileNode));
                    }

                }
            }
        }

    }


}

//自定义渲染器类，设置节点的图标和名称
class FileTreeRenderer extends DefaultTreeCellRenderer {
    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        JLabel label = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;

        FileNode fileNode = (FileNode) node.getUserObject();
        label.setText(fileNode.name);
        label.setIcon(fileNode.icon);

        return label;
    }

}
