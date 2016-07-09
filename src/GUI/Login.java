package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import RFID.Database;

public class Login extends JFrame implements ActionListener {
	private Database database = null;
	private JTextField nameField;
	private JPasswordField passwordField;
	private JButton confirm;
	private JButton cancel;

	public Login() {
		this.setTitle("登录");
		this.setSize(300, 300);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		getContentPane().setSize(300, 400);
		getContentPane().setLayout(null);
		setResizable(false);
		
		JLabel label1 = new JLabel("用户名: ");
		label1.setBounds(40, 50, 50, 25);
		getContentPane().add(label1);
		nameField = new JTextField();
		nameField.setText("admin");
		nameField.setBounds(100, 50, 150, 25);
		nameField.setPreferredSize(new Dimension(50, 20));
		getContentPane().add(nameField);
		JLabel label2 = new JLabel("密码: ");
		label2.setBounds(40, 100, 50, 25);
		getContentPane().add(label2);
		passwordField = new JPasswordField();
		passwordField.setText("admin");
		passwordField.setBounds(100, 100, 150, 25);
		passwordField.setPreferredSize(new Dimension(50, 20));
		getContentPane().add(passwordField);

		confirm = new JButton("登录");
		confirm.setBounds(100, 175, 100, 25);
		getContentPane().add(confirm);
		confirm.addActionListener(this);

		setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - getSize().width) / 2,
				(Toolkit.getDefaultToolkit().getScreenSize().height - getSize().height) / 2);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Login();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == confirm) {
			String name, password;
			try {
				name = nameField.getText();
				password = new String(passwordField.getPassword());
			} catch (Exception ee) {
				JOptionPane.showMessageDialog(this, "Please enter valid information");
				return;
			}

			if (name == null || name.equals("") || password == null || password.equals("")) {
				return;
			}

			if (database == null) {
				database = new Database();
			}

			boolean verification;
			try {
				verification = database.login(name, password);
			} catch (SQLException e1) {
				JOptionPane.showMessageDialog(null, "Database malfunctions");
				return;
			}

			if (verification == true) {
				System.out.println("access granted");
			} else {
				System.out.println("access denied");
				return;
			}

			setVisible(false);
			new DemoCenter(database);
		} else if (e.getSource() == cancel) {
			System.exit(0);
		}

	}
}
