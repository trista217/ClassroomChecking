package com.example.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import service_impl.SearchToDetails;
import util.DBManager;
import domain.DataPerQuery;
import domain.Result;
import domain.ResultDetails;
import domain.query;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.Toast;

public class Results extends Activity {
	
	private static DBManager dbMgr;

	private ArrayList<Result> resultArrayList = new ArrayList<Result>();
	
	//默认页flag
	private int flag;
	
	private domain.Results re = new domain.Results();
	//private query q = new query();
	
	//private ArrayList<domain.Results> hisResultsList = new ArrayList<domain.Results>();
	
	//保存每次查询结果
	private static ArrayList<DataPerQuery> dataPerQueries = new ArrayList<DataPerQuery>();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
		
		//本次查询结果
		DataPerQuery currentDataPerQuery = new DataPerQuery();
		
		//设置返回按钮
		ActionBar actionBar = getActionBar();  
	    actionBar.setDisplayHomeAsUpEnabled(true); 
	    
		//Tab
		TabHost tabHost = (TabHost) findViewById(R.id.tabhost);  
        tabHost.setup();  
        tabHost.addTab(tabHost.newTabSpec("results").setIndicator("查询结果").setContent(R.id.results));  
        tabHost.addTab(tabHost.newTabSpec("historical_results").setIndicator("历史查询结果").setContent(R.id.historical_querys));
		
        //处理Intent
        Intent intentFromMain = getIntent();
        flag=intentFromMain.getIntExtra("tab", 0);
        if(flag==0) {
        	//本次查询结果
        	re = intentFromMain.getParcelableExtra("results");
        	resultArrayList = re.getAllResult();
        	Log.v("~~~~!!!!!!!@@@@@@#######$$$$$$$$", re.getQuery().getRoomId().get(0));
        	
        	//本次查询结果
    		currentDataPerQuery.setResults(re);
    		ArrayList<ResultDetails> currentDetails = new ArrayList<ResultDetails>();
    		ResultDetails singleDetails = new ResultDetails();
    		for (Result temp:resultArrayList){
    			singleDetails = (new SearchToDetails()).toDetails(temp.getDate(), temp.getRoom(), dbMgr);
    			currentDetails.add(singleDetails);
    		}
    		currentDataPerQuery.setResultDetails(currentDetails);
    		dataPerQueries.add(0, currentDataPerQuery);
    		Log.v("!!!!!!!@@@@@@#######$$$$$$$$", dataPerQueries.get(0).getResults().getQuery().getRoomId().get(0));
        	
        }else {
        	//历史结果
        	
        }
        
        //设置默认页
        tabHost.setCurrentTab(flag);

