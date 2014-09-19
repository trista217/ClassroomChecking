package dao;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import util.dealWithTime;
import util.dealWithUrl;
import util.utilFactory;
import utilInter.DBManagerInter;
import utilInter.dealWithUrlInter;
import domain.RecordForDao;
import domain.query;

public class HttpHelper_parse implements Runnable {

	private static DBManagerInter dbMgr;
	private query userQuery;
	private ArrayList<String> queryUrlList;
	private static int flag = 0;

	public HttpHelper_parse() {
		super();
	}

	public HttpHelper_parse(query userQuery, ArrayList<String> queryUrlList) {
		super();
		this.userQuery = userQuery;
		this.queryUrlList = queryUrlList;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub

		dealWithTime timeDealer = new dealWithTime();
		try {
			for (String queryUrl : queryUrlList) {

				ArrayList<RecordForDao> recordsList = new ArrayList<RecordForDao>();
				dealWithUrlInter dealWithUrl = utilFactory.getDealWithUrl();
				String recordRoomId = dealWithUrl.getRmId(queryUrl);
				String recordDate = new dealWithUrl().getDate(queryUrl);
				String bestAvailableStartTime = "0000";
				String bestAvailableEndTime = "0000";
				String lastRecordEndTime = "";
				String bestOverlap = "0000";
				int recordNum = 1;
				Document doc = null;

				doc = Jsoup.connect(queryUrl).timeout(0).get();
				String recordType = doc.getElementsByTag("result").text();

				if (recordType.equals("占用")) {
					Elements records = doc.getElementsByTag("record");
					for (Element record : records) {

						String recordOverlap;
						String recordName = record.getElementsByTag("name")
								.text();
						String recordDepartment = record.getElementsByTag(
								"department").text();
						String recordStartTime = record
								.getElementsByTag("start").text()
								.replaceAll(":", "");
						if (recordNum == 1) {
							if (timeDealer.compareTime(
									userQuery.getStartTime(), recordStartTime)) {
								// 若第一条记录开始时间在用户查询起始时间之前，则第一条记录overlap为0000
								recordOverlap = "0000";
							} else {
								lastRecordEndTime = userQuery.getStartTime();
								recordOverlap = timeDealer.calOverlap(
										recordStartTime, lastRecordEndTime);
							}
						} else {
							recordOverlap = timeDealer.calOverlap(
									recordStartTime, lastRecordEndTime);
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
						String recordContent = record.getElementsByTag(
								"content").text();
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

				float bestOverlapFloat = timeDealer
						.dateStringToFloat(bestOverlap);

				if (bestAvailableStartTime == null) {
					bestAvailableStartTime = "0000";
				}
				if (bestAvailableEndTime == null)
					bestAvailableEndTime = "0000";

				for (RecordForDao recordLine : recordsList) {
					recordLine.setSharedValues(recordRoomId, recordDate,
							bestOverlap, bestOverlapFloat,
							bestAvailableStartTime, bestAvailableEndTime);
					dbMgr.insertRecordForDao(recordLine);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		flag++;
	}

	public static int getFlag() {
		return flag;
	}

	public static void setFlag(int flag) {
		HttpHelper_parse.flag = flag;
	}

	public static void setDbMgr(DBManagerInter dbMgr) {
		HttpHelper_parse.dbMgr = dbMgr;
	}
}
