package view;

import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
	JButton infoButton, gradeButton, courseButton, editButton, courseView, sortGrade, logoutButton;

	public TeachersPanel(String idd) {
		super("教师");
		this.idd = idd;
		setLocation(500, 300);
		setSize(300, 380);

		contain = new JPanel();
		contain.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		infoButton = new JButton("信息查询");
		gradeButton = new JButton("成绩登录");
		courseButton = new JButton("全部课程");
		editButton = new JButton("修改信息");
		courseView = new JButton("开课");
		sortGrade = new JButton("成绩统计");
		logoutButton = new JButton("退出登录");

		gbc.gridx = 0;
		gbc.gridy = 0;
		contain.add(infoButton, gbc);

		gbc.gridy = 1;
		contain.add(editButton, gbc);

		gbc.gridy = 2;
		contain.add(courseView, gbc);

		gbc.gridy = 3;
		contain.add(courseButton, gbc);

		gbc.gridy = 4;
		contain.add(gradeButton, gbc);

		gbc.gridy = 5;
		contain.add(sortGrade, gbc);

		gbc.gridy = 6;
		contain.add(logoutButton, gbc);

		infoButton.addActionListener(this);
		gradeButton.addActionListener(this);
		courseView.addActionListener(this);
		courseButton.addActionListener(this);
		editButton.addActionListener(this);
		sortGrade.addActionListener(this);

		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MainFrame();
			}
		});

		add(contain);
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