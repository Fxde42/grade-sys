package controller;

import java.awt.AWTEvent;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;

@SuppressWarnings("serial")
public class EditInfo extends JFrame implements ActionListener {
	String id;
	JPanel contain;
	JButton submit;
	JLabel name, inst, birth, pass1, pass2, major;
	JTextField namet, instt, birtht, majort;
	JPasswordField pass1t, pass2t;
	Checkbox check1, check2;
	CheckboxGroup group;
	int flag;

	public EditInfo(String id, int flag) {
		super("修改信息");
		setSize(300, 420);
		setLocation(600, 400);
		this.id = id;
		this.flag = flag;

		contain = new JPanel();
		contain.setLayout(new GridBagLayout());  // 使用 GridBagLayout 布局管理器
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10); // 设置组件间的间隔
		gbc.fill = GridBagConstraints.HORIZONTAL; // 让组件填满所在的单元格

		// 初始化所有组件
		name = new JLabel("姓名");
		birth = new JLabel("生日");
		inst = new JLabel("学院");
		major = new JLabel("专业");
		pass1 = new JLabel("新密码");
		pass2 = new JLabel("确认密码");
		submit = new JButton("提交");

		group = new CheckboxGroup();
		check1 = new Checkbox("男", group, true);
		check2 = new Checkbox("女", group, false);

		instt = new JTextField();
		namet = new JTextField();
		birtht = new JTextField();
		majort = new JTextField();
		pass1t = new JPasswordField();
		pass2t = new JPasswordField();

		// 添加组件到 GridBagLayout 中
		addComponent(name, gbc, 0, 0);
		addComponent(namet, gbc, 1, 0);
		addComponent(check1, gbc, 0, 1);
		addComponent(check2, gbc, 1, 1);
		addComponent(birth, gbc, 0, 2);
		addComponent(birtht, gbc, 1, 2);
		addComponent(inst, gbc, 0, 3);
		addComponent(instt, gbc, 1, 3);
		addComponent(major, gbc, 0, 4);
		addComponent(majort, gbc, 1, 4);
		addComponent(pass1, gbc, 0, 5);
		addComponent(pass1t, gbc, 1, 5);
		addComponent(pass2, gbc, 0, 6);
		addComponent(pass2t, gbc, 1, 6);
		addComponent(submit, gbc, 1, 7);

		submit.addActionListener(this);
		add(contain);
		setVisible(true);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	private void addComponent(java.awt.Component comp, GridBagConstraints gbc, int x, int y) {
		gbc.gridx = x;
		gbc.gridy = y;
		contain.add(comp, gbc);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			if ((instt.getText().equals("")) || (birtht.getText().equals("")) || (namet.getText().equals("")) || (pass1t.getText().equals("")) || (pass2t.getText().equals(""))) {
				JOptionPane.showMessageDialog(null, "信息不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				if (!(pass1t.getText().equals(pass2t.getText()))) {
					JOptionPane.showMessageDialog(null, "新密码与确认密码不同！", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else if (pass1t.getText().length() < 6) {
					JOptionPane.showMessageDialog(null, "密码长度至少为6位！", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					String m = check1.getState() ? "male" : "female";

					if (flag == 0) { // 学生修改信息
						modifyFile("/data/student.txt", m);
					} else if (flag == 1) { // 教师修改信息
						modifyFile("/data/teacher.txt", m);
					} else if (flag == 3) { // 教务员修改信息
						modifyFile("/data/administrator.txt", m);
					}

					JOptionPane.showMessageDialog(null, "修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	private void modifyFile(String filePath, String gender) {
		ArrayList<String> modifiedContent = new ArrayList<>();
		String file = System.getProperty("user.dir") + filePath;

		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String s;
			while ((s = br.readLine()) != null) {
				String[] result = s.split(" ");
				if (result[0].equals(id)) {
					result[1] = pass2t.getText();
					result[2] = namet.getText();
					result[3] = gender;
					result[4] = birtht.getText();
					result[5] = instt.getText();
					result[6] = majort.getText();
				}
				modifiedContent.add(String.join(" ", result));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			for (String line : modifiedContent) {
				bw.write(line);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}
}
