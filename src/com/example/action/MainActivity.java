package com.example.action;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import service_impl.checkAndSearch;
import util.DBManager;
import util.dealWithTime;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;
import dao.HttpHelper;
import dao.HttpTask;
import dao.generateQueryUrl;
import domain.DataPerQuery;
import domain.QueryAndUrlsForAsync;
import domain.query;

public class MainActivity extends Activity {
	private EditText startdate = null;
	private EditText enddate = null;
	private EditText starttime = null;
	private EditText endtime = null;
	private Spinner classroomstatusspinner;
	private ArrayAdapter<String> classroomstatusadapter;
	// 用来保存年月日：
	private int startYear, endYear;
	private int startMonth, endMonth;
	private int startDay, endDay;
	private int startHour, endHour;
	private int startMin, endMin;
	// 声明一个独一无二的标识，来作为要显示DatePicker和TimePicker的Dialog的ID：
	static final int START_DATE_DIALOG_ID = 0;
	static final int END_DATE_DIALOG_ID = 1;
	static final int START_TIME_DIALOG_ID = 2;
	static final int END_TIME_DIALOG_ID = 3;

	//MultiSpinnerActivity的请求码
	static final int CLS_NO_SELECT_CODE = 1;
	static final int CLS_NO_OF_P_SELECT_CODE = 2;
	static final int CLS_TYPE_SELECT_CODE = 3;

	//MultiSpinnerActivity的flag
	static final int CLS_NO_FLAG = 1;
	static final int CLS_NO_OF_P_FLAG = 2;
	static final int CLS_TYPE_FLAG = 3;

	//教室号MultiSpinnerActivity，三个String[]在所有代码合并之后应initiate为全部
	Button classroomNoButton;
	private ArrayList<String> classroomnolist = new ArrayList<String>();//所有会显示的项目的集合
	private ArrayList<String> all_classroomnolist = new ArrayList<String>();//所有项目集合
	private ArrayList<String> checked_classroomnolist = new ArrayList<String>();//用户已勾选项目

	//教室人数MultiSpinnerActivity，三个String[]在所有代码合并之后应initiate为全部
	Button classroomNoOfPeopleButton;
	private ArrayList<String> classroomnoofpeoplelist = new ArrayList<String>();
	private ArrayList<String> all_classroomnoofpeoplelist = new ArrayList<String>();
	private ArrayList<String> checked_classroomnoofpeoplelist = new ArrayList<String>();

	//教室类型MultiSpinnerActivity，三个String[]在所有代码合并之后应initiate为全部
	Button classroomTypeButton;
	private ArrayList<String> classroomtypelist = new ArrayList<String>();
	private ArrayList<String> all_classroomtypelist = new ArrayList<String>();
	private ArrayList<String> checked_classroomtypelist = new ArrayList<String>();

	//status
	private String[] classroomstatusarray = { "空闲", "占用" };
	private boolean classroomstatus = true; // true代表空闲

	//数据库
	private DBManager dbManager;

	//加载中
	HkDialogLoading dialogLoading;
	
	//Time Operator
	dealWithTime d = new dealWithTime();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
		Log.v("START", "********!!!!!!!!!****$$$$$$$$$$$$@@@@@@@@");
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		//设置结果页
		Results.setDataPerQueries(new ArrayList<DataPerQuery>());

		dbManager = new DBManager(this);
		//将dbMgr传入HttpHelper及HttpTask
		HttpHelper.setDbMgr(dbManager);
		HttpTask.setDbMgr(dbManager);

		//清理数据库
		dbManager.clean();

		//init data
		classroomnolist = dbManager.getfull("ClassroomName").get("ClassroomName");//所有会显示的项目的集合
		all_classroomnolist = dbManager.getfull("ClassroomName").get("ClassroomName");//所有项目集合
		checked_classroomnolist = dbManager.getfull("ClassroomName").get("ClassroomName");//用户已勾选项目
		
