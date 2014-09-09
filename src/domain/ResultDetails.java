package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class ResultDetails implements Serializable{
	private String room;
	private String type;
	private String ClassroomName;
	private String num;
	private ArrayList<Record> recordList;
	
	public ResultDetails()
	{
		super();
	}
	
	public ResultDetails(String room, String type, String ClassroomName, String num, ArrayList<Record> recordList)
	{
		this.ClassroomName = ClassroomName;
		this.room = room;
		this.type = type;
		this.num = num;
		this.recordList = new ArrayList<Record>(recordList);
	}

	public String getRoom() {
		return room;
	}

	public String getType() {
		return type;
	}

	public String getClassroomName() {
		return ClassroomName;
	}

	public String getNum() {
		return num;
	}

	public ArrayList<Record> getRecordList() {
		return recordList;
	}
}
