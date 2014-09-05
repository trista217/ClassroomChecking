package domain;

import java.util.ArrayList;

public class query {

	private String startDate;
	private String endDate;
	private String startTime;
	private String endTime;
	private ArrayList<String> room;
	private boolean IsAvaliable;
	
	public query() {
		super();
	}

	
	public query(String startDate, String endDate, String startTime,
			String endTime, ArrayList<String> room, boolean isAvaliable) {
		super();
		this.startDate = startDate;
		this.endDate = endDate;
		this.startTime = startTime;
		this.endTime = endTime;
		this.room = room;
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

	public ArrayList<String> getRoom() {
		return room;
	}

	public void setRoom(ArrayList<String> room) {
		this.room = room;
	}

	public boolean isIsAvaliable() {
		return IsAvaliable;
	}

	public void setIsAvaliable(boolean isAvaliable) {
		IsAvaliable = isAvaliable;
	}

	
}
