package dao;

import util.DBManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.action.Results;

import domain.QueryAndUrlsForAsync;

public class HttpTask extends AsyncTask<QueryAndUrlsForAsync, Void, String> {
	
	private static final String DEBUG_TAG = "HttpTask";
	//数据库
	private domain.Results re;
	private DBManager dbManager;
	
	//传入mainactivity
	private Context context;
	
	//传入数据
	private QueryAndUrlsForAsync queryAndUrls;
	
	public HttpTask (Context context,DBManager dbManager) {
        this.context=context;
        this.dbManager=dbManager;
	}
	
	@Override
	protected String doInBackground(QueryAndUrlsForAsync... queryAndUrlsList) {
		Log.d(DEBUG_TAG, "do in bg");
		// params[0] from excute() is the url
		queryAndUrls = queryAndUrlsList[0];

		try {
			Log.d(DEBUG_TAG, "start http helper");
			HttpHelper.parseUrl(queryAndUrls);
		} catch (Exception e) {
			e.printStackTrace();
		}
		//need to be changed
		return "do In bg over!";
	}
	
	//need to be changed
	protected void onPostExecute(String result) {
		System.out.println(result);

		//给Results传值
		Intent searchToResult = new Intent(context, Results.class);
		Bundle toPresent = new Bundle();
		toPresent.putInt("tab", 0);
		toPresent.putSerializable("results", re);
		searchToResult.putExtras(toPresent);
		Log.v("onPostExecute","onPostExecute complete");
		context.startActivity(searchToResult);
	}
}