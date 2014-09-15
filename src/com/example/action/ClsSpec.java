package com.example.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.bidaround.point.YtLog;
import cn.bidaround.youtui_template.YouTuiViewType;
import cn.bidaround.youtui_template.YtTemplate;
import cn.bidaround.ytcore.ErrorInfo;
import cn.bidaround.ytcore.YtShareListener;
import cn.bidaround.ytcore.data.ShareData;
import cn.bidaround.ytcore.data.YtPlatform;
import domain.Record;
import domain.ResultDetails;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class ClsSpec extends Activity {
	
	//正式数据
	ResultDetails rd = new ResultDetails();
	ArrayList<Record> rec;
	String date = new String();
	
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		YtTemplate.release(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clsspec);
		
		YtTemplate.init(this);
        Button btn1 =(Button) findViewById(R.id.action_share);
		
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
		Log.v("num of records", rec.size() + "  " + rec);
		for (int i = 0; i < rec.size(); i++) {
			Map<String, Object> item = new HashMap<String, Object>();
			item.put("spec_person", rec.get(i).getPersonName());
			item.put("spec_school", rec.get(i).getDepartment());
			item.put("spec_date", date.substring(0, 4) + "-" + date.substring(4, 6) + "-" + date.substring(6, 8)); 
			String rec_time = rec.get(i).getStartTime().substring(0, 2) + ":" + rec.get(i).getStartTime().substring(2, 4) + "-" + rec.get(i).getEndTime().substring(0, 2) + ":" + rec.get(i).getEndTime().substring(2, 4);
			if (rec_time.equals("00:00-00:00"))
				item.put("spec_time", "无");
			else
				item.put("spec_time", rec_time);
			//item.put("spec_time", rec.get(i).getStartTime().substring(0, 1) + ":" + rec.get(i).getStartTime().substring(2, 3) + "-" + rec.get(i).getEndTime().substring(0, 1) + ":" + rec.get(i).getEndTime().substring(2, 3));
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
		case R.id.action_share:{
			Toast.makeText(this, "share", Toast.LENGTH_SHORT).show();
			try {
				// ShareData使用内容分享类型分享类型
				ShareData whiteViewShareData = new ShareData();
				whiteViewShareData.isAppShare = false;
				whiteViewShareData.setDescription("经管学院教室使用情况");
				whiteViewShareData.setTitle("经管学院教室使用情况");
				whiteViewShareData.setText("以下为教室使用情况信息：");
				//whiteViewShareData.setTarget_url("http://apk.hiapk.com/html/2014/06/2770934.html?module=256&info=HHNmjwdo");
				whiteViewShareData.setImageUrl("http://youtui.oss-cn-hangzhou.aliyuncs.com/AppLogo/ic_launcher.png"); 
				YtTemplate whiteGridTemplate = new YtTemplate(ClsSpec.this, YouTuiViewType.BLACK_POPUP, false);
				whiteGridTemplate.setShareData(whiteViewShareData);
				YtShareListener whiteViewListener = new YtShareListener() {
				@Override
				public void onSuccess(ErrorInfo error) {
				YtLog.e("----", error.getErrorMessage());      
				}
				@Override
				public void onPreShare() {
				}
				@Override
				public void onError(ErrorInfo error) {
				YtLog.e("----", error.getErrorMessage());      
				}
				@Override
				public void onCancel() {
				}
				};
				/** 添加分享结果监听,如果开发者不需要处理回调事件则不必设置 */
				whiteGridTemplate.addListener(YtPlatform.PLATFORM_QQ, whiteViewListener);
				whiteGridTemplate.addListener(YtPlatform.PLATFORM_QZONE, whiteViewListener);
				whiteGridTemplate.addListener(YtPlatform.PLATFORM_RENN, whiteViewListener);
				whiteGridTemplate.addListener(YtPlatform.PLATFORM_SINAWEIBO, whiteViewListener);
				whiteGridTemplate.addListener(YtPlatform.PLATFORM_TENCENTWEIBO, whiteViewListener);
				whiteGridTemplate.addListener(YtPlatform.PLATFORM_WECHAT, whiteViewListener);
				whiteGridTemplate.addListener(YtPlatform.PLATFORM_WECHATMOMENTS, whiteViewListener);
				/**
				 * 为每个平台添加分享数据,如果不单独添加,分享的为whiteViewTemplate.setShareData(
				 * whiteViewShareData)设置的分享数据
				 */
				whiteGridTemplate.addData(YtPlatform.PLATFORM_QQ, whiteViewShareData);
				whiteGridTemplate.addData(YtPlatform.PLATFORM_QZONE, whiteViewShareData);
				whiteGridTemplate.addData(YtPlatform.PLATFORM_RENN, whiteViewShareData);
				whiteGridTemplate.addData(YtPlatform.PLATFORM_SINAWEIBO, whiteViewShareData);
				whiteGridTemplate.addData(YtPlatform.PLATFORM_TENCENTWEIBO, whiteViewShareData);
				whiteGridTemplate.addData(YtPlatform.PLATFORM_WECHAT, whiteViewShareData);
				whiteGridTemplate.addData(YtPlatform.PLATFORM_WECHATMOMENTS, whiteViewShareData);
				whiteGridTemplate.addData(YtPlatform.PLATFORM_MESSAGE, whiteViewShareData);
				whiteGridTemplate.addData(YtPlatform.PLATFORM_EMAIL, whiteViewShareData);
				whiteGridTemplate.addData(YtPlatform.PLATFORM_MORE_SHARE, whiteViewShareData);
				  
				whiteGridTemplate.show();
				 
				 
				} catch (Exception e) {
				 e.printStackTrace();
				 
				}
			
			return true;
			}
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}