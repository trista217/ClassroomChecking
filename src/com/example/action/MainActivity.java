package com.example.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import service_impl.getByThreeLimit;
import util.StdDBHelper;
import util.getFull;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;

public class MainActivity extends Activity {
	private EditText startdate = null;
	private EditText enddate = null;
	private EditText starttime = null;
	private EditText endtime = null;
	private Spinner classroomnospinner;
	private ArrayAdapter<String> classroomnoadapter;
	private Spinner classroomtypespinner;
	private ArrayAdapter<String> classroomtypeadapter;
	private Spinner classroomnoofpeoplespinner;
	private ArrayAdapter<String> classroomnoofpeopleadapter;
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

	//数据库相关
	private static final String DATABASE_NAME="classroom";
	private SQLiteDatabase db;
	private StdDBHelper dbHelper;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		setContentView(R.layout.activity_main);				
		//getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title);
		
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
		startdate.setText(new StringBuilder().append(startYear).append("年")
				.append(startMonth + 1).append("月")// 得到的月份+1，因为从0开始
				.append(startDay).append("日"));
		enddate.setText(new StringBuilder().append(endYear).append("年")
				.append(endMonth + 1).append("月")// 得到的月份+1，因为从0开始
				.append(endDay).append("日"));
		starttime.setText(new StringBuilder().append(pad(startHour))
				.append(":").append(pad(startMin)));
		endtime.setText(new StringBuilder().append(pad(endHour)).append(":")
				.append(pad(endMin)));
		
		//查询
		//创建数据库
		dbHelper =new StdDBHelper(this);
			
		//创建全查询
		getFull getfull = new getFull();
		
		//获得教室号字段
		String field="ClassroomName";
		String[] classroomnoarray =new String[getfull.getfull(field, dbHelper).get(field).size()];
		List<String> classroomList=getfull.getfull(field, dbHelper).get(field);
		classroomnoarray=classroomList.toArray(new String[0]);
		String[] roomName={classroomnoarray[0],classroomnoarray[35],classroomnoarray[50],classroomnoarray[63]};
		// 创建选择教室号的MySpinner
		// 可以根据service层数据处理实现根据已选择教室类型自动筛选过滤教室号，这里的classroomnoarray只是测试数据集
		
		MySpinner classroomNoMySpinner=(MySpinner) findViewById(R.id.classroomnospinner);
		classroomNoMySpinner.initContent(roomName);

		//
		field="NumRange";
		String[] classroomnoofpeoplearray =new String[getfull.getfull(field, dbHelper).get(field).size()];
		List<String> NumList=getfull.getfull(field, dbHelper).get(field);
		classroomnoofpeoplearray=NumList.toArray(new String[0]);
		String[] numRange={classroomnoofpeoplearray[0],classroomnoofpeoplearray[1],classroomnoofpeoplearray[2],classroomnoofpeoplearray[3]};
		// 创建选择教室人数的MySpinner
		// 可以根据service层数据处理实现根据已选择其他参数自动筛选过滤，这里的classroomnoofpeoplearray只是测试数据集

		MySpinner classroomNoOfPeopleMySpinner=(MySpinner) findViewById(R.id.classroomnoofpeoplespinner);
		classroomNoOfPeopleMySpinner.initContent(numRange);

		//
		//查询教室类型
		field="type";
		String[] classroomtypearray =new String[getfull.getfull(field, dbHelper).get(field).size()];
		List<String> TypeList=getfull.getfull(field, dbHelper).get(field);
		classroomtypearray=TypeList.toArray(new String[0]);
		String[] type={classroomtypearray[0],classroomtypearray[1],classroomtypearray[2],classroomtypearray[3],classroomtypearray[4],classroomtypearray[5],classroomtypearray[6],classroomtypearray[7],classroomtypearray[8],classroomtypearray[9],classroomtypearray[10],classroomtypearray[11]};
		// 创建选择教室类型的MySpinner
		// 可以根据service层数据处理实现根据已选择其他参数自动筛选过滤，这里的classroomtypearray只是测试数据集

		MySpinner classroomTypeMySpinner=(MySpinner) findViewById(R.id.classroomtypespinner);
		classroomTypeMySpinner.initContent(type);

		//查詢
		//打Map
		Map<String, List<String>> threemap=new LinkedHashMap<String, List<String>>();

		threemap.put("ClassroomName", Arrays.asList(roomName));
		threemap.put("NumRange", Arrays.asList(numRange));
		threemap.put("type", Arrays.asList(type));
		getByThreeLimit threeQuery=new getByThreeLimit();
		Log.v("Here","Before");
		threeQuery.getbythreelimit(threemap, dbHelper);
		Log.v("Here","after");
		
		// 创建选择教室状态的Spinner
		classroomstatusspinner = (Spinner) findViewById(R.id.classroomstatusspinner);
		// classroomstatusarray
		// 应该也需要根据其他参数的选择情况自动过滤
		String[] classroomstatusarray = { "空闲", "占用" };
		classroomstatusadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, classroomstatusarray);
		// 设置样式
		classroomstatusadapter
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		classroomstatusspinner.setAdapter(classroomstatusadapter);
		
		//点击“搜索”转到结果页，应该为点击搜索向后台传值，之后再修改吧
		Button search = (Button) findViewById(R.id.searchbtn);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent searchToResult = new Intent(MainActivity.this, Results.class);
				startActivity(searchToResult);
			}
		});
		
		//点击“历史查询”转到历史查询页，应该为点击搜索向后台传值，之后再修改吧;而且这里还没实现调到历史结果那个tab，建议删了这个Button
		Button historical_search = (Button) findViewById(R.id.hissearchbtn);
		historical_search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent searchToResult = new Intent(MainActivity.this, Results.class);
				startActivity(searchToResult);
			}
		});
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
			// 设置文本的内容：
			startdate.setText(new StringBuilder().append(startYear).append("年")
					.append(startMonth + 1).append("月")// 得到的月份+1，因为从0开始
					.append(startDay).append("日"));
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
}
