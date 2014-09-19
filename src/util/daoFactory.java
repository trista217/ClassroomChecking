package util;

import java.util.Properties;

import dao.generateQueryUrl;
import daoInter.generateQueryUrlInter;

public class daoFactory {
	public static generateQueryUrlInter getgenerateQueryUrl(){
		generateQueryUrlInter generateQueryUrl = null;
		
		Properties properties = new Properties();
		try {
			properties.load(utilFactory.class.getResourceAsStream("/assets/dao.properties")); 
			Class clazz = Class.forName(properties.getProperty("generateQueryUrl_dao"));
			//带参数构造
			generateQueryUrl = (generateQueryUrlInter)clazz.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return generateQueryUrl;
	}
}
