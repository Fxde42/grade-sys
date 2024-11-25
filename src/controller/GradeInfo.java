package controller;

import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class GradeInfo extends JFrame {
	private static final long serialVersionUID = 1L;
	JPanel contain;
	JTextArea list;
	String id;

	public GradeInfo(String id) {
		super("课程");
		this.id = id;
		setSize(600, 400);
		setLocation(600, 400);

		contain = new JPanel();
		contain.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = 0;

		list = new JTextArea();
		list.setEditable(false);
		list.setText("课程号\t课程名\t教师工号\t教师姓名\t学号\t学生姓名\t成绩\n");
		contain.add(list, gbc);

		String path = System.getProperty("user.dir") + "/data/grade";
		List<String> files = new ArrayList<>();
		File file = new File(path);
		File[] tempList = file.listFiles();

		for (File tempFile : tempList) {
			if (tempFile.isFile()) {
				files.add(tempFile.toString());
			}
		}

		try {
			for (String filePath : files) {
				BufferedReader br = new BufferedReader(new FileReader(filePath));
				String s;
				while ((s = br.readLine()) != null) {
					String[] result = s.split(" ");
					if (result[4].equals(id)) {
						gbc.gridy++;
						JTextArea data = new JTextArea(result[0] + "\t" + result[1] + "\t" + result[2] + "\t" + result[3] + "\t" + result[4] + "\t" + result[5] + "\t" + result[6]);
						data.setEditable(false);
						contain.add(data, gbc);
					}
				}
				br.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		add(contain);
		setVisible(true);
	}
}