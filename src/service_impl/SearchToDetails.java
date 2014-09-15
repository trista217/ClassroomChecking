package service_impl;

import util.DBManager;
import android.util.Log;
import domain.ResultDetails;

public class SearchToDetails {

	public ResultDetails toDetails(String date, String roomID,
			DBManager dbManager) {
		ResultDetails result = new ResultDetails();
		result.setRoom(roomID);
		result.setClassroomName(dbManager.RoomInfoByRoomID("ClassroomName",
				roomID));
		Log.v("ClassroomName",
				dbManager.RoomInfoByRoomID("ClassroomName", roomID));
		result.setNum(dbManager.RoomInfoByRoomID("num", roomID));
		Log.v("num", dbManager.RoomInfoByRoomID("num", roomID));
		result.setType(dbManager.RoomInfoByRoomID("type", roomID));
		Log.v("type", dbManager.RoomInfoByRoomID("type", roomID));

		result.setRecordList(dbManager.RoomDateToRecordList(roomID, date));

		return result;
	}
}
