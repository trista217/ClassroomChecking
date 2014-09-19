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

import domain.DataPerQuery;
import domain.Result;
import domain.ResultDetails;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class His_Results extends Activity {

	private ArrayList<Result> his_resultArrayList = new ArrayList<Result>();

	private DataPerQuery dpq = new DataPerQuery();
	private domain.Results re = new domain.Results();
	//private query q = new query();

	//注销分享模块
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		YtTemplate.release(this);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.his_results);

		//注册分享模块
		YtTemplate.init(this);
        Button btn1 =(Button) findViewById(R.id.action_share);

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
			String str = "";
			String besttime = "";
			for (int i = 0; i < his_resultArrayList.size() & i<3 ; i++) {
				str += i+1+"、 教室："+his_resultArrayList.get(i).getClassroomName()
						+" 日期：" + his_resultArrayList.get(i).getDate().substring(0, 4) + "-" + his_resultArrayList.get(i).getDate().substring(4, 6) + "-" + his_resultArrayList.get(i).getDate().substring(6, 8)
						+" 最长空闲时间："+ his_resultArrayList.get(i).getBestOverlap()
						+" 最近空闲时间段：";
				besttime = his_resultArrayList.get(i).getBestAvailableStartTime().substring(0, 2) + ":" + his_resultArrayList.get(i).getBestAvailableStartTime().substring(2, 4) + "-" + his_resultArrayList.get(i).getBestAvailableEndTime().substring(0, 2) + ":" +his_resultArrayList.get(i).getBestAvailableEndTime().substring(2, 4);
				if (besttime.equals("00:00-00:00"))
					besttime = "无";
				str += besttime +"; ";
			}

				Log.v("log:查询结果分享","以下为时间最合适的教室："+str);

			try {
				// ShareData使用内容分享类型分享类型
				ShareData whiteViewShareData = new ShareData();
				whiteViewShareData.isAppShare = false;
				whiteViewShareData.setDescription("经管学院教室使用情况");
				whiteViewShareData.setTitle("经管学院教室使用情况");
				whiteViewShareData.setText("以下为时间最合适的教室："+str);
				//whiteViewShareData.setTarget_url("http://apk.hiapk.com/html/2014/06/2770934.html?module=256&info=HHNmjwdo");
				whiteViewShareData.setImageUrl("http://youtui.oss-cn-hangzhou.aliyuncs.com/AppLogo/ic_launcher.png");
				YtTemplate whiteGridTemplate = new YtTemplate(His_Results.this, YouTuiViewType.BLACK_POPUP, false);
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
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
