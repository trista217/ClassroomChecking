package domain;

public class query {

	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private String roomNum;
	private String type;
	private int number;
	private boolean IsAvaliable;
	
	public query() {
		super();
	}

	public query(String startDate, String endDate, String startTime,
			String endTime, String roomNum, String type, int number,
			boolean isAvaliable) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roomNum = roomNum;
		this.type = type;
		this.number = number;
		IsAvaliable = isAvaliable;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public String getRoomNum() {
		return roomNum;
	}

	public void setRoomNum(String roomNum) {
		this.roomNum = roomNum;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public boolean isIsAvaliable() {
		return IsAvaliable;
	}

	public void setIsAvaliable(boolean isAvaliable) {
		IsAvaliable = isAvaliable;
	}
	
}
