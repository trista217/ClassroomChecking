package util;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import domain.Record;
import domain.RecordForDao;
import domain.Result;
import domain.Results;
import domain.query;

public class DBManager {
	private StdDBHelper helper;
	private SQLiteDatabase db;

	public DBManager(Context context) {
		helper = new StdDBHelper(context);
		// 因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0,
		// mFactory);
		// 所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里
		db = helper.getWritableDatabase();
	}

	public Map<String, ArrayList<String>> getfull(String field) {
		String DATABASE_NAME = "classroom";
		QuerySql sqlquery;

		sqlquery = new QuerySql();
		if (field.equals("NumRange"))
			return sqlquery.sqlQuery("Select DISTINCT " + field + " from "
					+ DATABASE_NAME + " ORDER BY num", helper);
		return sqlquery.sqlQuery("Select DISTINCT " + field + " from "
				+ DATABASE_NAME, helper);
	}

	public ArrayList<String> nametoid(ArrayList<String> arrayList) {
		// TODO Auto-generated method stub
		String DATABASE_NAME = "classroom";
		QuerySql sqlquery = new QuerySql();
		String str = " where (";
		for (String name : arrayList) {
			str += "ClassroomName =\"" + name + "\" OR ";
		}
		str = str.substring(0, str.length() - " OR ".length()) + ") ";
		ArrayList<String> result = sqlquery.sqlQuery(
				"Select DISTINCT room from " + DATABASE_NAME + str, helper)
				.get("room");
		return result;
	}

	public String RoomInfoByRoomID(String field, String roomID) {
		String DATABASE_NAME = "Classroom";

		String sql = "Select " + field + " FROM " + DATABASE_NAME
				+ " WHERE room = ? ";
		Cursor c = db.rawQuery(sql, new String[] { roomID });
		c.moveToFirst();

		int field_type = c.getType(0);
		String result = "";
		switch (field_type) {
		case Cursor.FIELD_TYPE_STRING: {
			result = c.getString(0);
			Log.v("String", result);
			c.close();
			return result;
		}
		case Cursor.FIELD_TYPE_NULL: {
			result = "";
			Log.v("null", result);
			c.close();
			return result;
		}
		case Cursor.FIELD_TYPE_INTEGER: {
			result = c.getInt(0) + "";
			Log.v("Integer", result + "");
			c.close();
			return result;
		}
		}

		c.close();
		return result;
	}

	public Map<String, ArrayList<String>> getbythreelimit(
			Map<String, ArrayList<String>> map) {
		Log.v("checkAndSearch", "checkAndSearch");
		String DATABASE_NAME = "classroom";
		Map<String, ArrayList<String>> result = new LinkedHashMap<String, ArrayList<String>>();
		Map<String, ArrayList<String>> temp = new LinkedHashMap<String, ArrayList<String>>();
		QuerySql sqlquery = new QuerySql();
		String str = " where ";
		for (String field : map.keySet()) {
			if (map.get(field).isEmpty()) {
				str += "(" + field + "=null ) and ";
			}
			str += "(";
			for (String value : map.get(field)) {
				str += field + "=\"" + value + "\" OR ";
			}
			str = str.substring(0, str.length() - " OR ".length()) + ") "
					+ "AND ";
		}
		str = str.substring(0, str.length() - "AND ".length());

		for (String field : map.keySet()) {
			temp = sqlquery.sqlQuery("Select DISTINCT " + field + " from "
					+ DATABASE_NAME + str + " ORDER BY num", helper);
			result.putAll(temp);
		}
		return result;
	}

