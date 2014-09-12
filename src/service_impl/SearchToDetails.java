package service_impl;

import java.text.ParseException;

import util.DBManager;
import util.dealWithTime;
import domain.ResultDetails;
import domain.query;

public class SearchToDetails {

	public ResultDetails toDetails(query q, String roomID ,DBManager dbManager)
	{
		ResultDetails result = new ResultDetails();
		result.setRoom(roomID);
		result.setClassroomName(dbManager.RoomInfoByRoomID("ClassroomName", roomID));
		result.setNum(dbManager.RoomInfoByRoomID("num", roomID));
		result.setType(dbManager.RoomInfoByRoomID("type", roomID));
		String endDate = "99999999";
		try {
			endDate = (new dealWithTime()).dateIncrement(q.getStartDate(), q.getDuration());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		result.setRecordList(dbManager.RoomDateToRecordList(roomID, q.getStartDate(), endDate));
		
		return result;
	}
}
