package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class query implements Serializable{

	private String startDate;
	private int duration;
	private String startTime;
	private String endTime;
	private ArrayList<String> roomId;
	private ArrayList<String> type;
	private ArrayList<String> number;
	private boolean isAvaliable;

	public query() {
		super();
	}

	public query(String startDate, int duration, String startTime,
			String endTime, ArrayList<String> roomId, ArrayList<String> type,
			ArrayList<String> number, boolean isAvaliable) {
		super();
		this.startDate = startDate;
		this.duration = duration;
		this.startTime = startTime;
		this.endTime = endTime;
		this.roomId = roomId;
		this.type = type;
		this.number = number;
		this.isAvaliable = isAvaliable;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public int getDuration() {
		return duration;
	}

	public void setEndDate(int duration) {
		this.duration = duration;
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

	public ArrayList<String> getRoomId() {
		return roomId;
	}

	public void setRoomId(ArrayList<String> roomId) {
		this.roomId = roomId;
	}

	public ArrayList<String> getType() {
		return type;
	}

	public void setType(ArrayList<String> type) {
		this.type = type;
	}

	public ArrayList<String> getNumber() {
		return number;
	}

	public void setNumber(ArrayList<String> number) {
		this.number = number;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public void setAvaliable(boolean isAvaliable) {
		this.isAvaliable = isAvaliable;
	}

	public boolean isAvaliable() {
		return isAvaliable;
	}

	public void setIsAvaliable(boolean isAvaliable) {
		this.isAvaliable = isAvaliable;
	}

}
