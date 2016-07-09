package GUI;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import com.mysql.fabric.xmlrpc.base.Data;

import RFID.AttendanceRecord;
import RFID.Database;
import RFID.People;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.ScrollPane;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.JScrollPane;

public class CheckingIn implements ActionListener, RFIDFrame {
	private Database database;

	private boolean startCheck;
	private JLabel stat;

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JList<String> list_y;
	private JList<String> list_n;
	private JTable table;
	private DefaultTableModel tableModel;
	private JButton startButton;
	private JButton endButton;
	private JButton searchButton;
	private JTextField course_name;

	private ArrayList<People> names;
	private ArrayList<People> peoples;
	private ArrayList<People> notCome;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CheckingIn window = new CheckingIn(new Database());
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
	public CheckingIn(Database database) {
		this.database = database;
		initialize();
		new DemoControl(this, database);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	@SuppressWarnings("deprecation")
	private void initialize() {
		frame = new JFrame();
		frame.setVisible(true);
		frame.setTitle("RFID考勤系统");
		frame.setSize(544, 444);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.getContentPane().setLayout(null);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		tabbedPane.setBounds(30, 28, 484, 350);
		frame.getContentPane().add(tabbedPane);

		JPanel panel = new JPanel();
		tabbedPane.addTab("实时考勤", null, panel, null);
		panel.setLayout(null);

		JLabel label = new JLabel("\u5F53\u524D\u8BFE\u7A0B\uFF1A");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label.setBounds(42, 24, 70, 15);
		panel.add(label);

		course_name = new JTextField("物联网引论");
		course_name.setBounds(120, 24, 300, 25);
		panel.add(course_name);

		startButton = new JButton("\u5F00\u59CB\u8003\u52E4");
		startButton.setEnabled(true);
		startCheck = false;
		startButton.addActionListener(this);
		startButton.setBounds(102, 67, 100, 25);
		panel.add(startButton);

		endButton = new JButton("\u7ED3\u675F\u8003\u52E4");
		endButton.setEnabled(false);
		endButton.addActionListener(this);
		endButton.setBounds(262, 67, 100, 25);
		panel.add(endButton);

		DefaultListModel<String> listModel1 = new DefaultListModel<>();
		list_y = new JList<String>(listModel1);
		JScrollPane scrollPane1 = new JScrollPane(list_y);
		scrollPane1.setBounds(110, 100, 120, 160);
		panel.add(scrollPane1);

		DefaultListModel<String> listModel2 = new DefaultListModel<>();
		list_n = new JList<String>(listModel2);
		JScrollPane scrollPane2 = new JScrollPane(list_n);
		scrollPane2.setBounds(300, 100, 120, 160);
		panel.add(scrollPane2);

		JLabel label_1 = new JLabel("\u5DF2\u5230\u4EBA\u5458\uFF1A");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_1.setBounds(42, 129, 85, 15);
		panel.add(label_1);

		JLabel label_2 = new JLabel("\u672A\u5230\u4EBA\u5458\uFF1A");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label_2.setBounds(232, 129, 85, 15);
		panel.add(label_2);

		stat = new JLabel();
		stat.setBounds(42, 280, 371, 15);
		panel.add(stat);

		JPanel panel_1 = new JPanel();
		tabbedPane.addTab("考勤记录查询", null, panel_1, null);
		panel_1.setLayout(null);

		JLabel lblNewLabel_1 = new JLabel(
				"\u653E\u7F6ERFID\u6216\u8F93\u5165\u67E5\u8BE2\u6761\u4EF6\u4EE5\u5B8C\u6210\u67E5\u8BE2");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		lblNewLabel_1.setBounds(78, 29, 300, 16);
		panel_1.add(lblNewLabel_1);

		JLabel lblRfid = new JLabel();
		lblRfid.setText("学号：");
		lblRfid.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		lblRfid.setBounds(122, 55, 80, 16);
		panel_1.add(lblRfid);

		textField = new JTextField();
		textField.setBounds(184, 53, 150, 21);
		panel_1.add(textField);
		textField.setColumns(10);

		JLabel label1 = new JLabel("\u59D3\u540D\uFF1A");
		label1.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		label1.setBounds(122, 89, 42, 16);
		panel_1.add(label1);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(184, 87, 150, 21);
		panel_1.add(textField_1);

		searchButton = new JButton("\u67E5\u8BE2");
		searchButton.setBounds(163, 118, 97, 23);
		searchButton.addActionListener(this);
		panel_1.add(searchButton);

		Object[][] cellData = {};
		String[] headers = { "课程名", "上课时间", "出勤时间" };
		tableModel = new DefaultTableModel(cellData, headers);
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setBounds(20, 164, 420, 135);
		panel_1.add(scrollPane);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == startButton) {
			startButton.setEnabled(false);
			endButton.setEnabled(true);
			names = new ArrayList<>();
			try {
				names = database.getAll();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			((DefaultListModel<String>) list_n.getModel()).clear();
			((DefaultListModel<String>) list_y.getModel()).clear();
			for (People people : names)
				((DefaultListModel<String>) list_n.getModel()).addElement(people.getName());
			stat.setText("");
			startCheck = true;
		} else if (e.getSource() == endButton) {
			endButton.setEnabled(false);
			startButton.setEnabled(true);
			startCheck = false;
			Date date = new Date();
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String checkTime = simpleDateFormat.format(date);
			for (People people : peoples) {
				try {
					database.insertAttendance(course_name.getText(), people.getRfid(), checkTime,
							people.getEntranceTime());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			for (People people : notCome) {
				try {
					database.insertAttendance(course_name.getText(), people.getRfid(), checkTime, "");
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		} else if (e.getSource() == searchButton) {
			if (textField.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "请输入学号");
				return;
			}
			if (textField_1.getText().length() == 0) {
				JOptionPane.showMessageDialog(null, "请输入姓名");
				return;
			}
			tableModel.setRowCount(0);
			People people = new People();
			try {
				people = database.queryPeopleInformation(textField.getText(), textField_1.getText());
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			if (people.getRfid().length() == 0)
				return;
			try {
				ArrayList<AttendanceRecord> records = database.getAttendanceByRfid(people.getRfid());
				for (AttendanceRecord record : records) {
					Vector vector = new Vector<>();
					vector.add(record.getCourseName());
					vector.add(record.getCheckTime());
					vector.add(record.getComeTime().equals("") ? "未出勤" : record.getComeTime());
					tableModel.addRow(vector);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void notifyPeopleListChanged(ArrayList<People> list) {
		if (!startCheck)
			return;
		peoples = list;
		notCome = new ArrayList<>();
		for (People people : names) {
			boolean found = false;
			for (People people2 : peoples)
				if (people.getRfid().equals(people2.getRfid())) {
					found = true;
					break;
				}
			if (!found)
				notCome.add(people);
		}
		((DefaultListModel<String>) list_n.getModel()).clear();
		for (People people : notCome)
			((DefaultListModel<String>) list_n.getModel()).addElement(people.getName());
		((DefaultListModel<String>) list_y.getModel()).clear();
		for (People people : peoples)
			((DefaultListModel<String>) list_y.getModel()).addElement(people.getName());
		stat.setText("应当" + names.size() + "人，实到" + peoples.size() + "人，未到" + notCome.size() + "人，出勤率"
				+ 100 * peoples.size() / names.size() + "%");
	}
}
