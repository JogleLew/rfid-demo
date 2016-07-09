package GUI;

import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import RFID.Database;

public class DemoCenter extends JFrame implements ActionListener {
	private Database database;
	private JButton demoLight, demoCheckIn, demoEntrance;

	public DemoCenter(Database database) {
		this.setTitle("RFID系统列表");
		this.setSize(300, 300);
		this.setLocationRelativeTo(null);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setResizable(false);
		
		getContentPane().setLayout(null);
		demoLight = new JButton("RFID灯控系统");
		demoLight.setBounds(50, 150, 200, 25);
		demoLight.addActionListener(this);
		demoCheckIn = new JButton("RFID考勤系统");
		demoCheckIn.setBounds(50, 100, 200, 25);
		demoCheckIn.addActionListener(this);
		demoEntrance = new JButton("RFID门禁系统");
		demoEntrance.setBounds(50, 50, 200, 25);
		demoEntrance.addActionListener(this);

		getContentPane().add(demoLight);
		getContentPane().add(demoCheckIn);
		getContentPane().add(demoEntrance);

		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
		setVisible(true);
		this.database = database;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == demoLight) {
			new Light(database);
		} else if (e.getSource() == demoCheckIn) {
			new CheckingIn(database);
		} else if (e.getSource() == demoEntrance) {
			new EntranceGuard(database);
		}

	}

}
