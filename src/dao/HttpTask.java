package dao;

import android.os.AsyncTask;
import android.util.Log;
import domain.QueryAndUrlsForAsync;

public class HttpTask extends AsyncTask<QueryAndUrlsForAsync, Void, String> {
	
	private static final String DEBUG_TAG = "HttpTask";
	@Override
	protected String doInBackground(QueryAndUrlsForAsync... queryAndUrlsList) {
		Log.d(DEBUG_TAG, "do in bg");
		// params[0] from excute() is the url
		QueryAndUrlsForAsync queryAndUrls = queryAndUrlsList[0];

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
	}
}
