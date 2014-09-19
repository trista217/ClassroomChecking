package utilInter;

import java.text.ParseException;
import java.util.Date;

public interface dealWithTimeInter {
	public String calOverlap(String timeString1, String timeString2) throws ParseException;
	public Boolean compareTime(String timeString1, String timeString2);
	public int calDateDuration(String startDateString, String endDateString) throws ParseException;
	public String dateIncrement(String dateString, int i) throws ParseException;
	public Date stringToDate(String timeString);
	public String dateToString(Date timeDate);
	public float dateStringToFloat(String timeString);
	
}
