package controller;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

@SuppressWarnings("serial")
public class AddUser extends JFrame implements ActionListener {
	// 界面组件
	private JPanel contain;
	private JLabel idLabel, nameLabel, birthdayLabel, instituteLabel, majorLabel;
	private JTextField idField, nameField, birthdayField, instituteField, majorField;
	private JRadioButton maleRadioButton, femaleRadioButton;
	private JButton submitButton;
	private JComboBox<String> userTypeComboBox;
	private ButtonGroup genderGroup;

	private String filePath = System.getProperty("user.dir") + "/data/";

	public AddUser() {
		super("添加用户");
		setSize(350, 400);
		setLocation(600, 400);

		// 初始化组件
		contain = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5); // 设置组件间距

		idLabel = new JLabel("帐号");
		nameLabel = new JLabel("姓名");
		birthdayLabel = new JLabel("生日");
		instituteLabel = new JLabel("学院");
		majorLabel = new JLabel("专业");

		maleRadioButton = new JRadioButton("男", true);
		femaleRadioButton = new JRadioButton("女");

		// 使用 ButtonGroup 确保只能选择一个性别
		genderGroup = new ButtonGroup();
		genderGroup.add(maleRadioButton);
		genderGroup.add(femaleRadioButton);

		submitButton = new JButton("提交");
		userTypeComboBox = new JComboBox<>(new String[]{"学生", "教师", "教务员"});

		idField = new JTextField();
		nameField = new JTextField();
		birthdayField = new JTextField();
		instituteField = new JTextField();
		majorField = new JTextField();

		// 布局设置
		gbc.fill = GridBagConstraints.HORIZONTAL;

		gbc.gridx = 0;
		gbc.gridy = 0;
		contain.add(idLabel, gbc);
		gbc.gridx = 1;
		contain.add(idField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		contain.add(nameLabel, gbc);
		gbc.gridx = 1;
		contain.add(nameField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		contain.add(birthdayLabel, gbc);
		gbc.gridx = 1;
		contain.add(birthdayField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		contain.add(instituteLabel, gbc);
		gbc.gridx = 1;
		contain.add(instituteField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 4;
		contain.add(majorLabel, gbc);
		gbc.gridx = 1;
		contain.add(majorField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 5;
		contain.add(maleRadioButton, gbc);
		gbc.gridx = 1;
		contain.add(femaleRadioButton, gbc);

		gbc.gridx = 0;
		gbc.gridy = 6;
		contain.add(userTypeComboBox, gbc);

		gbc.gridx = 1;
		contain.add(submitButton, gbc);

		// 按钮点击事件
		submitButton.addActionListener(this);

		// 添加面板到窗口
		add(contain);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitButton) {
			// 验证输入
			if (idField.getText().isEmpty() || nameField.getText().isEmpty() ||
					birthdayField.getText().isEmpty() || instituteField.getText().isEmpty() || majorField.getText().isEmpty()) {
				JOptionPane.showMessageDialog(this, "信息不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				String userType = (String) userTypeComboBox.getSelectedItem();
				if (userType.equals("学生")) {
					handleUserAddition("student.txt", "学生");
				} else if (userType.equals("教师")) {
					handleUserAddition("teacher.txt", "教师");
				} else {
					handleUserAddition("administrator.txt", "教务员");
				}
			}
		}
	}

	private void handleUserAddition(String fileName, String userType) {
		// 检查是否已存在此用户
		if (new CheckInfo().isMember(userType, idField.getText(), nameField.getText()) == 2) {
			JOptionPane.showMessageDialog(this, "此" + userType + "已经存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
		} else {
			filePath = filePath + fileName;

			ArrayList<String> modifiedContent = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
				String line;
				while ((line = br.readLine()) != null) {
					modifiedContent.add(line);
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			String gender = maleRadioButton.isSelected() ? "male" : "female";
			String user = idField.getText() + " " + "123456" + " " + nameField.getText() + " " + gender + " " +
					birthdayField.getText() + " " + instituteField.getText() + " " + majorField.getText();
			modifiedContent.add(user);

			try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath))) {
				for (String entry : modifiedContent) {
					bw.write(entry);
					bw.newLine();
				}
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			JOptionPane.showMessageDialog(this, "成功添加一个" + userType + "！", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// 关闭窗口时调用
	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
		}
	}

	public static void main(String[] args) {
		new AddUser();
	}
}