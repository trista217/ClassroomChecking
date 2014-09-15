package domain;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class query implements Parcelable{

	private String startDate;
	private int duration;
	private String startTime;
	private String endTime;
	private ArrayList<String> roomId;
	private ArrayList<String> type;
	private ArrayList<String> number;
	private boolean isAvaliable;

	public query() {
		this.roomId = new ArrayList<String>();
		this.type = new ArrayList<String>();
		this.number = new ArrayList<String>();
	}
	
	public query(query q) {
		this.startDate = q.getStartDate();
		this.duration = q.getDuration();
		this.startTime = q.getStartTime();
		this.endTime = q.getEndTime();
		this.roomId = q.getRoomId();
		this.type = q.getType();
		this.number = q.getNumber();
		this.isAvaliable = q.isAvaliable();
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
	
	public ArrayList<String> getClassroomNameList() {
		ArrayList<String> ClassroomNameList = new ArrayList<String>();
		for(String room:roomId) {
			String ClassroomName = new String();
			if (room.equals("SDDT"))
				ClassroomName = "舜德大厅";
			else if(room.startsWith("SD"))
			{
				ClassroomName = room.replaceAll("SD", "舜德");
			}else {
				ClassroomName = room.replaceAll("WL", "伟伦");
			}
			ClassroomNameList.add(ClassroomName);
		}
		return ClassroomNameList;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) 
    {
        out.writeString(this.startDate);
        out.writeInt(this.duration);
        out.writeString(this.startTime);
        out.writeString(this.endTime);
        out.writeStringList(this.roomId);
        out.writeStringList(this.type);
        out.writeStringList(this.number);
        out.writeByte((byte) (this.isAvaliable?1:0));
    }
	
	public static final Parcelable.Creator<query> CREATOR = new Parcelable.Creator<query>() {
		@Override
		public query createFromParcel(Parcel in) {
			query q = new query();
			q.setStartDate(in.readString());
			q.setDuration(in.readInt());
			q.setStartTime(in.readString());
			q.setEndTime(in.readString());
			q.setRoomId(new ArrayList<String>(in.createStringArrayList()));
			q.setType(new ArrayList<String>(in.createStringArrayList()));
			q.setNumber(new ArrayList<String>(in.createStringArrayList()));
			q.setAvaliable(in.readByte()!=0);
			return q;
		}
		
		@Override
		public query[] newArray(int size) {
			return new query[size];
		}
	};
}
