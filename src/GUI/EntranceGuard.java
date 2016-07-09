package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JToggleButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.sound.sampled.*;

import RFID.Database;
import RFID.People;

import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.JLayeredPane;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JTabbedPane;
import javax.swing.JTable;

public class EntranceGuard extends Applet implements RFIDFrame {
	private Database database;

	private JTable table;
	private Object[][] cellData;

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel result_rfid;
	private JLabel result_name;
	private JLabel result_college;
	private JLabel result_dorm;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					EntranceGuard window = new EntranceGuard(new Database());
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public EntranceGuard(Database database) {
		this.database = database;
		initialize();
		new DemoControl(this, database);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("RFID门禁系统");
		frame.setVisible(true);
		frame.setBounds(100, 100, 744, 444);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(50, 20, 650, 350);
		frame.getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		// panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		tabbedPane.addTab("记录统计", null, panel, null);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("\u5F53\u524D\u72B6\u6001\uFF1A\u6B63\u5E38");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblNewLabel.setBounds(35, 21, 124, 19);
		panel.add(lblNewLabel);

		cellData = new Object[0][4];
		String[] headers = { "RFID编号", "姓名", "进入时间", "状态" };
		DefaultTableModel tableModel = new DefaultTableModel(cellData, headers) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) { 
            	 return false;
            }
		};
		table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(50, 50, 500, 222);
		panel.add(scrollPane);

		/*
		 * JButton btnNewButton = new
		 * JButton("\u5386\u53F2\u8BB0\u5F55\u67E5\u770B");
		 * btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		 * btnNewButton.setBounds(300, 259, 124, 23); panel.add(btnNewButton);
		 */

		JPanel panel_1 = new JPanel();
		panel_1.setLayout(null);
		// panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(-31, 33, 468, 310);
		tabbedPane.addTab("个人资料统计", null, panel_1, null);

		JLabel label = new JLabel(
				"\u653E\u7F6ERFID\u6216\u8F93\u5165\u67E5\u8BE2\u6761\u4EF6\u4EE5\u5B8C\u6210\u67E5\u8BE2");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		label.setBounds(195, 36, 259, 15);
		panel_1.add(label);

		JLabel label_1 = new JLabel();
		label_1.setText("学号：");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_1.setBounds(210, 69, 87, 15);
		panel_1.add(label_1);

		textField = new JTextField();
		textField.setColumns(10);
		textField.setBounds(250, 66, 150, 21);
		panel_1.add(textField);

		JLabel label_2 = new JLabel("\u59D3\u540D\uFF1A");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_2.setBounds(210, 110, 87, 15);
		panel_1.add(label_2);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(250, 107, 150, 21);
		panel_1.add(textField_1);

		JButton button = new JButton("\u67E5\u8BE2");
		button.setBounds(258, 158, 93, 23);
		button.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				if (textField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入学号");
					return;
				}
				if (textField_1.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "请输入姓名");
					return;
				}
				result_rfid.setText("");
				result_name.setText("");
				result_college.setText("");
				result_dorm.setText("");
				try {
					People people = database.queryPeopleInformation(textField.getText(), textField_1.getText());
					result_rfid.setText(people.getRfid());
					result_name.setText(people.getName());
					result_college.setText(people.getCollege());
					result_dorm.setText(people.getRfid().length() == 0 ? "" : people.getDormNum() + "公寓");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
		panel_1.add(button);

		JLabel label_3 = new JLabel("\u67E5\u8BE2\u7ED3\u679C\uFF1A");
		label_3.setBounds(78, 205, 85, 15);
		panel_1.add(label_3);

		JLabel label_4 = new JLabel("RFID\u7F16\u53F7\uFF1A");
		label_4.setBounds(88, 235, 75, 15);
		panel_1.add(label_4);
		
		result_rfid = new JLabel("");
		result_rfid.setBounds(160, 235, 200, 15);
		panel_1.add(result_rfid);

		JLabel label_5 = new JLabel("\u59D3\u540D\uFF1A");
		label_5.setBounds(88, 260, 75, 15);
		panel_1.add(label_5);
		
		result_name = new JLabel("");
		result_name.setBounds(160, 260, 200, 15);
		panel_1.add(result_name);

		JLabel label_6 = new JLabel("\u5B66\u9662\uFF1A");
		label_6.setBounds(357, 235, 75, 15);
		panel_1.add(label_6);
		
		result_college = new JLabel();
		result_college.setBounds(429, 235, 200, 15);
		panel_1.add(result_college);

		JLabel label_7 = new JLabel("\u516C\u5BD3\uFF1A");
		label_7.setBounds(357, 260, 75, 15);
		panel_1.add(label_7);
		
		result_dorm = new JLabel();
		result_dorm.setBounds(429, 260, 200, 15);
		panel_1.add(result_dorm);
	}
	
	private void addToModel(People newPeople) {
		Object[] newData = new Object[4];
		newData[0] = newPeople.getRfid();
		newData[1] = newPeople.getName().equals("") ? "陌生人" : newPeople.getName();
		newData[2] = newPeople.getEntranceTime();
		newData[3] = newPeople.getName().equals("") ? "报警" : 
					 newPeople.getDormNum() != 7 ? "提示" : "正常";
		((DefaultTableModel) table.getModel()).addRow(newData);
	}
	
	@Override
	public void notifyPeopleListChanged(ArrayList<People> list) {
		((DefaultTableModel)table.getModel()).setRowCount(0);
		for (int i = list.size() - 1; i >= 0; i--) {
			People people = list.get(i);
			addToModel(people);
		}
		if (list.get(list.size() - 1).getRfid().length() == 0) {
			new Music("files\\alarm.au");
			return;
		}
		if (list.get(list.size() - 1).getDormNum() != 7) {
			new Music("files\\notify.au");
			return;
		}
	}
}
