package domain;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class Results implements Parcelable{
	private query q;
	private ArrayList<Result> resultList;
	
	public Results()
	{
		super();
	}
	
	public Results(query q, ArrayList<Result> resultList)
	{
		//this.q = q; 这里确定不会出bug？我改了一下，要是没冲突就用我的吧
		this.q = new query(q);
		this.resultList = new ArrayList<Result>(resultList);
	}
	
	public query getQuery() {
		return this.q;
	}
	
	public String getStartDate()
	{
		return q.getStartDate();
	}
	
	/*public String getEndDate()
	{
		return q.getEndDate();
	}*/
	
	public String getStartTime()
	{
		return q.getStartTime();
	}
	
	public String getEndTime()
	{
		return q.getEndTime();
	}
	
	public ArrayList<Result> getAllResult()
	{
		return resultList;
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) 
    {
        out.writeParcelable(this.q,flags);
        out.writeTypedList(this.resultList);
    }
	
	public static final Parcelable.Creator<Results> CREATOR = new Parcelable.Creator<Results>() {
		@Override
		public Results createFromParcel(Parcel in) {
			query q = in.readParcelable(query.class.getClassLoader());
			return new Results(new query(q), new ArrayList<Result>(in.createTypedArrayList(Result.CREATOR)));
		}
		
		@Override
		public Results[] newArray(int size) {
			return new Results[size];
		}
	};
}
