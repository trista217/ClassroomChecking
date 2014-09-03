package service_impl;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import util.QuerySql;
import util.StdDBHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class getByThreeLimit {
	private static String DATABASE_NAME="classroom";
	private SQLiteDatabase db;
	private QuerySql sqlquery;
	
	public Map<String, List<String>> getbythreelimit(Map<String, List<String>> map,StdDBHelper dbHelper)
	{
		Log.v("getByThreeLimit","getByThreeLimit");
		Map<String, List<String>> result=new LinkedHashMap<String, List<String>>();
		Map<String, List<String>> temp=new LinkedHashMap<String, List<String>>();
		sqlquery=new QuerySql();
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
		//Log.v("getByThreeLimit str",str);
		
		for (String field:map.keySet())
		{
			temp=sqlquery.sqlQuery("Select DISTINCT "+field+" from "+DATABASE_NAME+str+" ORDER BY num",dbHelper);
			//Log.v("getByThreeLimit temp",temp.toString());
			result.putAll(temp);
		}
		//Log.v("getByThreeLimit result",result.toString());
		return map;
	}
}
