package serviceInter;

import java.util.ArrayList;
import java.util.Map;

import utilInter.DBManagerInter;

public interface checkAndSearchInter {
	public Map<String, ArrayList<String>> checkMap(Map<String, ArrayList<String>> map, DBManagerInter dbManager);
	public ArrayList<String> NameToID(ArrayList<String> arrayList,DBManagerInter dbManager);
}
