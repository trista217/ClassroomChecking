package util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import domain.Record;
import domain.Result;
import domain.ResultDetails;
import domain.Results;
import domain.query;

public class DBManager {
	private StdDBHelper helper;  
    private SQLiteDatabase db;
      
    public DBManager(Context context) {  
        helper = new StdDBHelper(context);  
        //因为getWritableDatabase内部调用了mContext.openOrCreateDatabase(mName, 0, mFactory);  
        //所以要确保context已初始化,我们可以把实例化DBManager的步骤放在Activity的onCreate里  
        db = helper.getWritableDatabase();  
    }
    
    public Map<String, ArrayList<String>> getfull(String field)
	{
    	String DATABASE_NAME="classroom";
    	QuerySql sqlquery;
    	
    	//Log.v("getFull","getFull");
		sqlquery = new QuerySql();
		if (field.equals("NumRange"))
			return sqlquery.sqlQuery("Select DISTINCT "+field+" from "+DATABASE_NAME+" ORDER BY num", helper);
		return sqlquery.sqlQuery("Select DISTINCT "+field+" from "+DATABASE_NAME, helper);
	}
    
	public ArrayList<String> nametoid(ArrayList<String> arrayList) {
		// TODO Auto-generated method stub
		String DATABASE_NAME="classroom";
		QuerySql sqlquery=new QuerySql();
		String str=" where (";
		for (String name:arrayList)
		{
			str+="ClassroomName =\""+name+"\" OR ";
		}
		str=str.substring(0, str.length()-" OR ".length())+") ";
		ArrayList<String> result=sqlquery.sqlQuery("Select DISTINCT room from "+DATABASE_NAME+str,helper).get("room");
		return result;
	}
	
    public Map<String, ArrayList<String>> getbythreelimit(Map<String, ArrayList<String>> map)
	{
		Log.v("checkAndSearch","checkAndSearch");
		String DATABASE_NAME="classroom";
		Map<String, ArrayList<String>> result=new LinkedHashMap<String, ArrayList<String>>();
		Map<String, ArrayList<String>> temp=new LinkedHashMap<String, ArrayList<String>>();
		QuerySql sqlquery=new QuerySql();
		String str=" where ";
		for (String field:map.keySet())
		{
			str+="(";
			for (String value:map.get(field))
			{
				str+=field+"=\""+value+"\" OR ";
			}
			str=str.substring(0, str.length()-" OR ".length())+") "+"AND ";
		}
		str=str.substring(0,str.length()-"AND ".length());
		//Log.v("checkAndSearch str",str);
		
		for (String field:map.keySet())
		{
			temp=sqlquery.sqlQuery("Select DISTINCT "+field+" from "+DATABASE_NAME+str+" ORDER BY num",helper);
			//Log.v("checkAndSearch temp",temp.toString());
			result.putAll(temp);
		}
		//Log.v("checkAndSearch result",result.toString());
		return map;
	}
    public void insertResults()
    {
    	
    }
    
    //need updating for isA
    public Results fetchResult(query q, String distinctAugment)
    {
    	String TABLE_NAME = "OrderRecord";
    	ArrayList<Result> resultList = new ArrayList<Result>();
    	int index = 0;
    	
    	String sql = "SELECT DISTINCT room, overlap FROM " + TABLE_NAME +" ORDER BY overlap";
    	Cursor c = db.rawQuery(sql, null);
    	
    	while (c.moveToNext())
    	{
    		Result r = new Result();
    		r.setRoom(c.getString(0).toString());
    		r.setOverlap(c.getString(1).toString());
    		r.setID(index);
    		index++; 
    		resultList.add(r);
    	}
    	c.close();
    	
    	Results results = new Results(q, resultList);
    	return results;
    }
    
    public ResultDetails fetchDetail(String _room)
    {
    	String TABLE_CLASSROOM = "Classroom";
    	String TABLE_ORDERRECORD = "OrderRecord";
    	ArrayList<Record> recordList = new ArrayList<Record>();
    	String room = new String();
    	String Classroom = new String();
    	String type = new String();
    	String num = new String();
    	
    	String sql = "SELECT room, ClassroomName, type, num FROM " + TABLE_CLASSROOM + " WHERE room = ?";
    	Cursor c1 = db.rawQuery(sql, new String[]{_room});
    	while (c1.moveToNext())
    	{
    		room = c1.getString(0).toString();
    		Classroom = c1.getString(1).toString();
    		type = c1.getString(2).toString();
    		num = c1.getString(3).toString();
    	}
    	c1.close();
    	
    	int index = 0;
    	
    	sql = "SELECT date, startTime, endTime, personName, department, content, state FROM"
    			+ TABLE_ORDERRECORD + "WHERE room = ?";
    	Cursor c2 = db.rawQuery(sql, new String[]{_room});
    	while (c2.moveToNext())
    	{
    		Record r = new Record();
    		r.setDate(c2.getString(0).toString());
    		r.setStartTime(c2.getString(1).toString());
    		r.setEndTime(c2.getString(2).toString());
    		r.setPersonName(c2.getString(3).toString());
    		r.setDepartment(c2.getString(4).toString());
    		r.setContent(c2.getString(5).toString());
    		r.setState(c2.getString(6).toString());
    		r.set_id(index);
    		index++;
    		recordList.add(r);
    	}
    	c2.close();
    	
    	ResultDetails resultDetails = new ResultDetails(room, type, Classroom, num, recordList);
    	return resultDetails;
    }
    
    public void closeDB() {  
        db.close();  
    }

}
