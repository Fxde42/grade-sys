package view;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
	JButton infoButton, gradeButton, courseButton, editButton, courseView, sortGrade, logoutButton; // 添加退出登录按钮

	public TeachersPanel(String idd) {
		super("教师");
		this.idd = idd;
		setLocation(300, 200);
		setSize(300, 380); // 调整高度以容纳新按钮
		contain = new JPanel();
		contain.setLayout(null);
		add(contain);

		infoButton = new JButton("信息查询");
		gradeButton = new JButton("成绩登录");
		courseButton = new JButton("全部课程");
		editButton = new JButton("修改信息");
		courseView = new JButton("开课");
		sortGrade = new JButton("成绩统计");
		logoutButton = new JButton("退出登录"); // 新增退出登录按钮

		infoButton.setBounds(70, 30, 140, 30);
		editButton.setBounds(70, 70, 140, 30);
		courseView.setBounds(70, 110, 140, 30);
		courseButton.setBounds(70, 150, 140, 30);
		gradeButton.setBounds(70, 190, 140, 30);
		sortGrade.setBounds(70, 230, 140, 30);
		logoutButton.setBounds(70, 270, 140, 30); // 设置位置

		contain.add(infoButton);
		contain.add(gradeButton);
		contain.add(courseView);
		contain.add(courseButton);
		contain.add(editButton);
		contain.add(sortGrade);
		contain.add(logoutButton); // 添加到面板中

		infoButton.addActionListener(this);
		gradeButton.addActionListener(this);
		courseView.addActionListener(this);
		courseButton.addActionListener(this);
		editButton.addActionListener(this);
		sortGrade.addActionListener(this);

		logoutButton.addActionListener(new ActionListener() { // 退出登录按钮的监听
			public void actionPerformed(ActionEvent e) {
				dispose(); // 关闭当前窗口
				new MainFrame(); // 返回登录主界面
			}
		});

		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

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
		}
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
			System.exit(0);
		}
	}
}
