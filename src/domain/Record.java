package domain;

public class Record {
	private String date;
	private String startTime;
	private String endTime;
	private String personName;
	private String department;
	private String content;
	private String status;
	private String recordType;
	public String getRecordType() {
		return recordType;
	}

	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}

	private int _id;
	
	public String getDate() {
		return date;
	}
	
	public String getStartTime() {
		return startTime;
	}
	
	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}

	public String getEndTime() {
		return endTime;
	}
	
	public String getPersonName() {
		return personName;
	}
	
	public String getDepartment() {
		return department;
	}
	
	public String getContent() {
		return content;
	}
	
	public String getStatus() {
		return status;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}
