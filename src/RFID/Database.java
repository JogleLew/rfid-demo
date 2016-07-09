package RFID;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;

public class Database {

	private Connection conn = null;

	private String url = "jdbc:mysql://hk-5.hostpark.cn:3306/s2402334_trade?"
			+ "user=s2402334_user&password=19950805&useUnicode=true&characterEncoding=UTF8";

	private String sql;

	/**
	 * MySQL的JDBC URL编写方式：jdbc:mysql://主机名称：连接端口/数据库的名称?参数=值
	 * 避免中文乱码要指定useUnicode和characterEncoding 执行数据库操作之前要在数据库管理系统上创建一个数据库，名字自己定，
	 * 下面语句之前就要先创建javademo数据库
	 */
	private boolean init() {
		try {
			Class.forName("com.mysql.jdbc.Driver");// 动态加载mysql驱动
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
		System.out.println("成功加载MySQL驱动程序");

		try {
			conn = DriverManager.getConnection(url);// 一个Connection代表一个数据库连接
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public Database() {

		if (init() == false) {
			JOptionPane.showMessageDialog(null, "Database's down");
			System.exit(1);
		}
	}

	public void printAllRecords() throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = "SELECT * FROM student";
		ResultSet rs = stmt.executeQuery(sql);// 还有executeUpdate语句会返回一个受影响的行数，如果返回-1就没有成功
		while (rs.next()) {
			System.out.println(rs.getString(1) + "\t" + rs.getString(2) + "\t" + rs.getString(3));// 如果返回的是int类型可以用getInt()
		}
		conn.close();
	}

	public ArrayList<String> getAllRFID() throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = String.format("SELECT RFIDSerialNumber FROM student");
		ResultSet rs = stmt.executeQuery(sql);

		ArrayList<String> result = new ArrayList<>();
		while (rs.next()) {
			result.add(rs.getString(1));
		}
		return result;
	}
	
	public ArrayList<People> getAll() throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = String.format("SELECT * FROM student");
		ResultSet rs = stmt.executeQuery(sql);

		ArrayList<People> result = new ArrayList<>();
		while (rs.next()) {
			result.add(new People(rs.getString(2), rs.getString(1), rs.getString(3), rs.getString(4), rs.getInt(5)));
		}
		return result;
	}

	public ArrayList<AttendanceRecord> getAttendanceByRfid(String rfid) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = String.format("SELECT * FROM attendance WHERE rfid = \'%s\'", rfid);
		ResultSet rs = stmt.executeQuery(sql);

		ArrayList<AttendanceRecord> result = new ArrayList<>();
		while (rs.next()) {
			result.add(new AttendanceRecord(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4)));
		}
		return result;
	}
	
	public People searchByRFID(String RFID) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = String.format("SELECT * FROM student WHERE RFIDSerialNumber = \'%s\'", RFID);
		ResultSet rs = stmt.executeQuery(sql);

		if (rs.next()) {
			return new People(rs.getString(2), rs.getString(1), rs.getString(3), rs.getString(4), rs.getInt(5));
		} else {
			return new People();
		}
	}

	public static void main(String[] args) {
		Database database = new Database();
		try {
			database.printAllRecords();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean login(String name, String password) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = String.format("SELECT * FROM user WHERE username = \'%s\' AND password = \'%s\'", name, password);
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {
			return true;
		} else {
			return false;
		}
	}

	public People queryPeopleInformation(String stu_id, String name) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = String.format("SELECT * FROM student WHERE studentNumber = \'%s\' AND name = \'%s\'", stu_id,
				name);
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()) {
			return new People(rs.getString(2), rs.getString(1), rs.getString(3), rs.getString(4), rs.getInt(5));
		} else {
			return new People();
		}
	}
	
	public void insertAttendance(String courseName, String rfid, String checkTime, String comeTime) throws SQLException {
		Statement stmt = conn.createStatement();
		String sql = String.format("INSERT INTO attendance VALUES (\'%s\', \'%s\', \'%s\', \'%s\')", 
				courseName, rfid, checkTime, comeTime);
		stmt.executeUpdate(sql);
	}
}
