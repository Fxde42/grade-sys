package controller;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class CourseView extends JFrame {
	/*
	 * 学生查询课程，教师查询所教授课程
	 */
	private JPanel contain;

	public CourseView(String id, int flag) {
		super("课程查询");

		setSize(600, 400);
		setLocation(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 窗口关闭时不退出程序

		contain = new JPanel();
		contain.setLayout(new BoxLayout(contain, BoxLayout.Y_AXIS)); // 使用垂直排列
		JScrollPane scrollPane = new JScrollPane(contain); // 使用滚动面板来显示内容

		// 创建表头部分，使用 JPanel 来承载
		JPanel headerPanel = new JPanel(new GridLayout(1, 4, 10, 0)); // 表头使用 GridLayout 来确保内容对齐
		headerPanel.setBackground(Color.LIGHT_GRAY);

		// 表头内容
		String[] headers = {"课程编号", "课程名", "学分", "学时"};
		for (String header : headers) {
			JLabel label = new JLabel(header, SwingConstants.CENTER);
			label.setFont(new Font("Arial", Font.BOLD, 16)); // 表头字体
			headerPanel.add(label);
		}

		contain.add(headerPanel); // 将表头添加到面板中

		// 读取数据
		if (flag == 0) {
			// 学生查询课程
			loadCoursesForStudent(id);
		} else if (flag == 1) {
			// 教师查询自己教授课程
			loadCoursesForTeacher(id);
		}

		// 将滚动面板添加到窗口
		add(scrollPane);
		setVisible(true);
	}

	// 学生查询课程方法
	private void loadCoursesForStudent(String id) {
		String path = System.getProperty("user.dir") + "/data/course_student";
		List<String> files = new ArrayList<>();
		File file = new File(path);
		File[] tempList = file.listFiles();

		for (File tempFile : tempList) {
			if (tempFile.isFile()) {
				files.add(tempFile.toString());
			}
		}

		// 读取文件，显示相关课程信息
		try {
			for (String filePath : files) {
				BufferedReader br = new BufferedReader(new FileReader(filePath));
				String s;
				while ((s = br.readLine()) != null) {
					String[] result = s.split(" ");
					if (result[2].equals(id)) { // 学生学号相等时
						String courseid = result[0];
						String coursename = result[1];

						// 读取课程详情
						loadCourseDetails(courseid, coursename);
					}
				}
				br.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 教师查询课程方法
	private void loadCoursesForTeacher(String id) {
		String path = System.getProperty("user.dir") + "/data/course.txt";
		String s;

		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			while ((s = br.readLine()) != null) {
				String[] result = s.split(" ");
				if (result[4].equals(id)) { // 教师ID相等时
					String courseid = result[0];
					String coursename = result[1];
					String credit = result[2];
					String classhour = result[3];

					// 添加课程信息
					addCourseToPanel(courseid, coursename, credit, classhour);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 加载课程详细信息
	private void loadCourseDetails(String courseid, String coursename) {
		String path = System.getProperty("user.dir") + "/data/course.txt";
		String s;
		try {
			BufferedReader br = new BufferedReader(new FileReader(path));
			while ((s = br.readLine()) != null) {
				String[] result = s.split(" ");
				if (courseid.equals(result[0])) {
					String credit = result[2];
					String classhour = result[3];

					// 添加课程信息
					addCourseToPanel(courseid, coursename, credit, classhour);
				}
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 添加课程到面板
	private void addCourseToPanel(String courseid, String coursename, String credit, String classhour) {
		// 创建一个新的 JPanel 来包含每行数据，使用 GridLayout 来确保对齐
		JPanel dataPanel = new JPanel(new GridLayout(1, 4, 10, 0)); // 4列，水平间隔10

		// 添加数据内容到面板
		dataPanel.add(new JLabel(courseid, SwingConstants.CENTER));
		dataPanel.add(new JLabel(coursename, SwingConstants.CENTER));
		dataPanel.add(new JLabel(credit, SwingConstants.CENTER));
		dataPanel.add(new JLabel(classhour, SwingConstants.CENTER));

		contain.add(dataPanel); // 将每一行数据添加到面板中
	}

	public static void main(String[] args) {
		// 测试界面
		new CourseView("testStudentID", 0); // 学生查询课程
		// new CourseView("testTeacherID", 1); // 教师查询课程
	}
}
