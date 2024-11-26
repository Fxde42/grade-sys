package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.CourseView;
import controller.EditInfo;
import controller.GradeInfo;
import controller.Info;

public class StudentsPanel extends JFrame implements ActionListener {
	/*
	 * 学生登陆后操作主界面
	 */
	JPanel contain;
	String id;
	JButton infoButton, gradeButton, courseButton, editButton, exitButton;

	public StudentsPanel(String id) {
		super("学生");
		this.id = id;
		setLocation(500, 300);
		setSize(300, 380); // 调整窗口高度以适应增加的间距

		// 使用 Swing 布局
		contain = new JPanel();
		contain.setLayout(new BoxLayout(contain, BoxLayout.Y_AXIS)); // 垂直布局

		// 添加顶部间距
		contain.add(Box.createVerticalStrut(20)); // 顶部间距

		// 设置每个按钮的外观
		infoButton = createButton("信息查询");
		gradeButton = createButton("成绩查询");
		courseButton = createButton("课程查询");
		editButton = createButton("修改信息");
		exitButton = createButton("退出登录");

		// 将按钮添加到面板并增加按钮之间的间距
		contain.add(infoButton);
		contain.add(Box.createVerticalStrut(15)); // 按钮之间的间距
		contain.add(gradeButton);
		contain.add(Box.createVerticalStrut(15));
		contain.add(courseButton);
		contain.add(Box.createVerticalStrut(15));
		contain.add(editButton);
		contain.add(Box.createVerticalStrut(15));
		contain.add(exitButton);

		// 设置窗体内容
		add(contain);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// 启用窗口关闭事件
		setResizable(false);
	}

	// 创建按钮并为按钮添加事件处理
	private JButton createButton(String text) {
		JButton button = new JButton(text);
		button.setAlignmentX(Component.CENTER_ALIGNMENT); // 按钮居中对齐
		button.addActionListener(this);
		return button;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == infoButton) {
			new Info(id, 1);
		}
		if (e.getSource() == gradeButton) {
			new GradeInfo(id);
		}
		if (e.getSource() == courseButton) {
			new CourseView(id, 0);
		}
		if (e.getSource() == editButton) {
			new EditInfo(id, 0);
		}
		if (e.getSource() == exitButton) {
			dispose(); // 关闭当前窗口
			new MainFrame(); // 返回到主界面
		}
	}

	public static void main(String[] args) {
		// 测试界面
		new StudentsPanel("testStudentID");
	}
}
