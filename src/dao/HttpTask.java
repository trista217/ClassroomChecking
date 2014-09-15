package dao;

import util.DBManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.action.Results;

import domain.QueryAndUrlsForAsync;
import domain.query;

public class HttpTask extends AsyncTask<QueryAndUrlsForAsync, Void, String> {
	
	private static final String DEBUG_TAG = "HttpTask";
	
	private query userQuery;
	//数据库
	private domain.Results re;
	private static DBManager dbManager;
	
	//传入MainActivity
	private Context context;
	
	//传入数据
	private QueryAndUrlsForAsync queryAndUrls;
	
	public HttpTask (Context context,query userQuery) {
        this.context=context;
        this.userQuery=userQuery;
	}
	
	public static void setDbMgr(DBManager dbMgr) {
		HttpTask.dbManager = dbMgr;
	}
	
	@Override
	protected String doInBackground(QueryAndUrlsForAsync... queryAndUrlsList) {
		Log.d(DEBUG_TAG, "do in bg");
		dbManager.clean();
		// params[0] from excute() is the url
		queryAndUrls = queryAndUrlsList[0];
		try {
			Log.d(DEBUG_TAG, "start http helper");
			HttpHelper.parseUrl(queryAndUrls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "do In bg over!";
	}
	
	protected void onPostExecute(String result) {
		System.out.println(result);	
		//把数据库传下去
		Results.setDbMgr(dbManager);
		re = dbManager.fetchResult(userQuery);
		Log.v("date starttime endtime result",re.getStartDate()+"    "+re.getStartTime()+"    "+re.getEndTime()+"    "+re.getAllResult());
		
		//给Results传值
		Intent searchToResult = new Intent(context, Results.class);
		Bundle toPresent = new Bundle();
		toPresent.putInt("tab", 0);
		toPresent.putParcelable("results", re);
		searchToResult.putExtras(toPresent);
		Log.v("onPostExecute","onPostExecute complete");
		context.startActivity(searchToResult);
	}
}