package dao;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.dealWithTime;
import util.utilFactory;
import utilInter.dealWithTimeInter;
import android.util.Log;
import daoInter.generateQueryUrlInter;
import domain.query;

public class generateQueryUrl implements generateQueryUrlInter{

	private static final String BASE_URL = "http://crm.sem.tsinghua.edu.cn/psc/CRMPRD/EMPLOYEE/CRM/s/WEBLIB_TZ_JSCX.TZ_JSCX_SELT.FieldFormula.IScript_GetRmRes?";

	public Map<String, ArrayList<String>> genQueryUrl(query userQuery)
			throws ParseException {
		Log.v("log:DAO:generateQueryUrl", "Start to generate query urls.");
		String startTimeStr = userQuery.getStartTime();
		String endTimeStr = userQuery.getEndTime();
		String startDateStr = userQuery.getStartDate();
		int duration = userQuery.getDuration();
		ArrayList<String> roomIdList = userQuery.getRoomId();
		Map<String, ArrayList<String>> finalQueryUrl = new HashMap<String, ArrayList<String>>();
		dealWithTimeInter timeDealer = utilFactory.getDealWithTime();

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
		Log.v("log:DAO:generateQueryUrl", "Complete generate query urls.");
		return finalQueryUrl;
	}

	private String genTmpQuery(String roomId, String curDateStr,
			String startTimeStr, String endTimeStr) {
		return BASE_URL + "rm_no=" + roomId + "&res_day=" + curDateStr
				+ "&s_time=" + startTimeStr + "&e_time=" + endTimeStr;
	}

}
