package dao;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.DBManager;
import util.dealWithTime;
import util.dealWithUrl;
import android.util.Log;
import domain.RecordForDao;
import domain.query;

public class HttpHelper_parse implements Runnable {

	private static DBManager dbMgr;
	private query userQuery;
	private ArrayList<String> queryUrlList;
	private static int flag=0;
	//private static Object lock = new Object();
	
	public HttpHelper_parse()
	{
		super();
	}
	
	public HttpHelper_parse(query userQuery,ArrayList<String> queryUrlList) {
		super();
		this.userQuery = userQuery;
		this.queryUrlList = queryUrlList;
	}



	@Override
	public void run(){
		// TODO Auto-generated method stub

		
		dealWithTime timeDealer = new dealWithTime();
		//Log.v("httphelper_parse","before for");
		try {
			for (String queryUrl : queryUrlList) {

				ArrayList<RecordForDao> recordsList = new ArrayList<RecordForDao>();
				String recordRoomId = new dealWithUrl().getRmId(queryUrl);
				String recordDate = new dealWithUrl().getDate(queryUrl);
				//Log.v("url cnt", recordRoomId + recordDate);
				String bestAvailableStartTime = null;
				String bestAvailableEndTime = null;
				String lastRecordEndTime = null;
				String bestOverlap = "0000";
				int recordNum = 1;
				Document doc = null;
				
				//synchronized (lock) {
					//Log.v("before doc",queryUrl);
					doc = Jsoup.connect(queryUrl).get();
				//}
				String recordType = doc.getElementsByTag("result").text();

				//Log.v("recordType", "recodeType");
				if (recordType.equals("占用")) {
					Elements records = doc.getElementsByTag("record");
					for (Element record : records) {
						// String recordDate
						String recordOverlap;
						String recordName = record.getElementsByTag("name").text();
						String recordDepartment = record.getElementsByTag(
								"department").text();
						String recordStartTime = record.getElementsByTag("start")
								.text().replaceAll(":", "");
						if (recordNum == 1) {
							if (timeDealer.compareTime(userQuery.getStartTime(),
									recordStartTime)) {
								// 若第一条记录开始时间在用户查询起始时间之前，则第一条记录overlap为0000
								recordOverlap = "0000";
							} else {
								lastRecordEndTime = userQuery.getStartTime();
								recordOverlap = timeDealer.calOverlap(
										recordStartTime, lastRecordEndTime);
							}
						} else {
							recordOverlap = timeDealer.calOverlap(recordStartTime,
									lastRecordEndTime);
						}

						// 插入空闲条目
						if (!(recordOverlap.equals("0000"))) {
							RecordForDao availableRecordLine = new RecordForDao(
									lastRecordEndTime, recordStartTime,
									recordOverlap);
							recordsList.add(availableRecordLine);
							recordNum++;
						}

						// 更新bestOverlap
						if (timeDealer.compareTime(recordOverlap, bestOverlap)) {
							bestOverlap = recordOverlap;
							bestAvailableStartTime = lastRecordEndTime;
							bestAvailableEndTime = recordStartTime;
						}

						String recordEndTime = record.getElementsByTag("end")
								.text().replaceAll(":", "");
						lastRecordEndTime = recordEndTime;
						String recordState = record.getElementsByTag("state")
								.text();
						String recordContent = record.getElementsByTag("content")
								.text();
						RecordForDao recordLine = new RecordForDao(recordType,
								recordStartTime, recordEndTime, recordOverlap,
								recordName, recordDepartment, recordState,
								recordContent);
						recordsList.add(recordLine);
						recordNum++;
					}
				} else {
					lastRecordEndTime = userQuery.getStartTime();
				}

				// 插入最后一条空闲
				if (timeDealer.compareTime(userQuery.getEndTime(),
						lastRecordEndTime)) {
					String recordOverlap = timeDealer.calOverlap(
							userQuery.getEndTime(), lastRecordEndTime);

					// 更新bestOverlap
					if (timeDealer.compareTime(recordOverlap, bestOverlap)) {
						bestOverlap = recordOverlap;
						bestAvailableStartTime = lastRecordEndTime;
						bestAvailableEndTime = userQuery.getEndTime();
					}

					// 生成空闲条目
					RecordForDao availableRecordLine = new RecordForDao(
							lastRecordEndTime, userQuery.getEndTime(),
							recordOverlap);
					recordsList.add(availableRecordLine);
				}

				float bestOverlapFloat = timeDealer.dateStringToFloat(bestOverlap);

				RecordForDao.setSharedValues(recordRoomId, recordDate, bestOverlap,
						bestOverlapFloat, bestAvailableStartTime,
						bestAvailableEndTime);
//				System.out.println(RecordForDao.getRecordRoomId() + ";"
//						+ RecordForDao.getRecordDate() + ";"
//						+ RecordForDao.getBestOverlap() + ";"
//						+ RecordForDao.getBestAvailableStartTime() + ";"
//						+ RecordForDao.getBestAvailableEndTime());

				//synchronized (lock) {
					dbMgr.insertRecordForDao(recordsList);
				//}
				//Log.v("db", "print");
				//dbMgr.printDB();
				Log.v("db", "end insert into db");
				// break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		flag++;
		//Log.v("Thread_flag",""+flag);
	}

	public static int getFlag() {
		return flag;
	}

	public static void setFlag(int flag) {
		HttpHelper_parse.flag = flag;
	}

	public static void setDbMgr(DBManager dbMgr) {
		HttpHelper_parse.dbMgr = dbMgr;
	}
}
