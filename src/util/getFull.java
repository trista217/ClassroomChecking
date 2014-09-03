package util;

import java.util.List;
import java.util.Map;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class getFull {
	private static String DATABASE_NAME="classroom";
	private SQLiteDatabase db;
	private QuerySql sqlquery;
	
	public Map<String, List<String>> getfull(String field,StdDBHelper dbHelper)
	{
		//Log.v("getFull","getFull");
		sqlquery=new QuerySql();
		if (field.equals("NumRange"))
			return sqlquery.sqlQuery("Select DISTINCT "+field+" from "+DATABASE_NAME+" ORDER BY num",dbHelper);
		return sqlquery.sqlQuery("Select DISTINCT "+field+" from "+DATABASE_NAME,dbHelper);
	}

    
}
