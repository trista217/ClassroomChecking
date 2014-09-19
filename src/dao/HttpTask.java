package dao;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import utilInter.DBManagerInter;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnDismissListener;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import com.example.action.Results;

import domain.QueryAndUrlsForAsync;
import domain.query;

public class HttpTask extends AsyncTask<QueryAndUrlsForAsync, Void, String>
		implements OnDismissListener {

	private query userQuery;

	// 加载中
	ProgressDialog dialogLoading;

	// 数据库
	private domain.Results re;
	private static DBManagerInter dbManager;

	// 传入MainActivity
	private Context context;

	// 传入数据
	private QueryAndUrlsForAsync queryAndUrls;

	@SuppressWarnings("deprecation")
	public HttpTask(Context context, query userQuery) {
		this.context = context;
		this.userQuery = userQuery;
		dialogLoading = new ProgressDialog(context);
		dialogLoading.setButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int i) {
				dialog.cancel();
			}
		});
		dialogLoading.setMessage("正在查询...");
		dialogLoading.setCancelable(true);
		dialogLoading.setCanceledOnTouchOutside(false);
		dialogLoading.setMax(userQuery.getRoomId().size());
		dialogLoading.setProgressPercentFormat(NumberFormat
				.getPercentInstance());
		dialogLoading.setProgress(0);
		dialogLoading.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
		dialogLoading.setOnDismissListener(this);
		dialogLoading.show();
	}

	public static void setDbMgr(DBManagerInter dbMgr) {
		HttpTask.dbManager = dbMgr;
	}

	@Override
	protected void onCancelled() {
		super.onCancelled();
	}

	public void onDismiss(DialogInterface dialog) {
		Log.v("log:DAO:HttpTask", "Query cancelled.");
		this.cancel(true);
	}

	@Override
	protected void onPreExecute() {
		HttpHelper_parse.setFlag(0);
	}

	@Override
	protected String doInBackground(QueryAndUrlsForAsync... queryAndUrlsList) {
		Log.v("log:DAO:HttpTask", "Start doInBackground() in async task.");
		dbManager.clean();

		// params[0] from excute() is the url
		queryAndUrls = queryAndUrlsList[0];

		HttpHelper_parse.setDbMgr(dbManager);
		Map<String, ArrayList<String>> queryUrlList = queryAndUrls
				.getQueryUrlList();
		query userQuery = queryAndUrls.getUserQuery();
		Map<String, Thread> threadmap = new HashMap<String, Thread>();
		Log.v("log:DAO:HttpTask", "Creat theads for requests.");
		try {
			for (String roomID : queryUrlList.keySet()) {
				if (isCancelled())
					break;
				Thread thread = new Thread(new HttpHelper_parse(userQuery,
						queryUrlList.get(roomID)), "t_" + roomID);
				threadmap.put(roomID, thread);
				threadmap.get(roomID).start();
			}

			Log.v("log:DAO:HttpTask", "Parsing html...check the progress bar to follow.");

			while (HttpHelper_parse.getFlag() < queryUrlList.size()) {
				if (isCancelled())
					break;
				dialogLoading.setProgress(HttpHelper_parse.getFlag());
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "doInbackground() over!";
	}

	protected void onPostExecute(String result) {

		Log.v("log:DAO:HttpTask",result);
		// 把数据库传下去
		Results.setDbMgr(dbManager);
		re = dbManager.fetchResult(userQuery);
		Log.v("log:all requests finished","begin to initiate results page");

		// 给Results传值
		Intent searchToResult = new Intent(context, Results.class);
		Bundle toPresent = new Bundle();
		toPresent.putInt("tab", 0);
		toPresent.putParcelable("results", re);
		searchToResult.putExtras(toPresent);
		context.startActivity(searchToResult);
		dialogLoading.hide();
	}
}
