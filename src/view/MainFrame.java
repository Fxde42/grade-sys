package view;

import java.awt.AWTEvent;
import java.awt.Choice;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.CheckInfo;

import java.awt.*;
import java.io.InputStream;

public class MainFrame extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	JTextField idTextField;
	JPasswordField passwdTextField;
	JLabel idLabel, passwdLabel;
	Choice chooice;
	JButton logon;
	JPanel contain;

	int count = 0;

	public MainFrame() {
		super("账号登陆");
		setLocation(500, 300);
		setSize(500, 378);

		// 使用自定义的BackgroundPanel
		BackgroundPanel backgroundPanel = new BackgroundPanel("data/Uestc.jpg");
		backgroundPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		idLabel = new JLabel("ID号");
		passwdLabel = new JLabel("密码");
		idTextField = new JTextField(15);
		passwdTextField = new JPasswordField(15);
		logon = new JButton("登陆");
		chooice = new Choice();
		chooice.addItem("学生");
		chooice.addItem("教师");
		chooice.addItem("系统管理员");

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
		backgroundPanel.add(new JLabel("角色:"), gbc);
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

	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		new MainFrame();
	}
}