package controller;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

@SuppressWarnings("serial")
public class AddUser extends JFrame implements ActionListener {
	/*
	 * 教务管理员添加用户，可以添加学生，教师，管理员
	 */
	private JPanel contain;
	private JLabel id, name, birthday, institute, major;
	private JTextField idt, namet, birthdayt, institutet, majort;
	private Checkbox check1, check2;
	private CheckboxGroup group;
	private JButton submit;
	private Choice choice;

	private String file = System.getProperty("user.dir") + "/data/";

	public AddUser() {
		super("添加用户");
		setSize(350, 400);
		setLocation(600, 400);

		// Initialize components
		contain = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		id = new JLabel("帐号");
		name = new JLabel("姓名");
		group = new CheckboxGroup();
		check1 = new Checkbox("男", group, true);
		check2 = new Checkbox("女", group, false);
		birthday = new JLabel("生日");
		institute = new JLabel("学院");
		major = new JLabel("专业");

		submit = new JButton("提交");
		choice = new Choice();
		choice.addItem("学生");
		choice.addItem("教师");
		choice.addItem("教务员");

		idt = new JTextField();
		namet = new JTextField();
		birthdayt = new JTextField();
		institutet = new JTextField();
		majort = new JTextField();

		// Layout setup using GridBagLayout
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;
		contain.add(id, gbc);

		gbc.gridx = 1;
		contain.add(idt, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		contain.add(name, gbc);

		gbc.gridx = 1;
		contain.add(namet, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		contain.add(birthday, gbc);

		gbc.gridx = 1;
		contain.add(birthdayt, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		contain.add(institute, gbc);

		gbc.gridx = 1;
		contain.add(institutet, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		contain.add(major, gbc);

		gbc.gridx = 1;
		contain.add(majort, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		contain.add(check1, gbc);

		gbc.gridx = 1;
		contain.add(check2, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		contain.add(choice, gbc);

		gbc.gridx = 1;
		contain.add(submit, gbc);

		// Button action listener
		submit.addActionListener(this);

		// Add panel to frame
		add(contain);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			if ((idt.getText().equals("")) || (namet.getText().equals("")) || (birthdayt.getText().equals(""))
					|| (institutet.getText().equals("")) || (majort.getText().equals(""))) {
				JOptionPane.showMessageDialog(null, "信息不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				String ch = (String) choice.getSelectedItem();
				if (ch.equals("学生")) {
					handleUserAddition("student.txt", "学生");
				} else if (ch.equals("教师")) {
					handleUserAddition("teacher.txt", "教师");
				} else {
					handleUserAddition("administrator.txt", "教务员");
				}
			}
		}
	}

	private void handleUserAddition(String fileName, String userType) {
		if ((new CheckInfo().isMember(userType, idt.getText(), namet.getText())) == 2) {
			JOptionPane.showMessageDialog(null, "此" + userType + "已经存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
		} else {
			file = file + fileName;

			ArrayList<String> modifiedContent = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(file))) {
				String s;
				while ((s = br.readLine()) != null) {
					String[] result = s.split(" ");
					String s1 = String.join(" ", result);
					modifiedContent.add(s1);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			String gender = check1.getState() ? "male" : "female";
			String user = idt.getText() + " " + "123456" + " " + namet.getText() + " " + gender + " " +
					birthdayt.getText() + " " + institutet.getText() + " " + majort.getText();
			modifiedContent.add(user);

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
				for (String entry : modifiedContent) {
					bw.write(entry);
					bw.newLine();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			JOptionPane.showMessageDialog(null, "成功添加一个" + userType + "！", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}
}