	public Results fetchResult(query q) {
		String TABLE_NAME = "OrderRecord";
		ArrayList<Result> resultList = new ArrayList<Result>();
		int index = 0;
		String sql = new String();
		Cursor c;
		if (q.isAvaliable() == true) {
			sql = "SELECT DISTINCT room, date, bestOverlapFloat,"
					+ "bestAvailableStartTime, bestAvailableEndTime  FROM "
					+ TABLE_NAME + " ORDER BY bestOverlapFloat DESC, date";
			String endDate = "99999999";
			try {
				endDate = (new dealWithTime()).dateIncrement(q.getStartDate(),
						q.getDuration());
			} catch (ParseException e) {
				e.printStackTrace();
			}
			// c = db.rawQuery(sql,new
			// String[]{q.getStartDate(),endDate,q.getStartTime(),q.getEndTime()});
			c = db.rawQuery(sql, null);
		} else {
			sql = "SELECT room, date, bestOverlapFloat, bestAvailableStartTime, bestAvailableEndTime FROM "
					+ "(Select room, date, bestOverlapFloat, bestAvailableStartTime, bestAvailableEndTime, MIN(recordStartTime) as minStartTime "
					+ "from OrderRecord WHERE recordType = ? group by room) "
					+ " ORDER BY date, minStartTime, bestOverlapFloat";
			c = db.rawQuery(sql, new String[] { "占用" });
		}

		if (c.getCount() > 0) {
			c.moveToFirst();
			do {
				Result r = new Result();
				// Log.v("room", c.getString(0).toString());
				r.setRoom(c.getString(0).toString());
				// Log.v("date", (c.getString(1).toString()));
				r.setDate(c.getString(1).toString());
				// Log.v("bestoverlap", c.getFloat(2) + "");
				r.setBestOverlap(c.getFloat(2));
				// Log.v("setBestAvailableStartTime",
				// c.getString(3).toString());
				r.setBestAvailableStartTime(c.getString(3).toString());
				// Log.v("setBestAvailableEndTime", c.getString(4).toString());
				r.setBestAvailableEndTime(c.getString(4).toString());
				r.set_id(index);
				index++;
				resultList.add(r);
			} while (c.moveToNext());
		}

		c.close();
		Results results = new Results(q, resultList);
		return results;
	}

	public void insertRecordForDao(RecordForDao recordLine) {

		String TABLE_NAME = "OrderRecord";
		long id;
		ContentValues cv = new ContentValues();
		cv.put("room", recordLine.getRecordRoomId());
		cv.put("date", recordLine.getRecordDate());
		cv.put("bestOverlap", recordLine.getBestOverlap());
		cv.put("bestOverlapFloat", recordLine.getBestOverlapFloat());
		cv.put("bestAvailableStartTime", recordLine.getBestAvailableStartTime());
		cv.put("bestAvailableEndTime", recordLine.getBestAvailableEndTime());
		cv.put("recordType", recordLine.getRecordType());
		cv.put("recordStartTime", recordLine.getRecordStartTime());
		cv.put("recordEndTime", recordLine.getRecordEndTime());
		cv.put("recordOverlap", recordLine.getRecordOverlap());
		cv.put("recordName", recordLine.getRecordName());
		cv.put("recordDepartment", recordLine.getRecordDepartment());
		cv.put("recordStatus", recordLine.getRecordStatus());
		cv.put("recordContent", recordLine.getRecordContent());
		id = db.insert(TABLE_NAME, null, cv);
	}

	public ArrayList<Record> RoomDateToRecordList(String roomID, String Date) {
		String TABLE_ORDERRECORD = "OrderRecord";
		ArrayList<Record> recordList = new ArrayList<Record>();

		// 不知道为什么不能加order by 道晨
		String sql = "SELECT recordStartTime, recordEndTime, recordName, recordDepartment, "
				+ "recordContent, recordstatus FROM "
				+ TABLE_ORDERRECORD
				+ " WHERE room = ? AND date = ? AND recordType = ?";
		// + " ORDER BY recordStartTime";
		Cursor c2 = db.rawQuery(sql, new String[] { roomID, Date, "占用" });

		Log.v("roomID", roomID + "  " + c2.getCount());
		if (c2.getCount() > 0) {
			int index = 1;
			c2.moveToFirst();
			do {
				Record r = new Record();
				r.setStartTime(c2.getString(0).toString());
				r.setEndTime(c2.getString(1).toString());
				r.setPersonName(c2.getString(2).toString());
				Log.v("personName", c2.getString(2).toString());
				r.setDepartment(c2.getString(3).toString());
				r.setContent(c2.getString(4).toString());
				r.setStatus(c2.getString(5).toString());
				r.set_id(index);
				index++;
				recordList.add(r);
			} while (c2.moveToNext());
		}

		c2.close();

		return recordList;
	}

	public void close() {
		db.close();
	}

	public void clean() {
		String TABLE_NAME = "OrderRecord";
		db.execSQL("Delete FROM " + TABLE_NAME);
	}

}
