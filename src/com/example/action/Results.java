package com.example.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.view.View;

public class Results extends Activity {
	//本次查询数据
	private String[] result_clsNo = {"舜德101", "舜德102", "伟伦501"};
	private String[] result_date = {"2014/1/1", "2014/1/2", "2014/1/3"};
	private String[] result_time = {"14:20-16:20", "15:10-16:00", "12:00-18:00"};
	
	//历史查询数据
	private String[] historical_result_clsNo = {"舜德401", "舜德418", "伟伦401"};
	private String[] historical_result_date = {"2014/3/1", "2014/3/2", "2014/3/3"};
	private String[] historical_result_time = {"14:00-16:00", "15:10-17:00", "11:00-18:00"};
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.results);
		
		//Tab
		TabHost tabHost = (TabHost) findViewById(R.id.tabhost);  
        tabHost.setup();  
        tabHost.addTab(tabHost.newTabSpec("results").setIndicator("查询结果").setContent(R.id.results));  
  
        tabHost.addTab(tabHost.newTabSpec("historical_results").setIndicator("历史查询结果").setContent(R.id.historical_results));
		
        //查询结果
		List<Map<String, Object>> resultItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < result_clsNo.length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("result_clsNo", result_clsNo[i]);
			item.put("result_date", result_date[i]);
			item.put("result_time", result_time[i]);
			resultItems.add(item);
		}
		
		SimpleAdapter eachItem = new SimpleAdapter(this, resultItems, R.layout.result_item, 
				new String[] {"result_clsNo", "result_date", "result_time"}, 
				new int[] {R.id.result_classroomno, R.id.result_date, R.id.result_time});
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
				Intent searchToResult = new Intent(Results.this, ClsSpec.class);
				//searchToResult.putExtra("position", position);
				startActivity(searchToResult);
			}
		});
		
		//单击向详情页跳转
		historical_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				Intent searchToResult = new Intent(Results.this, ClsSpec.class);
				//searchToResult.putExtra("position", position);
				startActivity(searchToResult);
			}
		});
	}
}