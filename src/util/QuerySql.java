package util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utilInter.QuerySqlInter;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class QuerySql implements QuerySqlInter{
	private SQLiteDatabase db;

	public Map<String, ArrayList<String>> sqlQuery(String sql,StdDBHelper dbHelper) {
		// TODO Auto-generated method stub
		String[] colNames;
		Map<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();

		//Log.v("log:sqlQuery","sqlQuery");
    	db=dbHelper.getWritableDatabase();
		//Log.v("log:before sql query", "*************");
		Cursor c=db.rawQuery(sql, null);
		colNames=c.getColumnNames();
		//Log.v("log:after sql", colNames.toString());
		for(int i=0;i<colNames.length;i++)
		{
			c.moveToFirst();
			ArrayList<String> fieldsValue=new ArrayList<String>();
			for(int j=0;j<c.getCount();j++)
			{
				fieldsValue.add(j, c.getString(i).toString());
				c.moveToNext();
			}
			map.put(colNames[i], fieldsValue);
		}
		//Log.v("log:sql query finished", "finished");
		return map;
    }
}
