package view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class BackgroundPanel extends JPanel {
    private Image backgroundImage;

    // 构造方法：接受文件路径
    public BackgroundPanel(String filePath) {
        try {
            if (filePath != null && !filePath.isEmpty()) {
                // 使用文件路径读取图片
                backgroundImage = ImageIO.read(new File(filePath));
            } else {
                throw new IllegalArgumentException("文件路径为空，无法加载背景图片！");
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
