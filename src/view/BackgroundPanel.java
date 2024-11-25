package view;

import javax.swing.*;
import java.awt.*;
import java.io.InputStream;
import javax.imageio.ImageIO;
import java.io.IOException;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    // 构造方法：接受 InputStream
    public BackgroundPanel(InputStream inputStream) {
        try {
            if (inputStream != null) {
                // 从 InputStream 读取图片
                backgroundImage = ImageIO.read(inputStream);
            } else {
                throw new IllegalArgumentException("输入流为空，无法加载背景图片！");
            }
        } catch (IOException e) {
            e.printStackTrace();
            backgroundImage = null; // 如果读取失败，则背景设置为 null
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            // 绘制背景图片
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
