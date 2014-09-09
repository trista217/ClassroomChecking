package domain;


public class Result {
	private String room;
	private String date;
	private String bestOverlap;
	private String bestAvailableStartTime;
	private String bestAvailableEndTime;
	private int _id;
	
	public Result()
	{
		super();
	}
	
	public Result(String room, String bestOverlap, String bestAvailableStartTime, String bestAvailableEndTime,int _id)
	{
		this.room = room;
		this.bestOverlap = bestOverlap;
		this.bestAvailableStartTime = bestAvailableStartTime;
		this.bestAvailableEndTime = bestAvailableEndTime;
		this._id = _id;
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
	
	public void printResult()
	{
		System.out.println(room + " " + date + " " + bestOverlap);
	}
}