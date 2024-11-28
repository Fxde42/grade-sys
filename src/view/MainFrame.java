package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import controller.CheckInfo;

public class MainFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JTextField idTextField;
	JPasswordField passwdTextField;
	JLabel idLabel, passwdLabel;
	JComboBox<String> chooice;
	JButton logon;

	int count = 0;

	public MainFrame() {
		super("账号登陆");
		setLocation(500, 300);
		setSize(400, 300);  // 适当调整窗口大小

		// 使用自定义的BackgroundPanel
		BackgroundPanel backgroundPanel = new BackgroundPanel("data/Uestc.jpg");
		backgroundPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);  // 设置组件之间的间距

		idLabel = new JLabel("ID号");
		passwdLabel = new JLabel("密码");
		idTextField = new JTextField(20);  // 设置合适的输入框宽度
		passwdTextField = new JPasswordField(20);  // 设置合适的输入框宽度
		logon = new JButton("登陆");

		// 使用JComboBox代替Choice
		chooice = new JComboBox<>();
		chooice.addItem("学生");
		chooice.addItem("教师");
		chooice.addItem("系统管理员");

		// 设置布局管理器的对齐方式
		gbc.gridx = 0;
		gbc.gridy = 0;
		backgroundPanel.add(idLabel, gbc);

		gbc.gridx = 1;
		backgroundPanel.add(idTextField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		backgroundPanel.add(passwdLabel, gbc);

		gbc.gridx = 1;
		backgroundPanel.add(passwdTextField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		backgroundPanel.add(new JLabel("身份:"), gbc);

		gbc.gridx = 1;
		backgroundPanel.add(chooice, gbc);

		gbc.gridx = 1;
		gbc.gridy = 3;
		backgroundPanel.add(logon, gbc);

		logon.addActionListener(this);
		add(backgroundPanel);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == logon) {
			String ch = (String) chooice.getSelectedItem();
			if (ch.equals("学生")) {
				if ((new CheckInfo().isMember("student", idTextField.getText(),
						new String(passwdTextField.getPassword()))) == 1) {
					setVisible(false);
					new StudentsPanel(idTextField.getText());
				} else {
					count += 1;
					if (count <= 5) {
						JOptionPane.showMessageDialog(null, "无此用户，或者密码输入错误！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
					}
					if (count > 5) {
						JOptionPane.showMessageDialog(null, "错误次数超过5次！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
						this.dispose();
						setVisible(false);
						System.exit(0);
					}
				}
			} else if (ch.equals("教师")) {
				if ((new CheckInfo().isMember("teacher", idTextField.getText(),
						new String(passwdTextField.getPassword(), 0,
								passwdTextField.getPassword().length))) == 1) {
					setVisible(false);
					new TeachersPanel(idTextField.getText());
				} else {
					count += 1;
					if (count <= 5) {
						JOptionPane.showMessageDialog(null, "无此用户，或者密码输入错误！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
					}
					if (count > 5) {
						JOptionPane.showMessageDialog(null, "错误次数超过5次！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
						this.dispose();
						setVisible(false);
						System.exit(0);
					}
				}
			} else if (ch.equals("系统管理员")) {
				if ((new CheckInfo().isMember("administrator", idTextField
						.getText(), new String(passwdTextField.getPassword(),
						0, passwdTextField.getPassword().length))) == 1) {
					setVisible(false);
					new AdministratorPanel(idTextField.getText());
				} else {
					count += 1;
					if (count <= 5) {
						JOptionPane.showMessageDialog(null, "无此用户，或者密码输入错误！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
					}
					if (count > 5) {
						JOptionPane.showMessageDialog(null, "错误次数超过5次！",
								"错误", JOptionPane.INFORMATION_MESSAGE);
						this.dispose();
						setVisible(false);
						System.exit(0);
					}
				}
			}
		}
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}
