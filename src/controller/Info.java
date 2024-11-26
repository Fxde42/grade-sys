package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;

import model.Student;
import model.Teacher;

public class Info extends JFrame {
	private static final long serialVersionUID = 1L;

	// 定义界面上的组件
	private JLabel idLabel, nameLabel, genderLabel, birLabel, insLabel, majorLabel;
	private String id, name, pwd, gender, birthday, institute, major;
	private JPanel stuInfoJPanel;

	private Student stu;
	private Teacher t;

	public Info(String id, int flag) {
		super("信息");

		this.id = id;
		setSize(300, 340);
		setLocation(600, 400);

		// 创建并设置面板
		stuInfoJPanel = new JPanel();
		stuInfoJPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// 确定文件路径
		String file = (flag == 1) ? System.getProperty("user.dir") + "/data/student.txt" : System.getProperty("user.dir") + "/data/teacher.txt";

		try {
			// 从文件中读取信息
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s;
			while ((s = br.readLine()) != null) {
				String[] result = s.split(" ");
				if (result[0].equals(id)) {
					// 填充学生或教师信息
					id = result[0];
					pwd = result[1];
					name = result[2];
					gender = result[3];
					birthday = result[4];
					institute = result[5];
					major = result[6];

					if (flag == 1) { // 学生
						stu = new Student(id, pwd, name, gender, birthday, institute, major);
						idLabel = new JLabel("账号: " + stu.getId());
						nameLabel = new JLabel("姓名: " + stu.getName());
						genderLabel = new JLabel("性别: " + stu.getSex());
						birLabel = new JLabel("生日: " + stu.getBirthday());
						insLabel = new JLabel("学院: " + stu.getInstitute());
						majorLabel = new JLabel("系别: " + stu.getMajor());
					} else { // 教师
						t = new Teacher(id, pwd, name, gender, birthday, institute, major);
						idLabel = new JLabel("账号: " + t.getId());
						nameLabel = new JLabel("姓名: " + t.getName());
						genderLabel = new JLabel("性别: " + t.getSex());
						birLabel = new JLabel("生日: " + t.getBirthday());
						insLabel = new JLabel("学院: " + t.getInstitute());
						majorLabel = new JLabel("系别: " + t.getMajor());
					}

					// 添加组件到面板
					gbc.gridx = 0;
					gbc.gridy = 0;
					stuInfoJPanel.add(idLabel, gbc);
					gbc.gridy = 1;
					stuInfoJPanel.add(nameLabel, gbc);
					gbc.gridy = 2;
					stuInfoJPanel.add(genderLabel, gbc);
					gbc.gridy = 3;
					stuInfoJPanel.add(birLabel, gbc);
					gbc.gridy = 4;
					stuInfoJPanel.add(insLabel, gbc);
					gbc.gridy = 5;
					stuInfoJPanel.add(majorLabel, gbc);
				}
			}
			br.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// 添加面板到框架
		add(stuInfoJPanel);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 关闭时不会退出程序
		setVisible(true);

		// 添加窗口关闭监听器
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dispose();  // 关闭窗口
			}
		});
	}

	public static void main(String[] args) {
		// 测试界面
		new Info("testStudentID", 1); // 用具体的id和flag来测试
	}
}
