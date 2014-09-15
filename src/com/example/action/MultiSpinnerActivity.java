package com.example.action;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

public class MultiSpinnerActivity extends Activity {
	List<String> bufferlist = new ArrayList<String>();
	ArrayList<String> m, all, list;
	Button finish, multiSpinner_all, multiSpinner_none;
	ListView choice;
	String print;//最终MainActivity显示内容
	int flag;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spinner);
		
		//Receive intent
		Intent intent = this.getIntent();
		flag = intent.getIntExtra("flag", 0);//default将导致程序退出
		m = new ArrayList<String>(intent.getStringArrayListExtra("current"));
		all = new ArrayList<String>(intent.getStringArrayListExtra("all"));
		list = new ArrayList<String>(intent.getStringArrayListExtra("checked"));
		
		//Finish
		finish = (Button) findViewById(R.id.over_text);
		finish.setText("完成");
		finish.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//从buffer list到list
				list.clear();
				list.addAll(new ArrayList<String>(bufferlist));
				//如果一定要查询更新，那么这里要加段代码避免bug
				
				//设置最终显示内容
				if(list.size()==0){
					Toast.makeText(getApplicationContext(), "请至少选择一项", Toast.LENGTH_SHORT).show();
					return;
				}else if(list.size()==all.size()) {
					print = new String("全部");
				}else{
					int count = 0;
					String one = new String();
					for(int i = 0; i<list.size(); i++) {
						if(m.contains(list.get(i))) {
							count++;
							one = list.get(i);
						}
					}
					StringBuffer buffer=new StringBuffer();
					if(count!=0) {
						buffer.append(one+','+"共"+count+"个");
					}else{
						buffer.append("共"+count+"个");
					}
					print = new String(buffer.toString().substring(0, buffer.length()));
				}
				
				bufferlist.clear();
				
				//向MainActivity回传值
				Intent intent = getIntent();
				Bundle bundle = new Bundle();
				bundle.putStringArrayList("checked", MultiSpinnerActivity.this.list);
				bundle.putString("Text", print);
				intent.putExtras(bundle);
				MultiSpinnerActivity.this.setResult(flag, intent);
				MultiSpinnerActivity.this.finish();
			}
		});
		
		choice = (ListView) findViewById(R.id.listview);
		
		//All
		multiSpinner_all=(Button) findViewById(R.id.myspinner_all);
		multiSpinner_all.setText("全选");
		multiSpinner_all.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//用all填充list
				list.clear();
				list.addAll(new ArrayList<String>(all));
				for(int i = 0; i<all.size(); i++) {
					if(m.contains(list.get(i))&&(!bufferlist.contains(list.get(i)))) {
						choice.performItemClick(choice, m.indexOf(list.get(i)), m.indexOf(list.get(i)));
					}
				}
				bufferlist.clear();
				bufferlist.addAll(new ArrayList<String>(list));
			}
		});
		
		//None
		multiSpinner_none=(Button) findViewById(R.id.myspinner_none);
		multiSpinner_none.setText("全不选");
		multiSpinner_none.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				while(bufferlist.size()!=0) {
					if(m.contains(bufferlist.get(0)))
						choice.performItemClick(choice, m.indexOf(bufferlist.get(0)), m.indexOf(bufferlist.get(0)));
					else
						bufferlist.remove(0);
				}
				list.clear();
				bufferlist.clear();
				bufferlist.addAll(list);
			}
		});
		
		//Choice
		choice.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
		ArrayAdapter<String> adapter = new ArrayAdapter<String> (choice.getContext(),android.R.layout.simple_list_item_multiple_choice,m);
		choice.setAdapter(adapter);
		choice.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				String object=m.get(arg2);
				if(bufferlist.contains(object)){
					bufferlist.remove(bufferlist.indexOf(object));
				}else{
					bufferlist.add(new String(object));
				}
			}
		});
		
		//显示上次保存的list
		for(int i = 0; i<list.size(); i++) {
			if(m.contains(list.get(i))) {
				choice.performItemClick(choice, m.indexOf(list.get(i)), m.indexOf(list.get(i)));
			}else{
				bufferlist.add(new String(list.get(i)));
			}
		}
	}
}