package util;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class PictureView extends JPanel {
    private Image image;
    //背景色
    private Color bgcolor;
    //图片边框
    private boolean broder=false;
    //直接指定图片
    public void setImage(Image image) {
        this.image = image;
        this.repaint();
    }
    //传入file对象获取图片
    public void setImage(File file){
        try {
            this.image=ImageIO.read(file);
            System.out.println(1);
            this.repaint();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //获取资源流地址的形式
    public void setImage(String resoursPath){
        InputStream res = this.getClass().getResourceAsStream(resoursPath);
        try {
            this.image = ImageIO.read(res);
            this.repaint();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void setBgcolor(Color color){
        this.bgcolor=color;
        this.repaint();
    }

    public void setBroder(boolean broder){
        this.broder=broder;
        this.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int width = getWidth();
        int height = getHeight();
        if(this.bgcolor!=null){
            g.setColor(this.bgcolor);
            g.fillRect(0,0,width,height);
        }
        if(this.image!=null){
            int imageHeight = this.image.getHeight(null);
            int imageWidth = this.image.getWidth(null);
            System.out.println(imageHeight);
            //先尝试以窗口容器宽度绘制
            int fitw=width;
            int fith=width*imageHeight/imageWidth;
            if(fith>height){
                //若比例缩放后绘制高度高出窗口高度，则换以高度绘制
                fith=height;
                fitw=height*imageWidth/imageHeight;
            }
            //绘制图片
            int fitx=(width-fitw)/2;
            int fity=(height-fith)/2;
            g.drawImage(image,fitx,fity,fitw,fith,null);
            if (broder) {
                g.setColor(new Color(0,255,255));
                g.drawRect(fitx,fity,fitw-1,fith-1);
            }
        }
    }
}
