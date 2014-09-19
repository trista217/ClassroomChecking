package util;

import java.util.Properties;

import service.SearchToDetails;
import service.checkAndSearch;
import serviceInter.SearchToDetailsInter;
import serviceInter.checkAndSearchInter;

public class serviceFactory {
	public static checkAndSearchInter getcheckAndSearch(){
		checkAndSearchInter checkAndSearch = null;
		
		Properties properties = new Properties();
		try {
			properties.load(utilFactory.class.getResourceAsStream("/assets/service.properties")); 
			Class clazz = Class.forName(properties.getProperty("checkAndSearch_service"));
			//带参数构造
			checkAndSearch=(checkAndSearchInter)clazz.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return checkAndSearch;
	}
	
	public static SearchToDetailsInter getSearchToDetails(){
		SearchToDetailsInter SearchToDetails = null;
		
		Properties properties = new Properties();
		try {
			properties.load(utilFactory.class.getResourceAsStream("/assets/service.properties")); 
			Class clazz = Class.forName(properties.getProperty("SearchToDetails_service"));
			//带参数构造
			SearchToDetails=(SearchToDetailsInter)clazz.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SearchToDetails;
	}
}
