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

		// Log.v("getFull","getFull");
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
	
//	public String idtoname(String room) {
//	// TODO Auto-generated method stub
//	String DATABASE_NAME = "classroom";
//	QuerySql sqlquery = new QuerySql();
//	ArrayList<String> result = sqlquery.sqlQuery(
//			"Select DISTINCT ClassroomName from " + DATABASE_NAME + "where room = \""+ room +"\"", helper)
//			.get("ClassroomName");
//	return result.get(0);
//}

	public String RoomInfoByRoomID (String field, String roomID)
	{
		String DATABASE_NAME = "classroom";
		Cursor c = db.rawQuery("Select ? from ? where room = ?", new String[] {field, DATABASE_NAME, roomID});
		c.moveToFirst();
		
		int field_type = c.getType(0);
		String result = "";
		switch (field_type){
		case  Cursor.FIELD_TYPE_STRING:
			result = c.getString(0);
		case  Cursor.FIELD_TYPE_NULL:
			result = "";
		case  Cursor.FIELD_TYPE_INTEGER:
			result = c.getInt(0)+"";
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
			//原先微信里那句有点问题，你看下面注释就明白了，我在UI那边加了空值控制了，估计不会再传过来空值，不过你最好再想想加一句 @猪头
			/*if(map.get(field).isEmpty()) {
				str += "(" + field + "=\"" + "EMPTY" + "\"" + ")" + "AND";
				continue;
			}*/
			if (map.get(field).isEmpty())
			{
				str += "("+field+"=null ) and ";
			}
			str += "(";
			for (String value : map.get(field)) {
				str += field + "=\"" + value + "\" OR ";
			}
			str = str.substring(0, str.length() - " OR ".length()) + ") "
					+ "AND ";
		}
		str = str.substring(0, str.length() - "AND ".length());
		// Log.v("checkAndSearch str",str);

		for (String field : map.keySet()) {
			temp = sqlquery.sqlQuery("Select DISTINCT " + field + " from "
					+ DATABASE_NAME + str + " ORDER BY num", helper);
			// Log.v("checkAndSearch temp",temp.toString());
			result.putAll(temp);
		}
		// Log.v("checkAndSearch result",result.toString());
		return result;
	}

	public Results fetchResult(query q) {
		String TABLE_NAME = "OrderRecord";
		ArrayList<Result> resultList = new ArrayList<Result>();
		int index = 0;
		String sql = new String();
		Cursor c;
		if (q.isAvaliable() ==  true)
		{
			sql = "SELECT DISTINCT room, date, bestOverlapFloat," +
				"bestAvailableStartTime, bestAvailableEndTime  FROM " + TABLE_NAME
				+ " where date >= ? and date <= ? and recordStartTime >= ? and recordEndTime <= ? "
				+ " ORDER BY bestOverlapFloat DESC, date";
			String endDate= "99999999" ;
			try {
				endDate = (new dealWithTime()).dateIncrement(q.getStartDate(), q.getDuration());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c = db.rawQuery(sql,new String[]{q.getStartDate(),endDate,q.getStartTime(),q.getEndTime()});
		}
		else
		{
			sql = "SELECT room, date, bestOverlapFloat, bestAvailableStartTime, bestAvailableEndTime FROM " +
					"(Select room, date, bestOverlapFloat, bestAvailableStartTime, bestAvailableEndTime, MIN(recordStartTime) as minStartTime " +
					"from OrderRecord WHERE recordType = ? group by room) " +
					"ORDER BY date, minStartTime, bestOverlapFloat";	
			c = db.rawQuery(sql, new String[]{"占用"});
		}
		
		c.moveToFirst();
		while (c.moveToNext()) {
//			Log.v("dbFetch", "start"+index);
			Result r = new Result();
			Log.v("room",c.getString(0).toString());
			r.setRoom(c.getString(0).toString());
			Log.v("date",(c.getString(1).toString()));
			r.setDate(c.getString(1).toString());
			Log.v("bestoverlap",c.getFloat(2)+"");
			r.setBestOverlap(c.getFloat(2));
			Log.v("setBestAvailableStartTime",c.getString(3).toString());
			r.setBestAvailableStartTime(c.getString(3).toString());
			Log.v("setBestAvailableEndTime",c.getString(4).toString());
			r.setBestAvailableEndTime(c.getString(4).toString());
			r.set_id(index);
			index++;
			resultList.add(r);
//			r.printResult();
//			Log.v("dbFetch", "end"+index);
		}
		c.close();

		Results results = new Results(q, resultList);
//		ArrayList<Result> p = new ArrayList<Result>(results.getAllResult());
//		Iterator<Result> pi = p.iterator();
//		Log.v("fetchResult", "PrintList");
//		while (pi.hasNext()){
//			pi.next().printResult();
//		}
		return results;
	}

	public void insertRecordForDao(ArrayList<RecordForDao> recordForDaoList) {
		Log.v("insert for DAO", "start insert into db");
		String TABLE_NAME = "OrderRecord";
		for (RecordForDao record : recordForDaoList) {
			long id;
			ContentValues cv = new ContentValues();
			// " (room String, date String, bestOverlap String, bestAvailableStartTime String, bestAvailableEndTime String,"
			// +
			// "recordType String, recordStartTime String, recordEndTime String, recordOverlap String"
			// +
			// "recordPersonName String, recordDepartment String, recordStatus String,recordContent String)";
			cv.put("room", RecordForDao.getRecordRoomId());
			cv.put("date", RecordForDao.getRecordDate());
			cv.put("bestOverlap", RecordForDao.getBestOverlap());
			cv.put("bestOverlapFloat", RecordForDao.getBestOverlapFloat());
			cv.put("bestAvailableStartTime",
					RecordForDao.getBestAvailableStartTime());
			cv.put("bestAvailableEndTime",
					RecordForDao.getBestAvailableEndTime());
			cv.put("recordType", record.getRecordType());
			cv.put("recordStartTime", record.getRecordStartTime());
			cv.put("recordEndTime", record.getRecordEndTime());
			cv.put("recordOverlap", record.getRecordOverlap());
			cv.put("recordName", record.getRecordName());
			cv.put("recordDepartment", record.getRecordDepartment());
			cv.put("recordStatus", record.getRecordStatus());
			cv.put("recordContent", record.getRecordContent());
			id = db.insert(TABLE_NAME, null, cv);
		}
		Log.v("insert for DAO", "end insert into db");

	}
	
	public ArrayList<Record> RoomDateToRecordList (String roomID, String StartDate, String EndDate){
		String TABLE_ORDERRECORD = "OrderRecord";
		ArrayList<Record> recordList = new ArrayList<Record>();
		
		String sql = "SELECT recordStartTime, recordEndTime, recordName, recordDepartment, " +
				"recordContent, recordstatus FROM"
				+ TABLE_ORDERRECORD + "WHERE (room = ?) AND (date > ?) AND (date < ?) AND (recordType = ?)"
				+ "ORDER　BY recordStartTIme";
		Cursor c2 = db.rawQuery(sql, new String[] { roomID, StartDate, EndDate, "占用"});
		
		int index=0;
		c2.moveToFirst();
		while (c2.moveToNext()) {
			Record r = new Record();
			r.setStartTime(c2.getString(0).toString());
			r.setEndTime(c2.getString(1).toString());
			r.setPersonName(c2.getString(2).toString());
			r.setDepartment(c2.getString(3).toString());
			r.setContent(c2.getString(4).toString());
			r.setStatus(c2.getString(5).toString());
			r.set_id(index);
			index++;
			recordList.add(r);
		}
		c2.close();
		
		return recordList;
	}

//	public ResultDetails fetchDetail(String _room, String _date) {
//		String TABLE_CLASSROOM = "Classroom";
//		String TABLE_ORDERRECORD = "OrderRecord";
//		ArrayList<Record> recordList = new ArrayList<Record>();
//		String room = new String();
//		String Classroom = new String();
//		String type = new String();
//		String num = new String();
//
//		String sql = "SELECT room, ClassroomName, type, num FROM "
//				+ TABLE_CLASSROOM + " WHERE room = ?";
//		Cursor c1 = db.rawQuery(sql, new String[] {_room});
//		
//		c1.moveToFirst();
//		while (c1.moveToNext()) {
//			room = c1.getString(0).toString();
//			Classroom = c1.getString(1).toString();
//			type = c1.getString(2).toString();
//			num = c1.getInt(3)+"";
//		}
//		c1.close();
//
//		int index = 0;
//
//		sql = "SELECT recordStartTime, recordEndTime, recordName, recordDepartment, " +
//				"recordContent, recordstatus FROM"
//				+ TABLE_ORDERRECORD + "WHERE (room = ?) AND (date = ?) AND (recordType = ?)"
//				+ "ORDER　BY recordStartTIme";
//		Cursor c2 = db.rawQuery(sql, new String[] { _room, _date, "占用"});
//		
//		c2.moveToFirst();
//		while (c2.moveToNext()) {
//			Record r = new Record();
//			r.setStartTime(c2.getString(0).toString());
//			r.setEndTime(c2.getString(1).toString());
//			r.setPersonName(c2.getString(2).toString());
//			r.setDepartment(c2.getString(3).toString());
//			r.setContent(c2.getString(4).toString());
//			r.setStatus(c2.getString(5).toString());
//			r.set_id(index);
//			index++;
//			recordList.add(r);
//		}
//		c2.close();
//
//		ResultDetails resultDetails = new ResultDetails(room, type, Classroom,
//				num, recordList);
//		return resultDetails;
//	}
//
//	public void deleteTable(String TableName)
//	{
//		String sql = "DELETE　FROM " + TableName;
//		db.execSQL(sql);
//	}
////	+ " (room String, date String, bestOverlap String, bestOverlapFloat float,bestAvailableStartTime String, bestAvailableEndTime String,"
////	+ "recordType String, recordStartTime String, recordEndTime String, recordOverlap String, recordName String, recordDepartment String, recordStatus String, recordContent String)";
//	
//	//测试
////	public void printDB()
////	{
////		String sql = "SELECT * FROM OrderRecord";
////		Cursor c = db.rawQuery(sql, null);
////		while (c.moveToNext())
////		{
////			Log.v("db","startPrint");
////			String s = new String();
////			for (int i = 0; i < 13; i++)
////			{
////				s = s + c.getString(i).toString() + " ";
////			}
////			Log.v("db", s);
////			Log.v("db","1line");
////		}
////		c.close();
////	}
//	
	public void close() {
		db.close();
	}
	
	public void clean() {
		String TABLE_NAME = "OrderRecord";
		db.execSQL("Delete FROM "+TABLE_NAME);
	}

}

