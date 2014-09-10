package domain;


public class Result {
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
	
	public void setRoom(String roomNum)
	{
		this.room = room;
	}
	
	public void setOverlap(String overlap)
	{
		this.overlap = overlap;
	}
	
	public void setNearestUsedTime(String nearestUsedTime)
	{
		this.nearestUsedTime = nearestUsedTime;
	}
	
	public void setID(int _id)
	{
		this._id = _id;
	}
	
	public String getRoom()
	{
		return room;
	}
	
	public String getOverlap()
	{
		return overlap;
	}
	
	public String getNearestUsedTime()
	{
		return nearestUsedTime;
	}
	
	public int getID()
	{
		return _id;
	}
}
