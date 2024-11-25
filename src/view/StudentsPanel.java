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

import controller.CourseView;
import controller.EditInfo;
import controller.GradeInfo;
import controller.Info;

@SuppressWarnings("serial")
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
		setSize(300, 340);

		contain = new JPanel();
		contain.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		infoButton = new JButton("信息查询");
		gradeButton = new JButton("成绩查询");
		courseButton = new JButton("课程查询");
		editButton = new JButton("修改信息");
		exitButton = new JButton("退出登录");

		gbc.gridx = 0;
		gbc.gridy = 0;
		contain.add(infoButton, gbc);

		gbc.gridy = 1;
		contain.add(gradeButton, gbc);

		gbc.gridy = 2;
		contain.add(courseButton, gbc);

		gbc.gridy = 3;
		contain.add(editButton, gbc);

		gbc.gridy = 4;
		contain.add(exitButton, gbc);

		infoButton.addActionListener(this);
		gradeButton.addActionListener(this);
		courseButton.addActionListener(this);
		editButton.addActionListener(this);
		exitButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// 关闭当前窗口
				dispose();
				// 返回到主界面
				new MainFrame();
			}
		});

		add(contain);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

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
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
			System.exit(0);
		}
	}
}