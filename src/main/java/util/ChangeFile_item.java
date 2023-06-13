package util;

import views.Main_view;

import java.io.File;
import java.io.IOException;

public class ChangeFile_item {
    public static int i = 0;

    public static void delete(File file) {
        File parentFile = file.getParentFile();

        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            deleteDirectory(file);
        }

        File[] files = parentFile.listFiles();
        Main_view.getRightPane().resetUP_panel(parentFile);
        Main_view.getRightPane().resetDown_panel(files,null);
    }

    public static void deleteDirectory(File file) {
        File[] fileList = file.listFiles();
        for (File file1 : fileList) {
            if (file1.isFile()) {
                file1.delete();
            } else if (file1.isDirectory()) {
                deleteDirectory(file1);
            }
        }
        file.delete();
    }

    public static void creatFile(File file, String child) {
        File newFile = new File(file, "新建文件(" + i + ")"+child );
        if (newFile.exists()) {
            i += 1;
            creatFile(file, child);
        } else {
            i = 0;
            try {
                newFile.createNewFile();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Main_view.getRightPane().resetDown_panel(file.listFiles(),null);
        }
    }

    public static void creatFile(File file, String child, Boolean bo) {
        if (bo) {
            File newFile = new File(file, child + "(" + i + ")");
            if (newFile.exists()) {
                i += 1;
                creatFile(file, child, true);
            } else {
                i = 0;
                newFile.mkdir();
                Main_view.getRightPane().resetDown_panel(file.listFiles(),null);
            }
        }
    }
}
