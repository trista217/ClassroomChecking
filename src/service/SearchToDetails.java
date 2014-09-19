package service;

import serviceInter.SearchToDetailsInter;
import utilInter.DBManagerInter;
import android.util.Log;
import domain.ResultDetails;

public class SearchToDetails implements SearchToDetailsInter{

	public ResultDetails toDetails(String date, String roomID,
			DBManagerInter dbManager) {
		ResultDetails result = new ResultDetails();
		result.setRoom(roomID);
		result.setClassroomName(dbManager.RoomInfoByRoomID("ClassroomName",
				roomID));
		//Log.v("log:ClassroomName",
				//dbManager.RoomInfoByRoomID("ClassroomName", roomID));
		result.setNum(dbManager.RoomInfoByRoomID("num", roomID));
		//Log.v("log:num", dbManager.RoomInfoByRoomID("num", roomID));
		result.setType(dbManager.RoomInfoByRoomID("type", roomID));
		//Log.v("log:type", dbManager.RoomInfoByRoomID("type", roomID));

		result.setRecordList(dbManager.RoomDateToRecordList(roomID, date));

		return result;
	}
}
