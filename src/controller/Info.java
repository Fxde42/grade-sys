package controller;

import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.FileReader;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.Student;
import model.Teacher;

public class Info extends JFrame {
	private static final long serialVersionUID = 1L;
	JLabel idLabel, nameLabel, genderLabel, birLabel, insLabel, majorLabel;
	String id, name, pwd, gender, birthday, institute, major;
	JPanel stuInfoJPanel;

	Student stu;
	Teacher t;

	public Info(String id, int flag) {
		super("信息");
		this.id = id;
		setSize(300, 340);
		setLocation(600, 400);

		stuInfoJPanel = new JPanel();
		stuInfoJPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		String file = "";
		if (flag == 1) {
			file = System.getProperty("user.dir") + "/data/student.txt";
		} else {
			file = System.getProperty("user.dir") + "/data/teacher.txt";
		}

		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s = null;
			while ((s = br.readLine()) != null) {
				String[] result = s.split(" ");
				if (result[0].equals(id)) {
					id = result[0];
					pwd = result[1];
					name = result[2];
					gender = result[3];
					birthday = result[4];
					institute = result[5];
					major = result[6];

					if (flag == 1) {
						stu = new Student(id, pwd, name, gender, birthday, institute, major);
						idLabel = new JLabel("账号:" + stu.getId());
						nameLabel = new JLabel("姓名:" + stu.getName());
						genderLabel = new JLabel("性别:" + stu.getSex());
						birLabel = new JLabel("生日:" + stu.getBirthday());
						insLabel = new JLabel("学院:" + stu.getInstitute());
						majorLabel = new JLabel("系别:" + stu.getMajor());
					} else {
						t = new Teacher(id, pwd, name, gender, birthday, institute, major);
						idLabel = new JLabel("账号:" + t.getId());
						nameLabel = new JLabel("姓名:" + t.getName());
						genderLabel = new JLabel("性别:" + t.getSex());
						birLabel = new JLabel("生日:" + t.getBirthday());
						insLabel = new JLabel("学院:" + t.getInstitute());
						majorLabel = new JLabel("系别:" + t.getMajor());
					}

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

		add(stuInfoJPanel);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
		setVisible(true);
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}
}