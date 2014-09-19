package daoInter;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Map;

import domain.query;

public interface generateQueryUrlInter {
	public Map<String, ArrayList<String>> genQueryUrl(query userQuery) throws ParseException;
}
