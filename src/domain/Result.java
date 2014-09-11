package domain;

import android.os.Parcel;
import android.os.Parcelable;


public class Result implements Parcelable{
	private String room;
	private String overlap;
	private String nearestUsedTime;
	private int _id;
	
	public Result()
	{
		super();
	}
	
	public Result(String room, String overlap, String nearestUsedTime, query q, int _id)
	{
		this.room = room;
		this.overlap = overlap;
		this.nearestUsedTime = nearestUsedTime;
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

	public String getOverlap() {
		return overlap;
	}

	public void setOverlap(String overlap) {
		this.overlap = overlap;
	}

	public String getNearestUsedTime() {
		return nearestUsedTime;
	}

	public void setNearestUsedTime(String nearestUsedTime) {
		this.nearestUsedTime = nearestUsedTime;
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
		out.writeString(this.overlap);
		out.writeString(this.nearestUsedTime);
		out.writeInt(this._id);
	}
	
	public static final Parcelable.Creator<Result> CREATOR = new Parcelable.Creator<Result>() {
		@Override
		public Result createFromParcel(Parcel in) {
			Result r = new Result();
			r.setRoom(in.readString());
			r.setOverlap(in.readString());
			r.setNearestUsedTime(in.readString());
			r.set_id(in.readInt());
			return r;
		}
		
		@Override
		public Result[] newArray(int size) {
			return new Result[size];
		}
	};
}
