package service_impl;

import java.util.ArrayList;
import java.util.Map;

import util.DBManager;
import android.util.Log;

public class checkAndSearch {
	
	public Map<String, ArrayList<String>> checkMap(Map<String, ArrayList<String>> map,DBManager dbManager)
	{
		Log.v("checkMap","here");
		Map<String, ArrayList<String>> result=dbManager.getbythreelimit(map);
		Log.v("result of checkMap",result.toString());
		return result;
	}

	public  ArrayList<String> NameToID(ArrayList<String> arrayList,
			DBManager dbManager) {
		// TODO Auto-generated method stub
		Log.v("NameToID","here");
		ArrayList<String> result=dbManager.nametoid(arrayList);
		Log.v("result of NameToID",result.toString());
		return result;
	}
}
