package controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class EditInfo extends JFrame implements ActionListener {
	private String id;
	private JPanel contain;
	private JButton submit;
	private JLabel name, inst, birth, pass1, pass2, major;
	private JTextField namet, instt, birtht, majort;
	private JPasswordField pass1t, pass2t;
	private JRadioButton male, female;
	private ButtonGroup genderGroup;
	private int flag;

	public EditInfo(String id, int flag) {
		super("修改信息");
		this.id = id;
		this.flag = flag;

		// 设置窗口属性
		setSize(350, 400);
		setLocation(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// 初始化面板和布局
		contain = new JPanel();
		contain.setLayout(new GridBagLayout()); // 使用 GridBagLayout 布局管理器
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

		// 性别选择
		male = new JRadioButton("男", true);
		female = new JRadioButton("女", false);
		genderGroup = new ButtonGroup();
		genderGroup.add(male);
		genderGroup.add(female);

		// 输入框
		instt = new JTextField();
		namet = new JTextField();
		birtht = new JTextField();
		majort = new JTextField();
		pass1t = new JPasswordField();
		pass2t = new JPasswordField();

		// 设置组件布局
		addComponent(name, gbc, 0, 0);
		addComponent(namet, gbc, 1, 0);
		addComponent(male, gbc, 0, 1);
		addComponent(female, gbc, 1, 1);
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

		// 按钮点击事件监听
		submit.addActionListener(this);

		// 将面板添加到窗口
		add(contain);
		setVisible(true);
	}

	private void addComponent(Component comp, GridBagConstraints gbc, int x, int y) {
		gbc.gridx = x;
		gbc.gridy = y;
		contain.add(comp, gbc);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			// 校验输入内容
			if ((instt.getText().equals("")) || (birtht.getText().equals("")) || (namet.getText().equals("")) || (pass1t.getText().equals("")) || (pass2t.getText().equals(""))) {
				JOptionPane.showMessageDialog(null, "信息不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				if (!(pass1t.getText().equals(pass2t.getText()))) {
					JOptionPane.showMessageDialog(null, "新密码与确认密码不同！", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else if (pass1t.getText().length() < 6) {
					JOptionPane.showMessageDialog(null, "密码长度至少为6位！", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					String gender = male.isSelected() ? "male" : "female";

					// 根据不同身份修改信息
					if (flag == 0) { // 学生修改信息
						modifyFile("/data/student.txt", gender);
					} else if (flag == 1) { // 教师修改信息
						modifyFile("/data/teacher.txt", gender);
					} else if (flag == 3) { // 教务员修改信息
						modifyFile("/data/administrator.txt", gender);
					}

					JOptionPane.showMessageDialog(null, "修改成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		}
	}

	private void modifyFile(String filePath, String gender) {
		ArrayList<String> modifiedContent = new ArrayList<>();
		String file = System.getProperty("user.dir") + filePath;

		// 读取文件并修改数据
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

		// 写回文件
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
			for (String line : modifiedContent) {
				bw.write(line);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 窗口关闭时的处理
	@Override
	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}

//	public static void main(String[] args) {
//		// 测试界面
//		new EditInfo("testStudentID", 0); // 学生修改信息
//		// new EditInfo("testTeacherID", 1); // 教师修改信息
//	}
}