		classroomnoofpeoplelist = dbManager.getfull("NumRange").get("NumRange");
		all_classroomnoofpeoplelist = dbManager.getfull("NumRange").get("NumRange");
		checked_classroomnoofpeoplelist = dbManager.getfull("NumRange").get("NumRange");
		
		classroomtypelist = dbManager.getfull("type").get("type");
		all_classroomtypelist = dbManager.getfull("type").get("type");
		checked_classroomtypelist = dbManager.getfull("type").get("type");
		
		
		
		dialogLoading = new HkDialogLoading(MainActivity.this);

		startdate = (EditText) findViewById(R.id.startdateDisplay);
		enddate = (EditText) findViewById(R.id.enddateDisplay);
		starttime = (EditText) findViewById(R.id.starttimeDisplay);
		endtime = (EditText) findViewById(R.id.endtimeDisplay);

		// 给EditText添加事件监听器：
		startdate.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// 调用Activity类的方法来显示Dialog:调用这个方法会允许Activity管理该Dialog的生命周期，
				// 并会调用 onCreateDialog(int)回调函数来请求一个Dialog
				showDialog(START_DATE_DIALOG_ID);
			}
		});
		enddate.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// 调用Activity类的方法来显示Dialog:调用这个方法会允许Activity管理该Dialog的生命周期，
				// 并会调用 onCreateDialog(int)回调函数来请求一个Dialog
				showDialog(END_DATE_DIALOG_ID);
			}
		});
		starttime.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// 调用Activity类的方法来显示Dialog:调用这个方法会允许Activity管理该Dialog的生命周期，
				// 并会调用 onCreateDialog(int)回调函数来请求一个Dialog
				showDialog(START_TIME_DIALOG_ID);
			}
		});
		endtime.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// 调用Activity类的方法来显示Dialog:调用这个方法会允许Activity管理该Dialog的生命周期，
				// 并会调用 onCreateDialog(int)回调函数来请求一个Dialog
				showDialog(END_TIME_DIALOG_ID);
			}
		});

		// 获得当前的日期和时间：
		final Calendar currentDate = Calendar.getInstance();
		startYear = endYear = currentDate.get(Calendar.YEAR);
		startMonth = endMonth = currentDate.get(Calendar.MONTH);
		startDay = endDay = currentDate.get(Calendar.DAY_OF_MONTH);
		startHour = endHour = currentDate.get(Calendar.HOUR_OF_DAY);
		startMin = endMin = currentDate.get(Calendar.MINUTE);

		// 设置文本的内容：
		startdate.setText(new StringBuilder().append(startYear).append("年").append(startMonth + 1).append("月").append(startDay).append("日"));
		enddate.setText(new StringBuilder().append(endYear).append("年").append(endMonth + 1).append("月").append(endDay).append("日"));
		starttime.setText(new StringBuilder().append(pad(startHour)).append(":").append(pad(startMin)));
		endtime.setText(new StringBuilder().append(pad(endHour)).append(":").append(pad(endMin)));

		// 创建选择教室号的MultiSpinnerActivity
		classroomNoButton = (Button) findViewById(R.id.classroomnobtn);
		classroomNoButton.setText("全部    ▼");
		classroomNoButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent classroomNoSelectionIntent = new Intent(MainActivity.this, MultiSpinnerActivity.class);
				Bundle classroomNoBundle = new Bundle();
				classroomNoBundle.putInt("flag", CLS_NO_FLAG);
				classroomNoBundle.putStringArrayList("current", MainActivity.this.classroomnolist);
				classroomNoBundle.putStringArrayList("all", MainActivity.this.all_classroomnolist);
				classroomNoBundle.putStringArrayList("checked", MainActivity.this.checked_classroomnolist);
				classroomNoSelectionIntent.putExtras(classroomNoBundle);
				startActivityForResult(classroomNoSelectionIntent, CLS_NO_SELECT_CODE);
			}
		});

		// 创建选择教室人数的MySpinner
		classroomNoOfPeopleButton = (Button) findViewById(R.id.classroomnoofpeoplebtn);
		classroomNoOfPeopleButton.setText("全部    ▼");
		classroomNoOfPeopleButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent classroomNoOfPeopleSelectionIntent = new Intent(MainActivity.this, MultiSpinnerActivity.class);
				Bundle classroomNoOfPeopleBundle = new Bundle();
				classroomNoOfPeopleBundle.putInt("flag", CLS_NO_OF_P_FLAG);
				classroomNoOfPeopleBundle.putStringArrayList("current", MainActivity.this.classroomnoofpeoplelist);
				classroomNoOfPeopleBundle.putStringArrayList("all", MainActivity.this.all_classroomnoofpeoplelist);
				classroomNoOfPeopleBundle.putStringArrayList("checked", MainActivity.this.checked_classroomnoofpeoplelist);
				classroomNoOfPeopleSelectionIntent.putExtras(classroomNoOfPeopleBundle);
				startActivityForResult(classroomNoOfPeopleSelectionIntent, CLS_NO_OF_P_SELECT_CODE);
			}
		});

		// 创建选择教室类型的MySpinner
		classroomTypeButton = (Button) findViewById(R.id.classroomtypebtn);
		classroomTypeButton.setText("全部    ▼");
		classroomTypeButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent classroomTypeSelectionIntent = new Intent(MainActivity.this, MultiSpinnerActivity.class);
				Bundle classroomTypeBundle = new Bundle();
				classroomTypeBundle.putInt("flag", CLS_TYPE_FLAG);
				classroomTypeBundle.putStringArrayList("current", MainActivity.this.classroomtypelist);
				classroomTypeBundle.putStringArrayList("all", MainActivity.this.all_classroomtypelist);
				classroomTypeBundle.putStringArrayList("checked", MainActivity.this.checked_classroomtypelist);
				classroomTypeSelectionIntent.putExtras(classroomTypeBundle);
				startActivityForResult(classroomTypeSelectionIntent, CLS_TYPE_SELECT_CODE);
			}
		});

		// 创建选择教室状态的Spinner
		classroomstatusspinner = (Spinner) findViewById(R.id.classroomstatusspinner);
		classroomstatusadapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, classroomstatusarray);
		classroomstatusadapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		classroomstatusspinner.setAdapter(classroomstatusadapter);
		classroomstatusspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				classroomstatus = (position == 1)?false:true;
				Log.v("test", ""+classroomstatus);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				classroomstatus = true;
            }
		});

		//点击“搜索”转到结果页，应该为点击搜索向后台传值，值返回后startActivity，之后再修改吧
		Button search = (Button) findViewById(R.id.searchbtn);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//给后台传值
				Map<String,ArrayList<String>> search = new HashMap<String,ArrayList<String>>();
				search.put("ClassroomName", checked_classroomnolist);
				search.put("NumRange", checked_classroomnoofpeoplelist);
				search.put("type", checked_classroomtypelist);
				Map<String, ArrayList<String>> result_search = (new checkAndSearch()).checkMap(search,dbManager);

				//构造query，duration那里只是简单算了一下，可能会把没超天数的算超了，大家要是觉得不行我再做精细点 @猩猩@阳哥@申哥@猪头
				String startDate_String = Integer.toString(startYear) + ((startMonth+1)>=10?"":"0") + Integer.toString(startMonth+1) + (startDay>=10?"":"0") + Integer.toString(startDay);
				String endDate_String = Integer.toString(endYear) + ((endMonth+1)>=10?"":"0") + Integer.toString(endMonth+1) + (endDay>=10?"":"0") + Integer.toString(endDay);
				int duration_int;
				try {
					duration_int = d.calDateDuration(startDate_String, endDate_String);
					Log.v("duration", duration_int + "");
				} catch(Exception e) {
					e.printStackTrace();
					Toast.makeText(getApplicationContext(), "日期格式有误", Toast.LENGTH_SHORT).show();
					return;
				}
				String startTime_String = (startHour>=10?"":"0") + Integer.toString(startHour) + (startMin>=10?"":"0") + Integer.toString(startMin);
				String endTime_String = (endHour>=10?"":"0") + Integer.toString(endHour) + (endMin>=10?"":"0") + Integer.toString(endMin);
				ArrayList<String> roomId = (new checkAndSearch()).NameToID(result_search.get("ClassroomName"),dbManager);
				ArrayList<String> type = result_search.get("type");
				ArrayList<String> number = result_search.get("NumRange");
				boolean isAvaliable = classroomstatus;

				if(startHour>endHour||(startHour==endHour&&startMin>=endMin)) {
					Toast.makeText(getApplicationContext(), "开始时间应早于结束时间", Toast.LENGTH_SHORT).show();
					return;
				}
				if(duration_int>7) { 
					Toast.makeText(getApplicationContext(), "查询日期过长", Toast.LENGTH_SHORT).show();
					return;
				}

				//加载中
				MainActivity.this.dialogLoading.show();

				query userQuery = new query(startDate_String, duration_int, startTime_String, endTime_String, roomId, type, number, isAvaliable);

				// 测试是否连接到网络,testNetworkConn()需要放进MainActivity里面
				try {
					testNetworkConn(userQuery);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});

		//点击“历史查询”转到历史查询页，应该为点击搜索向后台传值，值返回后startActivity，之后再修改吧;而且这里还没实现调到历史结果那个tab，建议删了这个Button
		Button historical_search = (Button) findViewById(R.id.hissearchbtn);
		historical_search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				//给Results传tab
				Intent searchToResult = new Intent(MainActivity.this, Results.class);
				Bundle toPresent = new Bundle();
				toPresent.putInt("tab", 1);
				searchToResult.putExtras(toPresent);
				startActivity(searchToResult);
			}
		});
	}

	@Override
	public void onResume() {
		super.onResume();
		dialogLoading.hide();
	}

	private static String pad(int c) {
		if (c >= 10) {
			return String.valueOf(c);
		} else {
			return "0" + String.valueOf(c);
		}
	}

	// 需要定义弹出的DatePicker对话框的事件监听器：
	private DatePickerDialog.OnDateSetListener startDateSetListener = new OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			startYear = year;
			startMonth = monthOfYear;
			startDay = dayOfMonth;
			startdate.setText(new StringBuilder().append(startYear).append("年").append(startMonth + 1).append("月").append(startDay).append("日"));
		}
	};
	private DatePickerDialog.OnDateSetListener endDateSetListener = new OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			endYear = year;
			endMonth = monthOfYear;
			endDay = dayOfMonth;
			// 设置文本的内容：
			enddate.setText(new StringBuilder().append(endYear).append("年")
					.append(endMonth + 1).append("月")// 得到的月份+1，因为从0开始
					.append(endDay).append("日"));
		}
	};

	/**
	 * 当Activity调用showDialog函数时会触发该函数的调用：
	 */
	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case START_DATE_DIALOG_ID:
			return new DatePickerDialog(this, startDateSetListener, startYear,
					startMonth, startDay);
		case END_DATE_DIALOG_ID:
			return new DatePickerDialog(this, endDateSetListener, endYear,
					endMonth, endDay);
		case START_TIME_DIALOG_ID:
			return new TimePickerDialog(this, startTimeSetListener, startHour,
					startMin, false);
		case END_TIME_DIALOG_ID:
			return new TimePickerDialog(this, endTimeSetListener, endHour,
					endMin, false);
		}
		return null;
	}

	private TimePickerDialog.OnTimeSetListener startTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			startHour = hourOfDay;
			startMin = minute;
			starttime.setText(new StringBuilder().append(pad(startHour))
					.append(":").append(pad(startMin)));
		}
	};
	private TimePickerDialog.OnTimeSetListener endTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
		@Override
		public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
			// TODO Auto-generated method stub
			endHour = hourOfDay;
			endMin = minute;
			endtime.setText(new StringBuilder().append(pad(endHour))
					.append(":").append(pad(endMin)));
		}
	};


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		if(requestCode == CLS_NO_SELECT_CODE && resultCode == CLS_NO_SELECT_CODE) {
			Bundle data_cls_no = intent.getExtras();
			classroomNoButton.setText(data_cls_no.getString("Text")+"    ▼");
			checked_classroomnolist = new ArrayList<String>(data_cls_no.getStringArrayList("checked"));
		}
		if(requestCode == CLS_NO_OF_P_SELECT_CODE && resultCode == CLS_NO_OF_P_SELECT_CODE) {
			Bundle data_cls_no_of_p = intent.getExtras();
			classroomNoOfPeopleButton.setText(data_cls_no_of_p.getString("Text")+"    ▼");
			checked_classroomnoofpeoplelist = new ArrayList<String>(data_cls_no_of_p.getStringArrayList("checked"));
		}
		if(requestCode == CLS_TYPE_SELECT_CODE && resultCode == CLS_TYPE_SELECT_CODE) {
			Bundle data_cls_t = intent.getExtras();
			classroomTypeButton.setText(data_cls_t.getString("Text")+"    ▼");
			checked_classroomtypelist = new ArrayList<String>(data_cls_t.getStringArrayList("checked"));
		}

		//将三个button的checked打3个包
		Map<String,ArrayList<String>> for_NO = new HashMap<String,ArrayList<String>>();
		for_NO.put("ClassroomName", all_classroomnolist);
		for_NO.put("NumRange", checked_classroomnoofpeoplelist);
		for_NO.put("type", checked_classroomtypelist);

		Map<String,ArrayList<String>> for_NOP = new HashMap<String,ArrayList<String>>();
		for_NOP.put("ClassroomName", checked_classroomnolist);
		for_NOP.put("NumRange", all_classroomnoofpeoplelist);
		for_NOP.put("type", checked_classroomtypelist);

		Map<String,ArrayList<String>> for_Type = new HashMap<String,ArrayList<String>>();
		for_Type.put("ClassroomName", checked_classroomnolist);
		for_Type.put("NumRange", checked_classroomnoofpeoplelist);
		for_Type.put("type", all_classroomtypelist);

		//上述每个包向数据库查询1次，共返回3个包；除了no，noOfPeople，type之外其他的属性都是all
		Map<String, ArrayList<String>> result_NO = (new checkAndSearch()).checkMap(for_NO,dbManager);
		Map<String, ArrayList<String>> result_NOP = (new checkAndSearch()).checkMap(for_NOP,dbManager);
		Map<String, ArrayList<String>> result_Type = (new checkAndSearch()).checkMap(for_Type,dbManager);
		
		//根据返回的3个包更新3个要显示的list
		classroomnolist = new ArrayList<String>(result_NO.get("ClassroomName"));
		classroomnoofpeoplelist = new ArrayList<String>(result_NOP.get("NumRange"));
		classroomtypelist = new ArrayList<String>(result_Type.get("type"));
	}

	//
	private void testNetworkConn(query userQuery) throws ParseException {
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			// generate query urls
			Map<String,ArrayList<String>> queryUrlList = new generateQueryUrl().genQueryUrl(userQuery);
			QueryAndUrlsForAsync queryAndUrls = new QueryAndUrlsForAsync(userQuery, queryUrlList);
			Log.d("httptask", "start http task");
			//使用HttpTask
			HttpTask httptask = new HttpTask(MainActivity.this,userQuery);
			httptask.execute(queryAndUrls);
		} else {
			Toast.makeText(getApplicationContext(), "No network connection!",
					Toast.LENGTH_LONG).show();
		}
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		dbManager.clean();
		dbManager.close();
	}


}
