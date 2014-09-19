package utilInter;

import java.util.ArrayList;
import java.util.Map;

import domain.Record;
import domain.RecordForDao;
import domain.Results;
import domain.query;

public interface DBManagerInter {
	public Map<String, ArrayList<String>> getfull(String field);
	public ArrayList<String> nametoid(ArrayList<String> arrayList);
	public String RoomInfoByRoomID(String field, String roomID);
	public Map<String, ArrayList<String>> getbythreelimit(Map<String, ArrayList<String>> map);
	public Results fetchResult(query q);
	public void insertRecordForDao(RecordForDao recordLine);
	public ArrayList<Record> RoomDateToRecordList(String roomID, String Date);
	public void close();
	public void clean();
}
