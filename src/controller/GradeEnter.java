package controller;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

@SuppressWarnings("serial")
public class GradeEnter extends JFrame implements ActionListener {
	/*
	 * 教师登录课程信息
	 */
	String teacherId; // 教师号
	JPanel mainPanel;
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

		courseIdLabel = new JLabel("课程号:");
		courseIdField = new JTextField(15);
		submitButton = new JButton("提交");

		// 添加组件到主面板
		gbc.insets = new Insets(10, 10, 10, 10); // 外边距
		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		mainPanel.add(courseIdLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		mainPanel.add(courseIdField, gbc);

		gbc.gridy = 1;
		gbc.gridx = 1;
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

	private void openGradeEntryWindow() {
		JFrame gradeFrame = new JFrame("录入成绩");
		gradeFrame.setSize(400, 300);
		gradeFrame.setLocationRelativeTo(null);

		JPanel gradePanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		studentIdLabel = new JLabel("学号:");
		studentIdField = new JTextField(15);
		studentNameLabel = new JLabel("姓名:");
		studentNameField = new JTextField(15);
		studentGradeLabel = new JLabel("成绩:");
		studentGradeField = new JTextField(15);
		gradeSubmitButton = new JButton("提交");

		gbc.insets = new Insets(10, 10, 10, 10);

		gbc.gridx = 0;
		gbc.gridy = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gradePanel.add(studentIdLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gradePanel.add(studentIdField, gbc);

		gbc.gridy = 1;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gradePanel.add(studentNameLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gradePanel.add(studentNameField, gbc);

		gbc.gridy = 2;
		gbc.gridx = 0;
		gbc.anchor = GridBagConstraints.EAST;
		gradePanel.add(studentGradeLabel, gbc);

		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.WEST;
		gradePanel.add(studentGradeField, gbc);

		gbc.gridy = 3;
		gbc.gridx = 1;
		gbc.anchor = GridBagConstraints.CENTER;
		gradePanel.add(gradeSubmitButton, gbc);

		gradeSubmitButton.addActionListener(this);

		gradeFrame.add(gradePanel);
		gradeFrame.setVisible(true);
	}

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

	@Override
	protected void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}
}
