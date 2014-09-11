package domain;

import util.dealWithTime;
import android.os.Parcel;
import android.os.Parcelable;

/*
 * Record类为详情页的占用记录信息。
 * _id用以标记条目顺序，实际未使用
 */

public class Record implements Parcelable{
	//private String date;
	private String startTime;
	private String endTime;
	private String personName;
	private String department;
	private String content;
	private String status;
	private int _id;
	private static final dealWithTime d = new dealWithTime();
	
	public Record() {
		super();
	}
	
	public Record(Record r) {
		//this.date = r.getDate();
		this.startTime = r.getStartTime();
		this.endTime = r.getEndTime();
		this.personName = r.getPersonName();
		this.department = r.getDepartment();
		this.content = r.getContent();
		this.status = r.getStatus();
		this._id = r.get_id();
	}
	
	/*public String getDate() {
		return date;
	}*/
	
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

	/*public void setDate(String date) {
		this.date = date;
	}*/

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
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) {
		//out.writeString(this.date);
		out.writeString(this.startTime);
		out.writeString(this.endTime);
		out.writeString(this.personName);
		out.writeString(this.department);
		out.writeString(this.content);
		out.writeString(this.status);
		out.writeInt(this._id);
	}
	
	public static final Parcelable.Creator<Record> CREATOR = new Parcelable.Creator<Record>() {
		@Override
		public Record createFromParcel(Parcel in) {
			Record r = new Record();
			//r.setDate(in.readString());
			r.setStartTime(in.readString());
			r.setEndTime(in.readString());
			r.setPersonName(in.readString());
			r.setDepartment(in.readString());
			r.setContent(in.readString());
			r.setStatus(in.readString());
			r.set_id(in.readInt());
			return r;
		}
		
		@Override
		public Record[] newArray(int size) {
			return new Record[size];
		}
	};
}
