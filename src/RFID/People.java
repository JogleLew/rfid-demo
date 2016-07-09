package RFID;

import java.text.SimpleDateFormat;
import java.util.Date;

public class People {
	private String name;
	private String rfid;
	private String studentId;
	private String college;
	private String entranceTime;
	private int dormNum;

	public People() {
		name = "";
		rfid = "";
		studentId = "";
		college = "";
		dormNum = 0;
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.entranceTime = simpleDateFormat.format(date);
	}

	public People(String name, String rfid, String studentId, String college, int dormNum) {
		this.name = name;
		this.rfid = rfid;
		this.studentId = studentId;
		this.college = college;
		this.dormNum = dormNum;
		Date date = new Date();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		this.entranceTime = simpleDateFormat.format(date);
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getCollege() {
		return college;
	}

	public void setCollege(String college) {
		this.college = college;
	}

	public int getDormNum() {
		return dormNum;
	}

	public void setDormNum(int dormNum) {
		this.dormNum = dormNum;
	}

	public String getEntranceTime() {
		return entranceTime;
	}

	public void setEntranceTime(String entranceTime) {
		this.entranceTime = entranceTime;
	}
}
