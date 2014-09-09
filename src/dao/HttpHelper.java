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
import domain.QueryAndUrlsForAsync;
import domain.RecordForDao;
import domain.query;

public class HttpHelper {

	private static DBManager dbMgr;

	public static void parseUrl(QueryAndUrlsForAsync queryAndUrls)
			throws IOException, ParseException {

		Log.v("parse url", "parse url");

		query userQuery = queryAndUrls.getUserQuery();
		ArrayList<String> queryUrlList = queryAndUrls.getQueryUrlList();
		dealWithTime timeDealer = new dealWithTime();
		System.out.println("contents from Jsoup:");

		for (String queryUrl : queryUrlList) {

			ArrayList<RecordForDao> recordsList = new ArrayList<RecordForDao>();
			String recordRoomId = new dealWithUrl().getRmId(queryUrl);
			String recordDate = new dealWithUrl().getDate(queryUrl);
			Log.v("url cnt", recordRoomId + recordDate);
			String bestAvailableStartTime = null;
			String bestAvailableEndTime = null;
			String lastRecordEndTime = null;
			String bestOverlap = "0000";
			int recordNum = 1;
			Document doc = Jsoup.connect(queryUrl).get();
			String recordType = doc.getElementsByTag("result").text();

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
			System.out.println(RecordForDao.getRecordRoomId() + ";"
					+ RecordForDao.getRecordDate() + ";"
					+ RecordForDao.getBestOverlap() + ";"
					+ RecordForDao.getBestAvailableStartTime() + ";"
					+ RecordForDao.getBestAvailableEndTime());

			dbMgr.insertRecordForDao(recordsList);
			Log.v("db", "print");
			//dbMgr.printDB();
			Log.v("db", "end insert into db");
			// break;
		}

	}

	public static void setDbMgr(DBManager dbMgr) {
		HttpHelper.dbMgr = dbMgr;
	}
}
