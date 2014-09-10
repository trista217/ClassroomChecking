package domain;

import java.io.Serializable;
import java.util.ArrayList;

public class Results implements Serializable{
	private query q;
	private ArrayList<Result> resultList;
	
	public Results()
	{
		super();
	}
	
	public Results(query q, ArrayList<Result> resultList)
	{
		this.q = q;
		this.resultList = new ArrayList<Result>(resultList);
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
}
