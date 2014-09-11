package dao;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.dealWithTime;
import android.util.Log;
import domain.query;

public class generateQueryUrl {

	private static final String BASE_URL = "http://crm.sem.tsinghua.edu.cn/psc/CRMPRD/EMPLOYEE/CRM/s/WEBLIB_TZ_JSCX.TZ_JSCX_SELT.FieldFormula.IScript_GetRmRes?";

	public Map<String,ArrayList<String>> genQueryUrl(query userQuery) throws ParseException {
		Log.v("gen urls", "gen urls");
		String startTimeStr = userQuery.getStartTime();
		String endTimeStr = userQuery.getEndTime();
		String startDateStr = userQuery.getStartDate();
		int duration = userQuery.getDuration();
		ArrayList<String> roomIdList = userQuery.getRoomId();
		Map<String,ArrayList<String>> finalQueryUrl = new HashMap<String, ArrayList<String>>();
		dealWithTime timeDealer = new dealWithTime();

		// roomIdList.add("WL401");
		// roomIdList.add("SD401");

		// "rm_no=SD301&res_day=20140814&s_time=0600&e_time=1830"

		
		for (String roomId : roomIdList) {
			ArrayList<String> temp = new ArrayList<String>();
			for (int i = 0; i < duration; i++) {
				String curDateStr = timeDealer.dateIncrement(startDateStr, i);
				String tmpQuery = genTmpQuery(roomId, curDateStr, startTimeStr,
						endTimeStr);
				temp.add(tmpQuery);
			}
			finalQueryUrl.put(roomId, temp);
		}
		return finalQueryUrl;
	}
	
	private String genTmpQuery(String roomId, String curDateStr,
			String startTimeStr, String endTimeStr) {
		return BASE_URL + "rm_no=" + roomId + "&res_day=" + curDateStr
				+ "&s_time=" + startTimeStr + "&e_time" + endTimeStr;
	}


}
