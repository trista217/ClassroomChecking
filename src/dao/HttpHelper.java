package dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import util.DBManager;
import domain.QueryAndUrlsForAsync;
import domain.query;

public class HttpHelper {

	private static DBManager dbMgr;

	public static void parseUrl(QueryAndUrlsForAsync queryAndUrls) {

		HttpHelper_parse.setDbMgr(dbMgr);
		Map<String, ArrayList<String>> queryUrlList = queryAndUrls
				.getQueryUrlList();
		query userQuery = queryAndUrls.getUserQuery();
		Map<String, Thread> threadmap = new HashMap<String, Thread>();

		System.out.println("before start threads");

		for (String roomID : queryUrlList.keySet()) {
			Thread thread = new Thread(new HttpHelper_parse(userQuery,
					queryUrlList.get(roomID)), "t_" + roomID);
			threadmap.put(roomID, thread);
			threadmap.get(roomID).start();
		}

		while (HttpHelper_parse.getFlag() < queryUrlList.size()) {
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
