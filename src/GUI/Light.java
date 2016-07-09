package GUI;

import java.awt.Color;
import java.awt.EventQueue;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import RFID.Database;
import RFID.People;

import java.awt.Font;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class Light implements ActionListener, RFIDFrame{
	private Database database;
	
	private boolean isAutomatic;
	private int bound_turnOff;
    private int lower_bound_oneThird;
    private int upper_bound_oneThird;
    private int lower_bound_twoThirds;
    private int upper_bound_twoThirds;
    private int bound_turnOn;
    private int currentStudent;
    
	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JTextField textField_5;
	private JPanel lightPanel_1, lightPanel_2, lightPanel_3;
    private JButton btnNewButton;
    private JRadioButton rdbtnNewRadioButton;
    private JRadioButton rdbtnNewRadioButton_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Light window = new Light(new Database());
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
	public Light(Database database) {
		this.database = database;
		initialize();
		new DemoControl(this, database);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setTitle("RFID灯控系统");
		frame.setBounds(100, 100, 544, 444);
		frame.setLocationRelativeTo(null);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("\u706F\u63A7\u7CFB\u7EDF\u6A21\u5F0F");
		lblNewLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel.setBounds(49, 26, 90, 33);
		frame.getContentPane().add(lblNewLabel);
		
        rdbtnNewRadioButton = new JRadioButton("\u81EA\u52A8\u6A21\u5F0F\uFF08\u7531\u706F\u63A7\u7CFB\u7EDF\u81EA\u52A8\u63A7\u5236\u706F\u7684\u5F00\u542F\u548C\u5173\u95ED\uFF09");
		rdbtnNewRadioButton.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		rdbtnNewRadioButton.setBounds(80, 55, 468, 23);
		rdbtnNewRadioButton.setSelected(true);
		rdbtnNewRadioButton.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				textField.setEnabled(true);
				textField_1.setEnabled(true);
				textField_2.setEnabled(true);
				textField_3.setEnabled(true);
				textField_4.setEnabled(true);
				textField_5.setEnabled(true);
			}
		});
		frame.getContentPane().add(rdbtnNewRadioButton);
		
        rdbtnNewRadioButton_1 = new JRadioButton("\u624B\u52A8\u6A21\u5F0F\uFF08\u9700\u8981\u4EBA\u5DE5\u63A7\u5236\u706F\u7684\u5F00\u542F\u548C\u5173\u95ED\uFF09");
		rdbtnNewRadioButton_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		rdbtnNewRadioButton_1.setBounds(80, 80, 310, 23);
		rdbtnNewRadioButton_1.addItemListener(new ItemListener() {
			
			@Override
			public void itemStateChanged(ItemEvent e) {
				textField.setEnabled(false);
				textField_1.setEnabled(false);
				textField_2.setEnabled(false);
				textField_3.setEnabled(false);
				textField_4.setEnabled(false);
				textField_5.setEnabled(false);
			}
		});
		frame.getContentPane().add(rdbtnNewRadioButton_1);
		
		ButtonGroup group = new ButtonGroup();
		group.add(rdbtnNewRadioButton);
		group.add(rdbtnNewRadioButton_1);
		
		JLabel lblNewLabel_1 = new JLabel("\u706F\u63A7\u7CFB\u7EDF\u9608\u503C");
		lblNewLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(49, 149, 90, 33);
		frame.getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("\u5173\u95ED\u6240\u6709\u706F\uFF1A");
		lblNewLabel_2.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_2.setBounds(80, 191, 397, 23);
		frame.getContentPane().add(lblNewLabel_2);
		
		JLabel newLabel_1 = new JLabel("\u4EBA\u6570\u2264");
		newLabel_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		newLabel_1.setBounds(222, 191, 397, 23);
		frame.getContentPane().add(newLabel_1);
		
		JLabel lblNewLabel_3 = new JLabel("\u6253\u5F001/3\u7684\u706F\uFF1A");
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_3.setBounds(80, 224, 98, 15);
		frame.getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("\u2264\u4EBA\u6570\u2264");
		lblNewLabel_4.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_4.setBounds(212, 224, 54, 15);
		frame.getContentPane().add(lblNewLabel_4);
		
		JLabel label = new JLabel("\u6253\u5F002/3\u7684\u706F\uFF1A");
		label.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label.setBounds(80, 249, 98, 15);
		frame.getContentPane().add(label);
		
		JLabel label_1 = new JLabel("\u2264\u4EBA\u6570\u2264");
		label_1.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_1.setBounds(212, 249, 54, 15);
		frame.getContentPane().add(label_1);
		
		JLabel label_2 = new JLabel("\u6253\u5F00\u5168\u90E8\u7684\u706F\uFF1A");
		label_2.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		label_2.setBounds(80, 274, 98, 15);
		frame.getContentPane().add(label_2);
		
		JLabel lblNewLabel_5 = new JLabel("\u2264\u4EBA\u6570");
		lblNewLabel_5.setFont(new Font("微软雅黑", Font.PLAIN, 12));
		lblNewLabel_5.setBounds(212, 274, 54, 15);
		frame.getContentPane().add(lblNewLabel_5);
		
		textField = new JTextField();
		textField.setText("1");
		textField.setBounds(262, 192, 34, 21);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setText("2");
		textField_1.setColumns(10);
		textField_1.setBounds(168, 221, 34, 21);
		frame.getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setText("3");
		textField_2.setColumns(10);
		textField_2.setBounds(262, 221, 34, 21);
		frame.getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setText("4");
		textField_3.setColumns(10);
		textField_3.setBounds(168, 249, 34, 21);
		frame.getContentPane().add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setText("5");
		textField_4.setColumns(10);
		textField_4.setBounds(262, 249, 34, 21);
		frame.getContentPane().add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setText("6");
		textField_5.setColumns(10);
		textField_5.setBounds(168, 274, 34, 21);
		frame.getContentPane().add(textField_5);
		
        btnNewButton = new JButton("\u4FDD\u5B58\u914D\u7F6E");
		btnNewButton.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		btnNewButton.setBounds(189, 342, 149, 33);
        btnNewButton.addActionListener(this);
		frame.getContentPane().add(btnNewButton);
		
		JLabel lightStatusLabel = new JLabel();
		lightStatusLabel.setText("当前状态");
		lightStatusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 15));
		lightStatusLabel.setBounds(320, 148, 90, 33);
		frame.getContentPane().add(lightStatusLabel);
		
        JLabel l1 = new JLabel("Light Group 1");
        l1.setBounds(320, 192, 150, 20);
        frame.getContentPane().add(l1);
        
        JLabel l2 = new JLabel("Light Group 2");
        l2.setBounds(320, 224, 150, 20);
        frame.getContentPane().add(l2);
        
        JLabel l3 = new JLabel("Light Group 3");
        l3.setBounds(320, 257, 150, 20);
        frame.getContentPane().add(l3);
        
        lightPanel_1 = new JPanel();
        lightPanel_1.setBackground(new Color(0x000000));
        lightPanel_1.setBounds(430, 191, 20, 20);
        frame.getContentPane().add(lightPanel_1);
        lightPanel_2 = new JPanel();
        lightPanel_2.setBackground(new Color(0x000000));
        lightPanel_2.setBounds(430, 224, 20, 20);
        frame.getContentPane().add(lightPanel_2);
        lightPanel_3 = new JPanel();
        lightPanel_3.setBackground(new Color(0x000000));
        lightPanel_3.setBounds(430, 257, 20, 20);
        frame.getContentPane().add(lightPanel_3);
        
        frame.setVisible(true);
        isAutomatic = true;
    	bound_turnOff = 1;
        lower_bound_oneThird = 2;
        upper_bound_oneThird = 3;
        lower_bound_twoThirds = 4;
        upper_bound_twoThirds = 5;
        bound_turnOn = 6;
	}

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNewButton){
            int bound_turnOff, lower_bound_oneThird, upper_bound_oneThird,
                    lower_bound_twoThirds, upper_bound_twoThirds, bound_turnOn;
            
            if (rdbtnNewRadioButton.isSelected() == true)
                isAutomatic = true;
            else {
                isAutomatic = false;
                JOptionPane.showMessageDialog(null, "保存配置成功");
                manipulateLights();
                return;
            }
            
            try {
                bound_turnOff = Integer.valueOf(textField.getText());
                lower_bound_oneThird = Integer.valueOf(textField_1.getText());
                upper_bound_oneThird = Integer.valueOf(textField_2.getText());
                lower_bound_twoThirds = Integer.valueOf(textField_3.getText());
                upper_bound_twoThirds = Integer.valueOf(textField_4.getText());
                bound_turnOn = Integer.valueOf(textField_5.getText());
            }catch(Exception e_){
                JOptionPane.showMessageDialog(null,"输入错误，请输入整数");
                return;
            }
            
            if (0> bound_turnOff || bound_turnOff >= lower_bound_oneThird || lower_bound_oneThird > upper_bound_oneThird
                    || upper_bound_oneThird >= lower_bound_twoThirds || lower_bound_twoThirds > upper_bound_twoThirds||
                    upper_bound_twoThirds >= bound_turnOn){
                JOptionPane.showMessageDialog(null, "非法的条件，请检查填写的人数范围");
                return;
            }

            this.bound_turnOff = bound_turnOff;
            this.lower_bound_oneThird = lower_bound_oneThird;
            this.upper_bound_oneThird = upper_bound_oneThird;
            this.lower_bound_twoThirds = lower_bound_twoThirds;
            this.upper_bound_twoThirds = upper_bound_twoThirds;
            this.bound_turnOn = bound_turnOn;
            
            JOptionPane.showMessageDialog(null, "保存配置成功");
            manipulateLights();
        }
    }
    
    public void manipulateLights(){
    	if (!isAutomatic) {
    		lightPanel_1.setBackground(new Color(222, 222, 222));
    		lightPanel_2.setBackground(new Color(222, 222, 222));
            lightPanel_3.setBackground(new Color(222, 222, 222));
    		return;
    	}
        if (currentStudent <= bound_turnOff){
            lightPanel_1.setBackground(new Color(0,0,0));
            lightPanel_2.setBackground(new Color(0,0,0));
            lightPanel_3.setBackground(new Color(0,0,0));
        } else if (currentStudent >= lower_bound_oneThird && currentStudent <= upper_bound_oneThird){
            lightPanel_1.setBackground(new Color(255,255,255));
            lightPanel_2.setBackground(new Color(0,0,0));
            lightPanel_3.setBackground(new Color(0,0,0));
        } else if (currentStudent >= lower_bound_twoThirds && currentStudent <= upper_bound_twoThirds){
            lightPanel_1.setBackground(new Color(255,255,255));
            lightPanel_2.setBackground(new Color(255,255,255));
            lightPanel_3.setBackground(new Color(0,0,0));
        } else if (currentStudent >= bound_turnOn) {
            lightPanel_1.setBackground(new Color(255,255,255));
            lightPanel_2.setBackground(new Color(255,255,255));
            lightPanel_3.setBackground(new Color(255,255,255));
        }
    }

	@Override
	public void notifyPeopleListChanged(ArrayList<People> list) {
		currentStudent = 0;
		for (People people : list) {
			if (people.getRfid().length() > 0)
				currentStudent++;
		}
		manipulateLights();
	}
}
