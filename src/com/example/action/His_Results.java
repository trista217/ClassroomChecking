package com.example.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import domain.DataPerQuery;
import domain.Result;
import domain.ResultDetails;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class His_Results extends Activity {
	
	private ArrayList<Result> his_resultArrayList = new ArrayList<Result>();
	
	private DataPerQuery dpq = new DataPerQuery();
	private domain.Results re = new domain.Results();
	//private query q = new query();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.his_results);
		
		// 设置返回按钮
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		
		//处理Intent
		Intent intentFromHisQuery = getIntent();
		dpq = intentFromHisQuery.getParcelableExtra("dataPerQuery");
		re = new domain.Results(dpq.getResults());
		//q = new query(re.getQuery());
		his_resultArrayList = new ArrayList<Result>(re.getAllResult());
		
		// 查询结果
		List<Map<String, Object>> his_resultItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < his_resultArrayList.size(); i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("his_result_clsNo", his_resultArrayList.get(i).getClassroomName());
			item.put("his_result_date", his_resultArrayList.get(i).getDate().substring(0, 4) + "-" + his_resultArrayList.get(i).getDate().substring(4, 6) + "-" + his_resultArrayList.get(i).getDate().substring(6, 8));
			item.put("his_result_bestOverlap", his_resultArrayList.get(i).getBestOverlap());
			String besttime = his_resultArrayList.get(i).getBestAvailableStartTime().substring(0, 2) + ":" + his_resultArrayList.get(i).getBestAvailableStartTime().substring(2, 4) + "-" + his_resultArrayList.get(i).getBestAvailableEndTime().substring(0, 2) + ":" + his_resultArrayList.get(i).getBestAvailableEndTime().substring(2, 4);
			if (besttime.equals("00:00-00:00"))
				item.put("his_result_availableTime", "无");
			else
				item.put("his_result_availableTime", besttime);
			his_resultItems.add(item);
		}

		SimpleAdapter eachhisItem = new SimpleAdapter(this, his_resultItems, R.layout.result_item, 
				new String[] { "his_result_clsNo", "his_result_date", "his_result_bestOverlap", "his_result_availableTime" }, 
				new int[] {R.id.result_classroomno, R.id.result_date, R.id.result_overlap, R.id.result_time });
		ListView his_results = (ListView) findViewById(R.id.his_resultsList);
		his_results.setAdapter(eachhisItem);

		// 单击向详情页跳转
		his_results.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
				//根据position获得ResultDetails
				ResultDetails rd = new ResultDetails(dpq.getResultDetails().get(position));
				
				Intent his_resultToSpec = new Intent(His_Results.this, ClsSpec.class);
				Bundle bundleForSpec = new Bundle();
				bundleForSpec.putString("date", His_Results.this.re.getAllResult().get(position).getDate());
				bundleForSpec.putParcelable("spec", rd);
				his_resultToSpec.putExtras(bundleForSpec);

				// 跳转
				startActivity(his_resultToSpec);
			}
		});
	}

	// 创建分享按钮
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.his_results_menu, menu);
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
}