package util;

import android.annotation.SuppressLint;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class dealWithTime {
	@SuppressLint("SimpleDateFormat")
	private static final SimpleDateFormat SDF = new SimpleDateFormat("HHmm");

	public String calOverlap(String timeString1, String timeString2)
			throws ParseException {
		long timeDiff = calTimeDiff(timeString1, timeString2);
		long timeDiffHour = timeDiff / (60 * 60 * 1000);
		long timeDiffMin = timeDiff / (1000 * 60) - timeDiffHour * 60;
		String timeDiffHourStr;
		String timeDiffMinStr;

		if (Math.abs(timeDiffHour) < 10) {
			timeDiffHourStr = "0" + Math.abs(timeDiffHour);
		} else {
			timeDiffHourStr = "" + Math.abs(timeDiffHour);
		}

		if (Math.abs(timeDiffMin) < 10) {
			timeDiffMinStr = "0" + Math.abs(timeDiffMin);
		} else {
			timeDiffMinStr = "" + Math.abs(timeDiffMin);
		}

		String timeDiffStr = timeDiffHourStr + timeDiffMinStr;
		Date overlapDate = SDF.parse(timeDiffStr);
		String overlapString = dateToString(overlapDate);
		return overlapString;
	}

	public Boolean compareTime(String timeString1, String timeString2) {
		// true: 1 > 2
		Boolean result;
		long timeDiff = calTimeDiff(timeString1, timeString2);
		if (timeDiff > 0) {
			result = true;
		} else {
			result = false;
		}
		return result;
	}

	public Date stringToDate(String timeString) {
		Date timeDate = null;
		try {
			timeDate = SDF.parse(timeString);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return timeDate;
	}

	public String dateToString(Date timeDate) {
		String timeString = null;
		timeString = SDF.format(timeDate);
		return timeString;
	}
	
	public float dateStringToFloat(String timeString) {
		Long timeLong = calTimeDiff(timeString, "0000"); 
		int timeHour = (int)(timeLong / (60 * 60 * 1000));
		int timeMinInt = (int)(timeLong / (1000 * 60) - timeHour * 60);
		float timeMinHour = (float) (timeMinInt / 60.0);
		timeMinHour = (float) (Math.round(timeMinHour * 100)) / 100;
		float dateFloat = (float) (timeHour * 1.0 + timeMinHour);		
		return dateFloat;
	}

	private long calTimeDiff(String timeString1, String timeString2) {
		Date timeDate1 = stringToDate(timeString1);
		Date timeDate2 = stringToDate(timeString2);
		long timeDiff = timeDate1.getTime() - timeDate2.getTime();
		return timeDiff;
	}

}
