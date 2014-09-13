package com.example.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import service_impl.SearchToDetails;
import util.DBManager;
import dao.HttpHelper;
import domain.Record;
import domain.Result;
import domain.ResultDetails;
import domain.query;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
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
	
	//本次查询数据，按需要更改格式及数据类型
	private String[] result_clsNo = {"舜德101", "舜德102", "伟伦501"};
	private String[] result_date = {"2014/1/1", "2014/1/2", "2014/1/3"};
	private String[] result_time = {"14:20-16:20", "15:10-16:00", "12:00-18:00"};
	private ArrayList<Result> resultArrayList = new ArrayList<Result>();
	
	//历史查询数据，按需要更改格式及数据类型
	private String[] historical_result_clsNo = {"舜德401", "舜德418", "伟伦401"};
	private String[] historical_result_date = {"2014/3/1", "2014/3/2", "2014/3/3"};
	private String[] historical_result_time = {"14:00-16:00", "15:10-17:00", "11:00-18:00"};
	
	//默认页flag
	private int flag;
	
	private domain.Results re = new domain.Results();
	private query q = new query();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
		
		//设置返回按钮
		ActionBar actionBar = getActionBar();  
	    actionBar.setDisplayHomeAsUpEnabled(true); 
	    
		//Tab
		TabHost tabHost = (TabHost) findViewById(R.id.tabhost);  
        tabHost.setup();  
        tabHost.addTab(tabHost.newTabSpec("results").setIndicator("查询结果").setContent(R.id.results));  
        tabHost.addTab(tabHost.newTabSpec("historical_results").setIndicator("历史查询结果").setContent(R.id.historical_results));
		
        //处理Intent
        Intent intentFromMain = getIntent();
        flag=intentFromMain.getIntExtra("tab", 0);
        if(flag==0) {
        	//本次查询结果
        	re = intentFromMain.getParcelableExtra("results");
        	resultArrayList = re.getAllResult();
        }else {
        	//历史结果
        	
        }
        
        //设置默认页
        tabHost.setCurrentTab(flag);

        //查询结果
		List<Map<String, Object>> resultItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < resultArrayList.size(); i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("result_clsNo", resultArrayList.get(i).getRoom());
			item.put("result_date", resultArrayList.get(i).getDate());
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
		List<Map<String, Object>> historical_resultItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < historical_result_clsNo.length; i++) {
			Map<String, Object> historical_item = new HashMap<String, Object>();
			historical_item.put("historical_result_clsNo", historical_result_clsNo[i]);
			historical_item.put("historical_result_date", historical_result_date[i]);
			historical_item.put("historical_result_time", historical_result_time[i]);
			historical_resultItems.add(historical_item);
		}
		
		SimpleAdapter historical_eachItem = new SimpleAdapter(this, historical_resultItems, R.layout.result_item, 
				new String[] {"historical_result_clsNo", "historical_result_date", "historical_result_time"}, 
				new int[] {R.id.result_classroomno, R.id.result_date, R.id.result_time});
		ListView historical_results = (ListView) findViewById(R.id.historical_resultsList);
		historical_results.setAdapter(historical_eachItem);
		
		//单击向详情页跳转
		results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				//根据position获得教室号，向后台查找ResultDetails(参数为Results.this.q和roomId_click)，返回一个ResultDetails类，填下面括号里
				Results.this.q = Results.this.re.getQuery();
				String roomId_click = Results.this.re.getAllResult().get(position).getRoom();
				
				ResultDetails rd = new ResultDetails();//填空@猪头
				//假设是room_Id @大头
				rd = (new SearchToDetails()).toDetails(Results.this.q, roomId_click, dbMgr);
				
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
		historical_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				//根据position获得教室号，向后台查找ResultDetails，并将结果放在Intent里
				Intent resultToSpec = new Intent(Results.this, ClsSpec.class);
				
				//跳转
				startActivity(resultToSpec);
			}
		});
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
			Intent upIntent = NavUtils.getParentActivityIntent(this);
			if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
				TaskStackBuilder.create(this)
						.addNextIntentWithParentStack(upIntent)
						.startActivities();
			} else {
				upIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				NavUtils.navigateUpTo(this, upIntent);
			}
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