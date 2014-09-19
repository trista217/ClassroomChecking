package service;

import java.util.ArrayList;
import java.util.Map;

import serviceInter.checkAndSearchInter;
import util.DBManager;
import utilInter.DBManagerInter;

public class checkAndSearch implements checkAndSearchInter{

	public Map<String, ArrayList<String>> checkMap(
			Map<String, ArrayList<String>> map, DBManagerInter dbManager) {
		// Log.v("log:checkMap","here");
		Map<String, ArrayList<String>> result = dbManager.getbythreelimit(map);
		// Log.v("log:result of checkMap",result.toString());
		return result;
	}

	public ArrayList<String> NameToID(ArrayList<String> arrayList,
			DBManagerInter dbManager) {
		// TODO Auto-generated method stub
		// Log.v("log:NameToID","here");
		ArrayList<String> result = dbManager.nametoid(arrayList);
		// Log.v("log:result of NameToID",result.toString());
		return result;
	}
}
