package com.example.action;

import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
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
	// �������������գ�
	private int startYear, endYear;
	private int startMonth, endMonth;
	private int startDay, endDay;
	private int startHour, endHour;
	private int startMin, endMin;
	// ����һ����һ�޶��ı�ʶ������ΪҪ��ʾDatePicker��TimePicker��Dialog��ID��
	static final int START_DATE_DIALOG_ID = 0;
	static final int END_DATE_DIALOG_ID = 1;
	static final int START_TIME_DIALOG_ID = 2;
	static final int END_TIME_DIALOG_ID = 3;

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
		// ��EditText����¼���������
		startdate.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// ����Activity��ķ�������ʾDialog:�����������������Activity�����Dialog���������ڣ�
				// ������� onCreateDialog(int)�ص�����������һ��Dialog
				showDialog(START_DATE_DIALOG_ID);
			}
		});
		enddate.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// ����Activity��ķ�������ʾDialog:�����������������Activity�����Dialog���������ڣ�
				// ������� onCreateDialog(int)�ص�����������һ��Dialog
				showDialog(END_DATE_DIALOG_ID);
			}
		});
		starttime.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// ����Activity��ķ�������ʾDialog:�����������������Activity�����Dialog���������ڣ�
				// ������� onCreateDialog(int)�ص�����������һ��Dialog
				showDialog(START_TIME_DIALOG_ID);
			}
		});
		endtime.setOnClickListener(new OnClickListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View v) {
				// ����Activity��ķ�������ʾDialog:�����������������Activity�����Dialog���������ڣ�
				// ������� onCreateDialog(int)�ص�����������һ��Dialog
				showDialog(END_TIME_DIALOG_ID);
			}
		});
		// ��õ�ǰ�����ں�ʱ�䣺
		final Calendar currentDate = Calendar.getInstance();
		startYear = endYear = currentDate.get(Calendar.YEAR);
		startMonth = endMonth = currentDate.get(Calendar.MONTH);
		startDay = endDay = currentDate.get(Calendar.DAY_OF_MONTH);
		startHour = endHour = currentDate.get(Calendar.HOUR_OF_DAY);
		startMin = endMin = currentDate.get(Calendar.MINUTE);
		// �����ı������ݣ�
		startdate.setText(new StringBuilder().append(startYear).append("��")
				.append(startMonth + 1).append("��")// �õ����·�+1����Ϊ��0��ʼ
				.append(startDay).append("��"));
		enddate.setText(new StringBuilder().append(endYear).append("��")
				.append(endMonth + 1).append("��")// �õ����·�+1����Ϊ��0��ʼ
				.append(endDay).append("��"));
		starttime.setText(new StringBuilder().append(pad(startHour))
				.append(":").append(pad(startMin)));
		endtime.setText(new StringBuilder().append(pad(endHour)).append(":")
				.append(pad(endMin)));
		
		// ����ѡ����Һŵ�MySpinner
		// ���Ը���service�����ݴ���ʵ�ָ�����ѡ����������Զ�ɸѡ���˽��Һţ������classroomnoarrayֻ�ǲ������ݼ�
		final String[] classroomnoarray ={"˴��101", "ΰ��101", "˴��102", "����"};
		MySpinner classroomNoMySpinner=(MySpinner) findViewById(R.id.classroomnospinner);
		classroomNoMySpinner.initContent(classroomnoarray);

		// ����ѡ�����������MySpinner
		// ���Ը���service�����ݴ���ʵ�ָ�����ѡ�����������Զ�ɸѡ���ˣ������classroomnoofpeoplearrayֻ�ǲ������ݼ�
		final String[] classroomnoofpeoplearray ={"1-10", "11-20", "21-30" };
		MySpinner classroomNoOfPeopleMySpinner=(MySpinner) findViewById(R.id.classroomnoofpeoplespinner);
		classroomNoOfPeopleMySpinner.initContent(classroomnoofpeoplearray);
		
		// ����ѡ��������͵�MySpinner
		// ���Ը���service�����ݴ���ʵ�ָ�����ѡ�����������Զ�ɸѡ���ˣ������classroomtypearrayֻ�ǲ������ݼ�
		final String[] classroomtypearray = { "����", "������", "������" };
		MySpinner classroomTypeMySpinner=(MySpinner) findViewById(R.id.classroomtypespinner);
		classroomTypeMySpinner.initContent(classroomtypearray);

		// ����ѡ�����״̬��Spinner
		classroomstatusspinner = (Spinner) findViewById(R.id.classroomstatusspinner);
		// classroomstatusarray
		// Ӧ��Ҳ��Ҫ��������������ѡ������Զ�����
		String[] classroomstatusarray = { "����", "ռ��" };
		classroomstatusadapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, classroomstatusarray);
		// ������ʽ
		classroomstatusadapter
				.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
		classroomstatusspinner.setAdapter(classroomstatusadapter);
		
		//�����������ת�����ҳ��Ӧ��Ϊ����������̨��ֵ��֮�����޸İ�
		Button search = (Button) findViewById(R.id.searchbtn);
		search.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent searchToResult = new Intent(MainActivity.this, Results.class);
				startActivity(searchToResult);
			}
		});
		
		//�������ʷ��ѯ��ת����ʷ��ѯҳ��Ӧ��Ϊ����������̨��ֵ��֮�����޸İ�;�������ﻹûʵ�ֵ�����ʷ����Ǹ�tab������ɾ�����Button
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

	// ��Ҫ���嵯����DatePicker�Ի�����¼���������
	private DatePickerDialog.OnDateSetListener startDateSetListener = new OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			startYear = year;
			startMonth = monthOfYear;
			startDay = dayOfMonth;
			// �����ı������ݣ�
			startdate.setText(new StringBuilder().append(startYear).append("��")
					.append(startMonth + 1).append("��")// �õ����·�+1����Ϊ��0��ʼ
					.append(startDay).append("��"));
		}
	};
	private DatePickerDialog.OnDateSetListener endDateSetListener = new OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			endYear = year;
			endMonth = monthOfYear;
			endDay = dayOfMonth;
			// �����ı������ݣ�
			enddate.setText(new StringBuilder().append(endYear).append("��")
					.append(endMonth + 1).append("��")// �õ����·�+1����Ϊ��0��ʼ
					.append(endDay).append("��"));
		}
	};

	/**
	 * ��Activity����showDialog����ʱ�ᴥ���ú����ĵ��ã�
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
