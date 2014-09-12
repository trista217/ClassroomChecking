package domain;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

/*
 * ResultDetails封装了详情页的所有信息，包括房间基本信息及该房间某天所有占用记录
 * room为房间ID，ClassroomName为房间名称（中文），type为房间类型，num为房间人数
 * recordList存有房间占用记录
 */

public class ResultDetails implements Parcelable{
	private String room;
	private String type;
	private String classroomName;
	private String num;
	private ArrayList<Record> recordList;
	
	public ResultDetails()
	{
		super();
	}
	
	public ResultDetails(ResultDetails rd)
	{
		this.classroomName = rd.getClassroomName();
		this.room = rd.getRoom();
		this.type = rd.getType();
		this.num = rd.getNum();
		this.recordList = new ArrayList<Record>(rd.getRecordList());
	}
	
	public ResultDetails(String room, String type, String ClassroomName, String num, ArrayList<Record> recordList)
	{
		this.classroomName = ClassroomName;
		this.room = room;
		this.type = type;
		this.num = num;
		this.recordList = new ArrayList<Record>(recordList);
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getClassroomName() {
		return classroomName;
	}

	public void setClassroomName(String classroomName) {
		this.classroomName = classroomName;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public ArrayList<Record> getRecordList() {
		return recordList;
	}

	public void setRecordList(ArrayList<Record> recordList) {
		//this.recordList = recordList; //稍作修改
		this.recordList = new ArrayList<Record>(recordList);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) 
    {
        out.writeString(this.room);
        out.writeString(this.type);
        out.writeString(this.classroomName);
        out.writeString(this.num);
        out.writeTypedList(this.recordList);
    }
	
	public static final Parcelable.Creator<ResultDetails> CREATOR = new Parcelable.Creator<ResultDetails>() {
		@Override
		public ResultDetails createFromParcel(Parcel in) {
			ResultDetails rd = new ResultDetails();
			rd.setRoom(in.readString());
			rd.setType(in.readString());
			rd.setClassroomName(in.readString());
			rd.setClassroomName(in.readString());
			rd.setRecordList(in.createTypedArrayList(Record.CREATOR));
			return rd;
		}
		
		@Override
		public ResultDetails[] newArray(int size) {
			return new ResultDetails[size];
		}
	};

}
