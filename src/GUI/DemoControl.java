package GUI;

import RFID.Database;
import RFID.People;
import RFID.RFIDObject;
import RFID.RFIDReader;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

/**
 * Created by HarveyYan on 16/6/13.
 */
public class DemoControl implements ActionListener {
	private JButton add;
	private JButton addUnknown;
	private JButton delete;
	private JButton clear;
	private JPanel outerPanel;
	private JTable table;
	private DefaultTableModel tableModel;
	private Object[][] tableData;

	private Database database;
	private ArrayList<People> peoples = new ArrayList<>();

	private RFIDFrame parentFrameRef;

	public DemoControl(RFIDFrame frame, Database database) {
		this.parentFrameRef = frame;
		this.database = database;
		initFrame();
	}

	private void initFrame() {
		JFrame frame = new JFrame();
		frame.setTitle("模拟控制台");
		frame.setSize(500, 400);

		JPanel buttonPanel = new JPanel();
		outerPanel = new JPanel();
		outerPanel.setLayout(new BoxLayout(outerPanel, BoxLayout.Y_AXIS));
		buttonPanel.setSize(400, 100);
		add = new JButton("随机增加一名学生");
		addUnknown = new JButton("增加一个陌生人");
		delete = new JButton("删除");
		delete.setEnabled(false);
		delete.addActionListener(this);
		clear = new JButton("清空");
		add.addActionListener(this);
		addUnknown.addActionListener(this);
		clear.addActionListener(this);
		buttonPanel.add(add);
		buttonPanel.add(addUnknown);
		buttonPanel.add(delete);
		buttonPanel.add(clear);
		outerPanel.add(buttonPanel);
		frame.getContentPane().add(outerPanel, BorderLayout.NORTH);
		
		String[] columnNames = {"姓名", "RFID编号", "学号"};
		tableData = new Object[0][3];
		tableModel = new DefaultTableModel(tableData, columnNames) {
			private static final long serialVersionUID = 1L;
			public boolean isCellEditable(int row, int column) {
			    return false;
			}
		};
		table = new JTable(tableModel);
		table.addMouseListener(new MouseListener() {
			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub
				delete.setEnabled(true);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub
				
			}
		});
		JScrollPane scroll = new JScrollPane(table);
		scroll.setPreferredSize(new Dimension(100, 400));
		frame.getContentPane().add(scroll, BorderLayout.CENTER);
		
		/*listModel = new DefaultListModel<People>();
		table = new JTable();
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JScrollPane scroll = new JScrollPane(list);
		scroll.setPreferredSize(new Dimension(100, 400));
		frame.getContentPane().add(scroll, BorderLayout.CENTER);*/

		frame.setResizable(false);
		frame.setVisible(true);
	}

	private void refreshModel(People newPeople) {
		Object[] newData = new Object[3];
		newData[0] = newPeople.getName().equals("") ? "陌生人" : newPeople.getName();
		newData[1] = newPeople.getRfid();
		newData[2] = newPeople.getStudentId();
		tableModel.addRow(newData);
		//table.invalidate();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == add) {
			ArrayList<String> RFIDs = new ArrayList<>();
			try {
				RFIDs = database.getAllRFID();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			while (RFIDs.size() > 0) {
				Random random = new Random();
				int c = random.nextInt(RFIDs.size());
				boolean flag = false;
				for (People people : peoples) {
					if (people.getRfid().equals(RFIDs.get(c))) {
						flag = true;
						break;
					}
				}
				if (flag) {
					RFIDs.remove(c);
					continue;
				}
				People newPeople;
				try {
					newPeople = database.searchByRFID(RFIDs.get(c));
					peoples.add(newPeople);
					refreshModel(newPeople);
					System.out.println("new people: " + newPeople.getName() + " " + newPeople.getRfid() + " " + newPeople.getStudentId());
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				break;
			}
		} else if (e.getSource() == addUnknown) {
			People newPeople = new People();
			peoples.add(newPeople);
			refreshModel(newPeople);
			System.out.println("new stranger ");
		} else if (e.getSource() == delete) {
			int choose = table.getSelectedRow();
			if (choose == -1)
				return;
			peoples.remove(choose);
			tableModel.removeRow(choose);
			delete.setEnabled(false);
		} else if (e.getSource() == clear) {
			peoples.clear();
			tableModel.setRowCount(0);
			delete.setEnabled(false);
		}
		parentFrameRef.notifyPeopleListChanged(peoples);
	}
}

interface RFIDFrame {
	void notifyPeopleListChanged(ArrayList<People> list);
}