package controller;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

import model.Course;

@SuppressWarnings("serial")
public class AddCourse extends JFrame implements ActionListener {

	/*
	 * 教师增加课程
	 */

	private JPanel contain;
	private JButton submit;
	private JLabel id, name, gredit, classH, teacherId, teacherName;
	private JTextField idt, namet, greditt, classHt, teacherIdt, teacherNamet;

	public AddCourse() {
		super("增加课程");
		setSize(400, 500);  // 增大窗口的高度以容纳更多内容
		setLocation(600, 200);

		contain = new JPanel(new GridBagLayout()); // 使用 GridBagLayout 作为主布局
		GridBagConstraints gbc = new GridBagConstraints();

		// 初始化组件
		id = new JLabel("课程号");
		name = new JLabel("课程名");
		gredit = new JLabel("学分");
		classH = new JLabel("学时");
		teacherId = new JLabel("教师号");
		teacherName = new JLabel("教师名");

		submit = new JButton("提交");
		idt = new JTextField(15);
		namet = new JTextField(15);
		greditt = new JTextField(15);
		classHt = new JTextField(15);
		teacherIdt = new JTextField(15);
		teacherNamet = new JTextField(15);

		// 设置按钮和文本框间距
		gbc.insets = new Insets(15, 10, 15, 10); // 设置组件间距
		gbc.fill = GridBagConstraints.HORIZONTAL; // 设置组件填充模式

		// 添加组件到布局
		gbc.gridx = 0; gbc.gridy = 0;
		contain.add(id, gbc);
		gbc.gridx = 1; gbc.gridy = 0;
		contain.add(idt, gbc);

		gbc.gridx = 0; gbc.gridy = 1;
		contain.add(name, gbc);
		gbc.gridx = 1; gbc.gridy = 1;
		contain.add(namet, gbc);

		gbc.gridx = 0; gbc.gridy = 2;
		contain.add(gredit, gbc);
		gbc.gridx = 1; gbc.gridy = 2;
		contain.add(greditt, gbc);

		gbc.gridx = 0; gbc.gridy = 3;
		contain.add(classH, gbc);
		gbc.gridx = 1; gbc.gridy = 3;
		contain.add(classHt, gbc);

		gbc.gridx = 0; gbc.gridy = 4;
		contain.add(teacherId, gbc);
		gbc.gridx = 1; gbc.gridy = 4;
		contain.add(teacherIdt, gbc);

		gbc.gridx = 0; gbc.gridy = 5;
		contain.add(teacherName, gbc);
		gbc.gridx = 1; gbc.gridy = 5;
		contain.add(teacherNamet, gbc);

		// 设置按钮所在的行并将其居中
		gbc.gridx = 0; gbc.gridy = 6;
		gbc.gridwidth = 2; // 合并两列用于按钮
		gbc.anchor = GridBagConstraints.CENTER;
		contain.add(submit, gbc);

		// 添加按钮点击事件监听
		submit.addActionListener(this);

		// 设置窗口属性
		add(contain);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	// 检查课程是否已存在
	public int hasCourse(String id) {
		String file = System.getProperty("user.dir") + "/data/course.txt";
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String s;
			while ((s = br.readLine()) != null) {
				String[] result = s.split(" ");
				if (result[0].equals(id)) {
					return 1;  // 如果课程号已经存在
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;  // 课程不存在
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			// 检查所有字段是否为空
			if (idt.getText().isEmpty() || namet.getText().isEmpty() || greditt.getText().isEmpty()
					|| classHt.getText().isEmpty() || teacherIdt.getText().isEmpty() || teacherNamet.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null, "信息不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				// 检查课程是否已存在
				if (hasCourse(idt.getText()) == 1) {
					JOptionPane.showMessageDialog(null, "此课程已经存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					String file = System.getProperty("user.dir") + "/data/course.txt";
					ArrayList<String> modifiedContent = new ArrayList<>();

					// 读取现有课程并保存修改
					try (BufferedReader br = new BufferedReader(new FileReader(file))) {
						String s;
						while ((s = br.readLine()) != null) {
							modifiedContent.add(s);
						}
					} catch (Exception e1) {
						e1.printStackTrace();
					}

					// 创建新课程对象
					Course course = new Course(idt.getText(), namet.getText(), greditt.getText(), classHt.getText(),
							teacherIdt.getText(), teacherNamet.getText());

					// 将新课程添加到课程列表
					modifiedContent.add(course.getCourseId() + " " + course.getCourseName() + " " + course.getCredit()
							+ " " + course.getHour() + " " + course.getTeacherId() + " " + course.getTeacherName());

					// 更新课程文件
					try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
						for (String line : modifiedContent) {
							bw.write(line);
							bw.newLine();
						}
					} catch (IOException e1) {
						e1.printStackTrace();
					}

					// 创建成绩文件和学生选课文件
					new File(System.getProperty("user.dir") + "/data/grade" + course.getCourseName() + ".txt");
					new File(System.getProperty("user.dir") + "/data/course_student" + course.getCourseName() + "_student.txt");

					// 显示成功提示
					JOptionPane.showMessageDialog(null, "添加成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	public static void main(String[] args) {
		new AddCourse();
	}
}
