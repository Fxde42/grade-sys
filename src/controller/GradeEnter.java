package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import javax.swing.*;
import java.util.ArrayList;


@SuppressWarnings("serial")
public class GradeEnter extends JFrame implements ActionListener {
	/*
	 * 教师登录课程信息
	 */
	String teacherId; // 教师号
	JPanel mainPanel, gradePanel;
	JLabel courseIdLabel, studentIdLabel, studentNameLabel, studentGradeLabel;
	JTextField courseIdField, studentIdField, studentNameField, studentGradeField;
	JButton submitButton, gradeSubmitButton;
	ArrayList<String> modifiedContent = new ArrayList<>();
	String targetFile;

	public GradeEnter(String teacherId) {
		super("教师课程管理");
		this.teacherId = teacherId;
		setSize(400, 200);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		mainPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// 初始化组件
		courseIdLabel = new JLabel("课程号:");
		courseIdField = new JTextField(15);
		submitButton = new JButton("提交");

		// 设置组件间距
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// 添加课程号标签和文本框到主面板
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		mainPanel.add(courseIdLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(courseIdField, gbc);

		// 添加提交按钮
		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		mainPanel.add(submitButton, gbc);

		submitButton.addActionListener(this);

		add(mainPanel);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitButton) {
			if (hasThisCourse(courseIdField.getText()) == 1) {
				openGradeEntryWindow();
			} else {
				JOptionPane.showMessageDialog(this, "您未开设此课程！", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		} else if (e.getSource() == gradeSubmitButton) {
			if (hasThisStudent()) {
				saveGradeData();
				JOptionPane.showMessageDialog(this, "成绩录入成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				JOptionPane.showMessageDialog(this, "该课程无此学生！", "提示", JOptionPane.INFORMATION_MESSAGE);
			}
		}
	}

	// 打开成绩录入窗口
	private void openGradeEntryWindow() {
		JFrame gradeFrame = new JFrame("录入成绩");
		gradeFrame.setSize(400, 300);
		gradeFrame.setLocationRelativeTo(null);

		gradePanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		// 初始化组件
		studentIdLabel = new JLabel("学号:");
		studentIdField = new JTextField(15);
		studentNameLabel = new JLabel("姓名:");
		studentNameField = new JTextField(15);
		studentGradeLabel = new JLabel("成绩:");
		studentGradeField = new JTextField(15);
		gradeSubmitButton = new JButton("提交");

		// 设置组件间距
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		// 添加学号
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gradePanel.add(studentIdLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.WEST;
		gradePanel.add(studentIdField, gbc);

		// 添加姓名
		gbc.gridx = 0;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.EAST;
		gradePanel.add(studentNameLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gradePanel.add(studentNameField, gbc);

		// 添加成绩
		gbc.gridx = 0;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.EAST;
		gradePanel.add(studentGradeLabel, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		gbc.anchor = GridBagConstraints.WEST;
		gradePanel.add(studentGradeField, gbc);

		// 添加提交按钮
		gbc.gridx = 1;
		gbc.gridy = 3;
		gbc.anchor = GridBagConstraints.CENTER;
		gradePanel.add(gradeSubmitButton, gbc);

		gradeSubmitButton.addActionListener(this);

		gradeFrame.add(gradePanel);
		gradeFrame.setVisible(true);
	}

	// 检查课程是否存在
	private int hasThisCourse(String courseId) {
		String file = System.getProperty("user.dir") + "/data/course.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] result = line.split(" ");
				if (result[0].equals(courseId)) {
					return 1;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return 0;
	}

	// 检查该学生是否在此课程中
	private boolean hasThisStudent() {
		String studentId = studentIdField.getText();
		String path = System.getProperty("user.dir") + "/data/course_student";
		File directory = new File(path);

		for (File file : directory.listFiles()) {
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = br.readLine()) != null) {
					String[] result = line.split(" ");
					if (result[0].equals(courseIdField.getText()) && result[2].equals(studentId)) {
						return true;
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	// 保存成绩数据
	private void saveGradeData() {
		String gradeInfo = String.format("%s %s %s", studentIdField.getText(), studentNameField.getText(),
				studentGradeField.getText());
		modifiedContent.add(gradeInfo);

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(targetFile))) {
			for (String line : modifiedContent) {
				bw.write(line);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 处理窗口关闭事件
	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}
}
