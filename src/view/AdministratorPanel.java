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

import controller.AddUser;
import controller.DeleteUser;
import controller.EditInfo;

@SuppressWarnings("serial")
public class AdministratorPanel extends JFrame implements ActionListener {
	JButton deleteUser, addUser, selfInfo, logoutButton;
	JPanel contain;
	String idd;

	public AdministratorPanel(String idd) {
		super("系统管理员");
		this.idd = idd;
		setLocation(500, 300);
		setSize(300, 340);

		contain = new JPanel();
		contain.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;

		selfInfo = new JButton("修改信息");
		addUser = new JButton("增加用户");
		deleteUser = new JButton("删除用户");
		logoutButton = new JButton("退出登录");

		gbc.gridx = 0;
		gbc.gridy = 0;
		contain.add(selfInfo, gbc);

		gbc.gridy = 1;
		contain.add(addUser, gbc);

		gbc.gridy = 2;
		contain.add(deleteUser, gbc);

		gbc.gridy = 3;
		contain.add(logoutButton, gbc);

		selfInfo.addActionListener(this);
		addUser.addActionListener(this);
		deleteUser.addActionListener(this);
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
				new MainFrame();
			}
		});

		add(contain);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addUser) {
			new AddUser();
		} else if (e.getSource() == deleteUser) {
			new DeleteUser();
		} else if (e.getSource() == selfInfo) {
			new EditInfo(idd, 3);
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