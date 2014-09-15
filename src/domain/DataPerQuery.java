package domain;

import java.util.ArrayList;

import android.os.Parcel;
import android.os.Parcelable;

public class DataPerQuery implements Parcelable{
	private Results re;
	private ArrayList<ResultDetails> rd;
	
	public DataPerQuery() {
		super();
	}
	
	public DataPerQuery(DataPerQuery dpq) {
		this.re = new Results(dpq.getResults());
		this.rd = new ArrayList<ResultDetails>(dpq.getResultDetails());		
	}
	
	public DataPerQuery(Results re, ArrayList<ResultDetails> rd) {
		this.re = new Results(re);
		this.rd = new ArrayList<ResultDetails>(rd); 
	}
	
	public Results getResults() {
		return re;
	}
	
	public void setResults(Results re) {
		this.re = new Results(re);
	}
	
	public ArrayList<ResultDetails> getResultDetails() {
		return rd;
	}
	
	public void setResultDetails(ArrayList<ResultDetails> rd) {
		this.rd = new ArrayList<ResultDetails>(rd);
	}
	
	@Override
	public int describeContents() {
		return 0;
	}
	
	@Override
	public void writeToParcel(Parcel out, int flags) 
    {
        out.writeParcelable(this.re,flags);
        out.writeTypedList(this.rd);
    }
	
	public static final Parcelable.Creator<DataPerQuery> CREATOR = new Parcelable.Creator<DataPerQuery>() {
		@Override
		public DataPerQuery createFromParcel(Parcel in) {
			Results re = in.readParcelable(Results.class.getClassLoader());
			return new DataPerQuery(re, in.createTypedArrayList(ResultDetails.CREATOR));
		}
		
		@Override
		public DataPerQuery[] newArray(int size) {
			return new DataPerQuery[size];
		}
	};
}