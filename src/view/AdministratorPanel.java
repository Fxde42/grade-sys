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
	// 定义按钮和面板
	JButton deleteUser, addUser, selfInfo, logoutButton;
	JPanel contain;
	String idd;

	public AdministratorPanel(String idd) {
		super("系统管理员");
		this.idd = idd;
		setLocation(500, 300);
		setSize(300, 380);

		// 初始化面板和布局
		contain = new JPanel();
		contain.setLayout(new GridBagLayout()); // 使用 GridBagLayout 布局
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(15, 10, 15, 10);  // 设置组件间距
		gbc.fill = GridBagConstraints.HORIZONTAL; // 水平填充按钮

		// 初始化按钮
		selfInfo = new JButton("修改信息");
		addUser = new JButton("增加用户");
		deleteUser = new JButton("删除用户");
		logoutButton = new JButton("退出登录");

		// 设置按钮添加到布局面板中
		gbc.gridx = 0;
		gbc.gridy = 0;
		contain.add(selfInfo, gbc);

		gbc.gridy = 1;
		contain.add(addUser, gbc);

		gbc.gridy = 2;
		contain.add(deleteUser, gbc);

		gbc.gridy = 3;
		contain.add(logoutButton, gbc);

		// 添加按钮的事件监听器
		selfInfo.addActionListener(this);
		addUser.addActionListener(this);
		deleteUser.addActionListener(this);
		logoutButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();  // 关闭当前窗口
				new MainFrame();  // 返回主界面
			}
		});

		// 设置窗口属性
		add(contain);
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		enableEvents(AWTEvent.WINDOW_EVENT_MASK);
	}

	// 按钮点击事件处理
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == addUser) {
			new AddUser();
		} else if (e.getSource() == deleteUser) {
			new DeleteUser();
		} else if (e.getSource() == selfInfo) {
			new EditInfo(idd, 3);  // 系统管理员修改信息
		}
	}

	// 处理窗口关闭事件
	public void processWindowEvent(WindowEvent e) {
		if (e.getID() == WindowEvent.WINDOW_CLOSING) {
			this.dispose();
			setVisible(false);
			System.exit(0);
		}
	}
}
