package controller;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GradeInfo extends JFrame {
	private static final long serialVersionUID = 1L;

	private JPanel contain;
	private String id;

	public GradeInfo(String id) {
		super("成绩查询");

		this.id = id;
		setSize(600, 400);
		setLocation(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 窗口关闭时不退出程序

		// 创建主面板
		contain = new JPanel();
		contain.setLayout(new BoxLayout(contain, BoxLayout.Y_AXIS)); // 使用垂直排列
		JScrollPane scrollPane = new JScrollPane(contain); // 使用滚动面板来显示内容

		// 创建表头部分，使用 JPanel 来承载
		JPanel headerPanel = new JPanel(new GridLayout(1, 7, 10, 0)); // 表头使用 GridLayout 来确保内容对齐
		headerPanel.setBackground(Color.LIGHT_GRAY);

		// 表头内容
		String[] headers = {"课程号", "课程名", "教师工号", "教师姓名", "学号", "学生姓名", "成绩"};
		for (String header : headers) {
			JLabel label = new JLabel(header, SwingConstants.CENTER);
			label.setFont(new Font("Arial", Font.BOLD, 16)); // 表头字体
			headerPanel.add(label);
		}

		contain.add(headerPanel); // 将表头添加到面板中

		// 文件路径
		String path = System.getProperty("user.dir") + "/data/grade";
		List<String> files = new ArrayList<>();
		File file = new File(path);
		File[] tempList = file.listFiles();

		// 收集所有文件
		for (File tempFile : tempList) {
			if (tempFile.isFile()) {
				files.add(tempFile.toString());
			}
		}

		// 读取每个文件并过滤出相关学生成绩信息
		try {
			for (String filePath : files) {
				BufferedReader br = new BufferedReader(new FileReader(filePath));
				String s;
				while ((s = br.readLine()) != null) {
					String[] result = s.split(" ");
					// 如果学生ID匹配，则显示相关成绩信息
					if (result[4].equals(id)) {
						// 创建一个新的 JPanel 来包含每行数据，使用 GridLayout 来确保对齐
						JPanel dataPanel = new JPanel(new GridLayout(1, 7, 10, 0)); // 7列，水平间隔10

						// 添加数据内容到面板
						for (String data : result) {
							JLabel dataLabel = new JLabel(data, SwingConstants.CENTER);
							dataLabel.setFont(new Font("Arial", Font.PLAIN, 14)); // 设置内容字体
							dataPanel.add(dataLabel);
						}

						contain.add(dataPanel); // 将每一行数据添加到面板中
					}
				}
				br.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 将滚动面板添加到窗口
		add(scrollPane);
		setVisible(true);
	}

	public static void main(String[] args) {
		// 测试界面
		new GradeInfo("testStudentID"); // 使用具体的学生ID来测试
	}
}