        //查询结果
		List<Map<String, Object>> resultItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < resultArrayList.size(); i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("result_clsNo", resultArrayList.get(i).getClassroomName());
			item.put("result_date", resultArrayList.get(i).getDate().substring(0, 4) + "-" + resultArrayList.get(i).getDate().substring(4, 6) + "-" + resultArrayList.get(i).getDate().substring(6, 8));
			item.put("result_bestOverlap", resultArrayList.get(i).getBestOverlap());
			//item.put("result_availableTime", resultArrayList.get(i).getBestAvailableStartTime() + ":" + resultArrayList.get(i).getBestAvailableStartTime() + "-" + resultArrayList.get(i).getBestAvailableEndTime() + ":" +resultArrayList.get(i).getBestAvailableEndTime());
			//Log.v("string out of bound", resultArrayList.get(i).getBestAvailableStartTime() + ":" + resultArrayList.get(i).getBestAvailableStartTime() + "-" + resultArrayList.get(i).getBestAvailableEndTime() + ":" +resultArrayList.get(i).getBestAvailableEndTime());
			//Log.v("string out of bound all",resultArrayList.get(i).getBestAvailableStartTime().substring(0, 2) + ":" + resultArrayList.get(i).getBestAvailableStartTime().substring(2, 4) + "-" + resultArrayList.get(i).getBestAvailableEndTime().substring(0, 2) + ":" +resultArrayList.get(i).getBestAvailableEndTime().substring(2, 4));
			String besttime = resultArrayList.get(i).getBestAvailableStartTime().substring(0, 2) + ":" + resultArrayList.get(i).getBestAvailableStartTime().substring(2, 4) + "-" + resultArrayList.get(i).getBestAvailableEndTime().substring(0, 2) + ":" +resultArrayList.get(i).getBestAvailableEndTime().substring(2, 4);
			if (besttime.equals("00:00-00:00"))
				item.put("result_availableTime", "无");
			else
				item.put("result_availableTime", besttime);
			resultItems.add(item);
		}
		
		SimpleAdapter eachItem = new SimpleAdapter(this, resultItems, R.layout.result_item, 
				new String[] {"result_clsNo", "result_date", "result_bestOverlap", "result_availableTime"}, 
				new int[] {R.id.result_classroomno, R.id.result_date, R.id.result_overlap, R.id.result_time});
		ListView results = (ListView) findViewById(R.id.resultsList);
		results.setAdapter(eachItem);
		
		//历史查询结果
		List<Map<String, Object>> historical_queryItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < dataPerQueries.size()-1; i++) {
			Map<String, Object> historical_queryitem = new HashMap<String, Object>();
			query his_query = new query(dataPerQueries.get(i).getResults().getQuery());
			historical_queryitem.put("historical_query_startDate", his_query.getStartDate().substring(0, 4) + "-" + his_query.getStartDate().substring(4, 6) + "-" +his_query.getStartDate().substring(6, 8));
			historical_queryitem.put("historical_query_duration", his_query.getDuration());
			String time_interval = his_query.getStartTime().substring(0, 2) + ":" + his_query.getStartTime().substring(2, 4) + "-" + his_query.getEndTime().substring(0, 2) + ":" + his_query.getEndTime().substring(2, 4);
			if (time_interval.equals("00:00-00:00"))
				historical_queryitem.put("historical_query_time", "无");
			else
				historical_queryitem.put("historical_query_time", time_interval);
			historical_queryitem.put("historical_query_roomID", his_query.getClassroomNameList().get(0) + ", 共" + his_query.getRoomId().size() + "个");
			historical_queryitem.put("historical_query_type",  his_query.getType().get(0) + ", 共" + his_query.getType().size() + "个");
			historical_queryitem.put("historical_query_number", his_query.getNumber().get(0) + ", 共" + his_query.getNumber().size() + "个");
			historical_queryitem.put("historical_query_isavaliable", his_query.isAvaliable()?"空闲":"占用");
			historical_queryItems.add(historical_queryitem);
		}

		SimpleAdapter historical_eachqueryItem = new SimpleAdapter(this, historical_queryItems, R.layout.query_item, 
				new String[] {"historical_query_startDate", "historical_query_duration", "historical_query_time", "historical_query_roomID", "historical_query_type", "historical_query_number", "historical_query_isavaliable" }, 
				new int[] {R.id.his_query_result_date, R.id.his_query_result_duration, R.id.his_query_result_time, R.id.his_query_result_roomid, R.id.his_query_result_type, R.id.his_query_result_number, R.id.his_query_result_availability });
		ListView historical_querys = (ListView) findViewById(R.id.historical_querysList);
		historical_querys.setAdapter(historical_eachqueryItem);
		
		//单击向详情页跳转
		results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				//根据position获得教室号，向后台查找ResultDetails(参数为Results.this.q和roomId_click)，返回一个ResultDetails类，填下面括号里
				//Results.this.q = Results.this.re.getQuery();
				String date_click = Results.this.re.getAllResult().get(position).getDate();
				String roomId_click = Results.this.re.getAllResult().get(position).getRoom();
				
				ResultDetails rd = new ResultDetails((new SearchToDetails()).toDetails(date_click, roomId_click, dbMgr));
				
				Intent resultToSpec = new Intent(Results.this, ClsSpec.class);
				Bundle bundleForSpec = new Bundle();
				bundleForSpec.putString("date", Results.this.re.getAllResult().get(position).getDate());
				bundleForSpec.putParcelable("spec", rd);
				resultToSpec.putExtras(bundleForSpec);
				
				//跳转
				startActivity(resultToSpec);
			}
		});
		
		//单击向详情页跳转
		historical_querys.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				//根据position获得data,传给His_Results
				Intent hisQueryToHisResults = new Intent(Results.this, His_Results.class);
				Bundle bundleForHisResults = new Bundle();
				bundleForHisResults.putParcelable("dataPerQuery", dataPerQueries.get(position));
				hisQueryToHisResults.putExtras(bundleForHisResults);
				//跳转
				startActivity(hisQueryToHisResults);
			}
		});
	}
	public static ArrayList<DataPerQuery> getDataPerQueries() {
		return dataPerQueries;
	}
	public static void setDataPerQueries(ArrayList<DataPerQuery> dataPerQueries) {
		Results.dataPerQueries = dataPerQueries;
	}
	// 创建分享按钮
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.result_menu, menu);
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		switch (id) {
		// 单击返回按钮效果设定
		case android.R.id.home:
			/*Intent upIntent = NavUtils.getParentActivityIntent(this);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.create(this)
						.addNextIntentWithParentStack(upIntent)
						.startActivities();
			} else {
				upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				NavUtils.navigateUpTo(this, upIntent);
			}*/
			finish();
			return true;
		// 分享按钮效果设定
		case R.id.results_share:
			Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public static void setDbMgr(DBManager dbMgr) {
		Results.dbMgr = dbMgr;
	}
	
	}