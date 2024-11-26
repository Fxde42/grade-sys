package controller;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;

import javax.swing.*;

public class DeleteUser extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	// 面板和组件
	private JPanel contain;
	private JLabel idLabel, roleLabel;
	private JTextField idField;
	private JComboBox<String> roleComboBox;
	private JButton submitButton;

	private String filePath = System.getProperty("user.dir") + "/data/";

	public DeleteUser() {
		super("删除用户");
		setSize(300, 340);
		setLocation(600, 400);

		// 使用 GridBagLayout 布局管理器
		contain = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5); // 设置组件的间距

		// 初始化组件
		idLabel = new JLabel("帐号");
		roleLabel = new JLabel("角色");
		idField = new JTextField(15);
		roleComboBox = new JComboBox<>(new String[] { "学生", "教师", "教务员" });
		submitButton = new JButton("提交");

		// 设置布局和组件位置
		gbc.gridx = 0;
		gbc.gridy = 0;
		contain.add(idLabel, gbc);

		gbc.gridx = 1;
		contain.add(idField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		contain.add(roleLabel, gbc);

		gbc.gridx = 1;
		contain.add(roleComboBox, gbc);

		gbc.gridx = 1;
		gbc.gridy = 2;
		contain.add(submitButton, gbc);

		// 添加按钮的事件监听器
		submitButton.addActionListener(this);

		// 将面板添加到框架中
		add(contain);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitButton) {
			String selectedRole = (String) roleComboBox.getSelectedItem();
			String userId = idField.getText().trim();

			if (userId.isEmpty()) {
				JOptionPane.showMessageDialog(this, "请输入用户帐号！", "提示", JOptionPane.INFORMATION_MESSAGE);
				return;
			}

			if (selectedRole.equals("学生")) {
				handleUserDeletion("student", "student.txt");
			} else if (selectedRole.equals("教师")) {
				handleUserDeletion("teacher", "teacher.txt");
			} else if (selectedRole.equals("教务员")) {
				handleUserDeletion("administrator", "administrator.txt");
			}
		}
	}

	// 根据用户类型和文件处理删除操作
	private void handleUserDeletion(String role, String fileName) {
		if (new CheckInfo().isMember(role, idField.getText(), "000") == 2) {
			filePath = filePath + fileName;
			deleteUserFromFile(filePath);
			JOptionPane.showMessageDialog(this, "删除" + role + "成功", "提示", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(this, "此" + role + "不存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
		}
	}

	// 从文件中删除指定的用户
	private void deleteUserFromFile(String file) {
		ArrayList<String> modifiedContent = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(file))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] result = line.split(" ");
				if (!result[0].equals(idField.getText())) {
					modifiedContent.add(String.join(" ", result));
				}
			}

			// 写入修改后的内容
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
				for (String content : modifiedContent) {
					bw.write(content);
					bw.newLine();
				}
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// 处理窗口事件（关闭窗口时的操作）
	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
		}
	}

	public static void main(String[] args) {
		new DeleteUser();
	}
}
