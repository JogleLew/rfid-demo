package RFID;

public class AttendanceRecord {
	private String courseName;
	private String rfid;
	private String checkTime;
	private String comeTime;
	
	public AttendanceRecord(String courseName, String rfid, String checkTime, String comeTime) {
		this.courseName = courseName;
		this.rfid = rfid;
		this.checkTime = checkTime;
		this.comeTime = comeTime;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getRfid() {
		return rfid;
	}

	public void setRfid(String rfid) {
		this.rfid = rfid;
	}

	public String getCheckTime() {
		return checkTime;
	}

	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}

	public String getComeTime() {
		return comeTime;
	}

	public void setComeTime(String comeTime) {
		this.comeTime = comeTime;
	}
	
}
