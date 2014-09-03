package com.example.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

public class ClsSpec extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clsspec);
		
		//数据
		String classroomNoText = new String("舜德101");
		String classroomTypeText = new String("讨论间");
		String classroomNoOfPeopleText = new String("13");
		String classroomStatusText = new String("占用");
		String[] spec_person = {"琦妹", "猪头", "阳哥"};
		String[] spec_school = {"管科", "金融", "会计"};
		String[] spec_date = {"2014/1/1", "2014/1/2", "2014/1/3"};
		String[] spec_time = {"14:20-16:20", "15:10-16:00", "12:00-18:00"};
		String[] spec_usage = {"班会", "讲座", "上课"};
		
		//借用信息
		TextView classroomNo = (TextView) findViewById(R.id.spec_classroomno);
		classroomNo.setText(classroomNoText);
		TextView classroomType = (TextView) findViewById(R.id.spec_classroomtype);
		classroomType.setText(classroomTypeText);
		TextView classroomNoOfPeople = (TextView) findViewById(R.id.spec_classroomnoofpeople);
		classroomNoOfPeople.setText(classroomNoOfPeopleText);
		TextView classroomStatus = (TextView) findViewById(R.id.spec_classroomstatus);
		classroomStatus.setText(classroomStatusText);
		
		//借用者信息
		List<Map<String, Object>> specItems = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < spec_person.length; i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("spec_person", spec_person[i]);
			item.put("spec_school", spec_school[i]);
			item.put("spec_date", spec_date[i]);
			item.put("spec_time", spec_time[i]);
			item.put("spec_usage", spec_usage[i]);
			specItems.add(item);
		}
		
		SimpleAdapter eachItem = new SimpleAdapter(this, specItems, R.layout.clsspecstatus_item, 
				new String[] {"spec_person", "spec_school", "spec_date", "spec_time", "spec_usage"}, 
				new int[] {R.id.spec_person, R.id.spec_school, R.id.spec_date, R.id.spec_time, R.id.spec_usage});
		ListView spec = (ListView) findViewById(R.id.spec_statuslist);
		spec.setAdapter(eachItem);
	}
}