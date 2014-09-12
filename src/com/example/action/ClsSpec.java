package com.example.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import util.DBManager;
import dao.HttpHelper;
import domain.Record;
import domain.ResultDetails;
import domain.query;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ClsSpec extends Activity {
	
	private static DBManager dbMgr;
	
	// 测试数据
	private String classroomNoText = new String("舜德101");
	private String classroomTypeText = new String("讨论间");
	private String classroomNoOfPeopleText = new String("13");
	private String classroomStatusText = new String("占用");
	private String[] spec_person = { "琦妹", "猪头", "阳哥" };
	private String[] spec_school = { "管科", "金融", "会计" };
	private String[] spec_date = { "2014/1/1", "2014/1/2", "2014/1/3" };
	private String[] spec_time = { "14:20-16:20", "15:10-16:00", "12:00-18:00" };
	private String[] spec_usage = { "班会", "讲座", "上课" };
	
	//正式数据
	ResultDetails rd = new ResultDetails();
	ArrayList<Record> rec;
	String date = new String();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clsspec);
		
		//接受Intent
		Intent intentFromResults = getIntent();
		date = intentFromResults.getStringExtra("date");
		rd = intentFromResults.getParcelableExtra("spec");
		rec = new ArrayList<Record>(rd.getRecordList());
		
		// 设置返回按钮
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);

		// 借用信息
		TextView classroomNo = (TextView) findViewById(R.id.spec_classroomno);
		classroomNo.setText(rd.getClassroomName());
		TextView classroomType = (TextView) findViewById(R.id.spec_classroomtype);
		classroomType.setText(rd.getType());
		TextView classroomNoOfPeople = (TextView) findViewById(R.id.spec_classroomnoofpeople);
		classroomNoOfPeople.setText(rd.getNum());
		//是否加status，目前讨论是不加
		//TextView classroomStatus = (TextView) findViewById(R.id.spec_classroomstatus);
		//classroomStatus.setText(classroomStatusText);

		// 借用者信息
		List<Map<String, Object>> specItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < rec.size(); i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("spec_person", rec.get(i).getPersonName());
			item.put("spec_school", rec.get(i).getDepartment());
			item.put("spec_date", date); //格式需要再改
			item.put("spec_time", rec.get(i).getStartTime().substring(0, 1) + ":" + rec.get(i).getStartTime().substring(2, 3) + "-" + rec.get(i).getEndTime().substring(0, 1) + ":" + rec.get(i).getEndTime().substring(2, 3));
			item.put("spec_usage", rec.get(i).getContent());
			specItems.add(item);
		}

		SimpleAdapter eachItem = new SimpleAdapter(this, specItems, R.layout.clsspecstatus_item,
				new String[] { "spec_person", "spec_school", "spec_date", "spec_time", "spec_usage" }, 
				new int[] {R.id.spec_person, R.id.spec_school, R.id.spec_date, R.id.spec_time, R.id.spec_usage });
		ListView spec = (ListView) findViewById(R.id.spec_statuslist);
		spec.setAdapter(eachItem);
	}

	// 创建分享按钮
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.cls_spec, menu);
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
		case R.id.action_share:
			Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
	
	public static void setDbMgr(DBManager dbMgr) {
		ClsSpec.dbMgr = dbMgr;
	}
}