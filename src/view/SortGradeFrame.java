package view;

import java.awt.AWTEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import controller.GradeSort;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

@SuppressWarnings("serial")
public class SortGradeFrame extends JFrame implements ActionListener {

	JPanel contain;
	JLabel id, pass, good, excellent;
	JTextField idt, passt, goodt, excellentt;

	JButton submit;

	int[] result = null;

	public SortGradeFrame() {
		super("输入课程号和成绩标准");
		setSize(300, 300);
		setLocation(600, 400);
		contain = new JPanel();
		contain.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		id = new JLabel("课程号");
		idt = new JTextField(15);

		pass = new JLabel("及格");
		passt = new JTextField(15);
		good = new JLabel("良好");
		goodt = new JTextField(15);
		excellent = new JLabel("优秀");
		excellentt = new JTextField(15);

		submit = new JButton("提交");

		gbc.gridx = 0;
		gbc.gridy = 0;
		contain.add(id, gbc);
		gbc.gridx = 1;
		contain.add(idt, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		contain.add(pass, gbc);
		gbc.gridx = 1;
		contain.add(passt, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		contain.add(good, gbc);
		gbc.gridx = 1;
		contain.add(goodt, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		contain.add(excellent, gbc);
		gbc.gridx = 1;
		contain.add(excellentt, gbc);

		gbc.gridx = 1;
		gbc.gridy = 4;
		contain.add(submit, gbc);

		submit.addActionListener(this);
		add(contain);
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == submit) {
			if ((idt.getText().equals("")) || (passt.getText().equals("")) || (goodt.getText().equals("")) || (excellentt.getText().equals(""))) {
				JOptionPane.showMessageDialog(null, "信息不能为空！", "提示",
						JOptionPane.INFORMATION_MESSAGE);

			} else if (new GradeSort(idt.getText(), Float.parseFloat(passt.getText()),
					Float.parseFloat(goodt.getText()), Float.parseFloat(excellentt.getText())).isValidate() == 1) {

				JOptionPane.showMessageDialog(null, "成绩标准设置错误！", "提示",
						JOptionPane.INFORMATION_MESSAGE);

			} else {
				GradeSort gradeSort = new GradeSort(idt.getText(), Float.parseFloat(passt.getText()),
						Float.parseFloat(goodt.getText()), Float.parseFloat(excellentt.getText()));

				if (gradeSort.hasCourse() == 0) {
					JOptionPane.showMessageDialog(null, "此课程不存在！", "提示",
							JOptionPane.INFORMATION_MESSAGE);
				} else {
					this.result = gradeSort.sortGrade();
					showResult();
				}
			}
		}
	}

	void showResult() {
		JFrame fm = new JFrame("成绩统计结果");
		fm.setSize(300, 340);
		JPanel contain = new JPanel();
		fm.setLocation(600, 400);
		contain.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);

		JLabel fail = new JLabel("不及格");
		JLabel pass = new JLabel("及格");
		JLabel good = new JLabel("良好");
		JLabel excellent = new JLabel("优秀");

		JTextField failt = new JTextField(15);
		JTextField passt = new JTextField(15);
		JTextField goodt = new JTextField(15);
		JTextField excellentt = new JTextField(15);

		gbc.gridx = 0;
		gbc.gridy = 0;
		contain.add(fail, gbc);
		gbc.gridx = 1;
		contain.add(failt, gbc);

		gbc.gridx = 0;
		gbc.gridy = 1;
		contain.add(pass, gbc);
		gbc.gridx = 1;
		contain.add(passt, gbc);

		gbc.gridx = 0;
		gbc.gridy = 2;
		contain.add(good, gbc);
		gbc.gridx = 1;
		contain.add(goodt, gbc);

		gbc.gridx = 0;
		gbc.gridy = 3;
		contain.add(excellent, gbc);
		gbc.gridx = 1;
		contain.add(excellentt, gbc);

		fm.add(contain);

		failt.setText(Integer.toString(this.result[0]) + "人");
		passt.setText(Integer.toString(this.result[1]) + "人");
		goodt.setText(Integer.toString(this.result[2]) + "人");
		excellentt.setText(Integer.toString(this.result[3]) + "人");

		fm.setVisible(true);
	}
}