package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import controller.GradeSort;

public class SortGradeFrame extends JFrame implements ActionListener {

	private JPanel contain;
	private JLabel idLabel, passLabel, goodLabel, excellentLabel;
	private JTextField idField, passField, goodField, excellentField;
	private JButton submitButton;

	private int[] result = null;

	public SortGradeFrame() {
		super("输入课程号和成绩标准");
		setSize(300, 300);
		setLocation(600, 400);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		// 主面板布局
		contain = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5); // 设置组件间距

		// 初始化标签和文本框
		idLabel = new JLabel("课程号");
		idField = new JTextField(15);

		passLabel = new JLabel("及格");
		passField = new JTextField(15);

		goodLabel = new JLabel("良好");
		goodField = new JTextField(15);

		excellentLabel = new JLabel("优秀");
		excellentField = new JTextField(15);

		submitButton = new JButton("提交");

		// 布局组件
		gbc.gridx = 0;
		gbc.gridy = 0;
		contain.add(idLabel, gbc);
		gbc.gridx = 1;
		contain.add(idField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		contain.add(passLabel, gbc);
		gbc.gridx = 1;
		contain.add(passField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		contain.add(goodLabel, gbc);
		gbc.gridx = 1;
		contain.add(goodField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		contain.add(excellentLabel, gbc);
		gbc.gridx = 1;
		contain.add(excellentField, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		contain.add(submitButton, gbc);

		submitButton.addActionListener(this);

		// 添加主面板
		add(contain);
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submitButton) {
			// 检查输入是否为空
			if (idField.getText().equals("") || passField.getText().equals("") ||
					goodField.getText().equals("") || excellentField.getText().equals("")) {
				JOptionPane.showMessageDialog(this, "信息不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);
			} else {
				// 验证成绩标准是否有效
				GradeSort gradeSort = new GradeSort(idField.getText(),
						Float.parseFloat(passField.getText()),
						Float.parseFloat(goodField.getText()),
						Float.parseFloat(excellentField.getText()));
				if (gradeSort.isValidate() == 1) {
					JOptionPane.showMessageDialog(this, "成绩标准设置错误！", "提示", JOptionPane.INFORMATION_MESSAGE);
				} else {
					// 检查课程是否存在
					if (gradeSort.hasCourse() == 0) {
						JOptionPane.showMessageDialog(this, "此课程不存在！", "提示", JOptionPane.INFORMATION_MESSAGE);
					} else {
						// 获取成绩统计结果
						this.result = gradeSort.sortGrade();
						showResult();
					}
				}
			}
		}
	}

	// 显示成绩统计结果
	private void showResult() {
		JFrame resultFrame = new JFrame("成绩统计结果");
		resultFrame.setSize(300, 340);
		resultFrame.setLocation(600, 400);

		JPanel resultPanel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		// 创建结果标签和文本框
		JLabel failLabel = new JLabel("不及格");
		JLabel passLabel = new JLabel("及格");
		JLabel goodLabel = new JLabel("良好");
		JLabel excellentLabel = new JLabel("优秀");

		JTextField failField = new JTextField(15);
		JTextField passField = new JTextField(15);
		JTextField goodField = new JTextField(15);
		JTextField excellentField = new JTextField(15);

		// 布局结果面板
		gbc.gridx = 0;
		gbc.gridy = 0;
		resultPanel.add(failLabel, gbc);
		gbc.gridx = 1;
		resultPanel.add(failField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		resultPanel.add(passLabel, gbc);
		gbc.gridx = 1;
		resultPanel.add(passField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		resultPanel.add(goodLabel, gbc);
		gbc.gridx = 1;
		resultPanel.add(goodField, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		resultPanel.add(excellentLabel, gbc);
		gbc.gridx = 1;
		resultPanel.add(excellentField, gbc);

		resultFrame.add(resultPanel);

		// 显示结果
		failField.setText(Integer.toString(result[0]) + "人");
		passField.setText(Integer.toString(result[1]) + "人");
		goodField.setText(Integer.toString(result[2]) + "人");
		excellentField.setText(Integer.toString(result[3]) + "人");

		resultFrame.setVisible(true);
	}

//	public static void main(String[] args) {
//		new SortGradeFrame();
//	}
}
