package util;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class QuerySql {
	private SQLiteDatabase db;
	private StdDBHelper dbHelper;

	public Map<String, List<String>> sqlQuery(String sql,StdDBHelper dbHelper) {
		// TODO Auto-generated method stub
		String[] colNames;
		Map<String, List<String>> map = new LinkedHashMap<String, List<String>>();
        // 数据库
		//Log.v("sqlQuery","sqlQuery");
    	db=dbHelper.getWritableDatabase();
		//Log.v("before sql query", "*************");
		Cursor c=db.rawQuery(sql, null);
		colNames=c.getColumnNames();
		//Log.v("after sql", colNames.toString());
		for(int i=0;i<colNames.length;i++)
		{
			c.moveToFirst();//到第一行
			ArrayList<String> fieldsValue=new ArrayList<String>();
			for(int j=0;j<c.getCount();j++)
			{
				fieldsValue.add(j, c.getString(i).toString());
				c.moveToNext();
			}
			map.put(colNames[i], fieldsValue);
		}
		//Log.v("sql query finished", "finished");
		return map;
    }
}