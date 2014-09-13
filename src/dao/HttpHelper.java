package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.DBManager;
import android.util.Log;
import domain.QueryAndUrlsForAsync;
import domain.query;

public class HttpHelper {

	private static DBManager dbMgr;
	//private ArrayList<Boolean> flag = new ArrayList<Boolean>();

	public static void parseUrl(QueryAndUrlsForAsync queryAndUrls) {

		//Log.v("parse url", "parse url");
		HttpHelper_parse.setDbMgr(dbMgr);
		
		Map<String,ArrayList<String>> queryUrlList = queryAndUrls.getQueryUrlList();
		query userQuery = queryAndUrls.getUserQuery();
		Map<String,Thread> threadmap = new HashMap<String, Thread>();
		
		System.out.println("before start threads");

		for(String roomID:queryUrlList.keySet()){
			Thread thread = new Thread(new HttpHelper_parse(userQuery,queryUrlList.get(roomID)), "t_"+roomID );
			threadmap.put(roomID, thread);
//			System.out.println("before start thread" + roomID);
			threadmap.get(roomID).start();
			
			//Log.v("thread_"+roomID,"start");
		}
		
		//Log.v("begin to sleep", "size="+queryUrlList.size());
		while (HttpHelper_parse.getFlag() < queryUrlList.size())
		{
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		HttpHelper_parse.setFlag(0);
		
	}

	public static void setDbMgr(DBManager dbMgr) {
		HttpHelper.dbMgr = dbMgr;
	}
}
