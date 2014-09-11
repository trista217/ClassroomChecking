package domain;

import android.os.Parcel;
import android.os.Parcelable;

/*Result类对应查询结果页的每条条目；
 * room为房间ID，date为日期，bestOverlap为当天最长空闲时段长度，bestAvailableStartTime及bestAvailbleEndTime为最长空闲时段的起止时间；
 * _id用以标记条目顺序，实际未使用
 */

public class Result implements Parcelable{
	private String room;//Id
	private String date;
	private String bestOverlap;//**小时**分
	private String bestAvailableStartTime;//char(4)
	private String bestAvailableEndTime;//char(4)
	private int _id;
	
	public Result()
	{
		super();
	}
	
	public Result(String room, String date, String bestOverlap, String bestAvailableStartTime, String bestAvailableEndTime, int _id)
	{
		this.room = room;
		this.date = date;
		this.bestOverlap = bestOverlap;
		this.bestAvailableStartTime = bestAvailableStartTime;
		this.bestAvailableEndTime = bestAvailableEndTime;
		this._id = _id;
	}

	public String getClassroomName() {
		String ClassroomName;
		if (room.equals("SDDT"))
			ClassroomName = "舜德大厅";
		else
		{
			ClassroomName = room.replaceAll("SD", "舜德");
			ClassroomName = room.replaceAll("WL", "伟伦");
		}
		return ClassroomName;
	}
	
	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBestOverlap() {
		return bestOverlap;
	}

	public void setBestOverlap(float bestOverlap) {
		int hour = (int) Math.floor(bestOverlap);
		int min = Math.round((bestOverlap - hour) * 60);
		this.bestOverlap = hour + "小时" + min + "分";
	}
	
	public void setBestOverlap(String bestOverlap) {
		this.bestOverlap = bestOverlap;
	}

	public String getBestAvailableStartTime() {
		return bestAvailableStartTime;
	}

	public void setBestAvailableStartTime(String bestAvailableStartTime) {
		this.bestAvailableStartTime = bestAvailableStartTime;
	}

	public String getBestAvailableEndTime() {
		return bestAvailableEndTime;
	}

	public void setBestAvailableEndTime(String bestAvailableEndTime) {
		this.bestAvailableEndTime = bestAvailableEndTime;
	}

	public int get_id() {
		return _id;
	}

	public void set_id(int _id) {
		this._id = _id;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeString(this.room);
		out.writeString(this.date);
		out.writeString(this.bestOverlap);
		out.writeString(this.bestAvailableStartTime);
		out.writeString(this.bestAvailableEndTime);
		out.writeInt(this._id);
	}
	
	public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
		@Override
		public Result createFromParcel(Parcel in) {
			Result r = new Result();
			r.setRoom(in.readString());
			r.setDate(in.readString());
			r.setBestOverlap(in.readString());
			r.setBestAvailableStartTime(in.readString());
			r.setBestAvailableEndTime(in.readString());
			r.set_id(in.readInt());
			return r;
		}
		
		@Override
		public Result[] newArray(int size) {
			return new Result[size];
		}
	};
}
