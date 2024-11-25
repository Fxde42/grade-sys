package controller;

import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class DeleteUser extends JFrame implements ActionListener {
	/**
	 * 管理员删除用户
	 */
	private static final long serialVersionUID = 1L;
	JPanel contain;
	JLabel id;
	JTextField idt;
	JComboBox<String> comboBox;
	JButton submit;

	String file = System.getProperty("user.dir") + "/data/";

	public DeleteUser() {
		super("删除用户");
		setSize(300, 340);
		setLocation(600, 400);

		// 使用 GridBagLayout 布局管理器
		contain = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		comboBox = new JComboBox<>(new String[] { "学生", "教师", "教务员" });

		id = new JLabel("帐号");
		idt = new JTextField(15);
		submit = new JButton("提交");

		// 设置组件的布局位置
		gbc.gridx = 0;
		gbc.gridy = 0;
		contain.add(id, gbc);

		gbc.gridx = 1;
		contain.add(idt, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		contain.add(new JLabel("角色"), gbc);

		gbc.gridx = 1;
		contain.add(comboBox, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		contain.add(submit, gbc);

		submit.addActionListener(this);

		add(contain);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			String ch = (String) comboBox.getSelectedItem();
			if (ch.equals("学生")) {
				if ((new CheckInfo().isMember("student", idt.getText(), "000") == 2)) {
					file = file + "student.txt";
					deleteUserFromFile(file);
					JOptionPane.showMessageDialog(null, "删除学生成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "此学生不存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (ch.equals("教师")) {
				if ((new CheckInfo().isMember("teacher", idt.getText(), "000") == 2)) {
					file = file + "teacher.txt";
					deleteUserFromFile(file);
					JOptionPane.showMessageDialog(null, "删除教师成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "此教师不存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			} else if (ch.equals("教务员")) {
				if ((new CheckInfo().isMember("administrator", idt.getText(), "000") == 2)) {
					file = file + "administrator.txt";
					deleteUserFromFile(file);
					JOptionPane.showMessageDialog(null, "删除教务员成功", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					JOptionPane.showMessageDialog(null, "此教务员不存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	// 删除用户的通用方法
	private void deleteUserFromFile(String file) {
		ArrayList<String> modifiedContent = new ArrayList<>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String s;
			while ((s = br.readLine()) != null) {
				String[] result = s.split(" ");
				if (result[0].equals(idt.getText())) {
					continue;
				}
				String s1 = String.join(" ", result);
				modifiedContent.add(s1);
			}
			br.close();

			BufferedWriter bw = new BufferedWriter(new FileWriter(file));
			for (String content : modifiedContent) {
				bw.write(content);
				bw.newLine();
			}
			bw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}
}
