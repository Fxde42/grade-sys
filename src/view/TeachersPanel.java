package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import controller.AddCourse;
import controller.CourseView;
import controller.EditInfo;
import controller.GradeEnter;
import controller.Info;

@SuppressWarnings("serial")
public class TeachersPanel extends JFrame implements ActionListener {
	/*
	 * 教师登陆后操作主界面
	 */
	String idd;
	JPanel contain;
	JButton infoButton, gradeButton, courseButton, editButton, courseView, sortGrade, logoutButton;

	public TeachersPanel(String idd) {
		super("教师");
		this.idd = idd;
		setLocation(500, 300);
		setSize(300, 420); // 增加了高度以适应按钮间距

		// 使用 Swing 布局
		contain = new JPanel();
		contain.setLayout(new BoxLayout(contain, BoxLayout.Y_AXIS)); // 垂直布局

		// 添加顶部间距
		contain.add(Box.createVerticalStrut(20)); // 顶部间距

		// 设置每个按钮的外观并添加到面板
		infoButton = createButton("信息查询");
		gradeButton = createButton("成绩登录");
		courseButton = createButton("全部课程");
		editButton = createButton("修改信息");
		courseView = createButton("开课");
		sortGrade = createButton("成绩统计");
		logoutButton = createButton("退出登录");

		// 将按钮添加到面板并增加按钮之间的间距
		contain.add(infoButton);
		contain.add(Box.createVerticalStrut(15)); // 按钮之间的间距
		contain.add(editButton);
		contain.add(Box.createVerticalStrut(15));
		contain.add(courseView);
		contain.add(Box.createVerticalStrut(15));
		contain.add(courseButton);
		contain.add(Box.createVerticalStrut(15));
		contain.add(gradeButton);
		contain.add(Box.createVerticalStrut(15));
		contain.add(sortGrade);
		contain.add(Box.createVerticalStrut(15));
		contain.add(logoutButton);

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
			new Info(idd, 0);
		} else if (e.getSource() == gradeButton) {
			new GradeEnter(idd);
		} else if (e.getSource() == courseButton) {
			new CourseView(idd, 1);
		} else if (e.getSource() == editButton) {
			new EditInfo(idd, 1);
		} else if (e.getSource() == courseView) {
			new AddCourse();
		} else if (e.getSource() == sortGrade) {
			new SortGradeFrame();
		} else if (e.getSource() == logoutButton) {
			this.dispose();
			setVisible(false);
			new MainFrame();
		}
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
			System.exit(0);
		}
	}

//	public static void main(String[] args) {
//		// 测试界面
//		new TeachersPanel("testTeacherID");
//	}
}
