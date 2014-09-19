package util;

import java.lang.reflect.Constructor;
import java.util.Properties;

import utilInter.DBManagerInter;
import utilInter.QuerySqlInter;
import utilInter.dealWithTimeInter;
import utilInter.dealWithUrlInter;
import utilInter.readAssetsInter;
import android.content.Context;

public class utilFactory {
	public static DBManagerInter getDbManager(Context context){
		DBManagerInter dbManager = null;
		String a = "";
		
		Properties properties = new Properties();
		try {
			properties.load(utilFactory.class.getResourceAsStream("/assets/util.properties")); 
			Class clazz = Class.forName(properties.getProperty("DBManager_util"));
			//带参数构造
			Constructor<DBManagerInter> constructor = clazz.getConstructor(Context.class);
			dbManager=(DBManagerInter)constructor.newInstance(context);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dbManager;
	}
	
	public static dealWithTimeInter getDealWithTime(){
		dealWithTimeInter dealWithTime = null;
		
		Properties properties = new Properties();
		try {
			properties.load(utilFactory.class.getResourceAsStream("/assets/util.properties")); 
			Class clazz = Class.forName(properties.getProperty("dealWithTime_util"));
			//带参数构造
			dealWithTime=(dealWithTimeInter)clazz.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dealWithTime;
	}
	
	public static dealWithUrlInter getDealWithUrl(){
		dealWithUrlInter dealWithUrl = null;
		
		Properties properties = new Properties();
		try {
			properties.load(utilFactory.class.getResourceAsStream("/assets/util.properties")); 
			Class clazz = Class.forName(properties.getProperty("dealWithUrl_util"));
			//带参数构造
			dealWithUrl=(dealWithUrlInter)clazz.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return dealWithUrl;
	}
	
	public static QuerySqlInter getQuerySql(){
		QuerySqlInter QuerySql = null;
		
		Properties properties = new Properties();
		try {
			properties.load(utilFactory.class.getResourceAsStream("/assets/util.properties")); 
			Class clazz = Class.forName(properties.getProperty("QuerySql_util"));
			//带参数构造
			QuerySql=(QuerySqlInter)clazz.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return QuerySql;
	}
	
	public static readAssetsInter getreadAssets(){
		readAssetsInter readAssets = null;
		
		Properties properties = new Properties();
		try {
			properties.load(utilFactory.class.getResourceAsStream("/assets/util.properties")); 
			Class clazz = Class.forName(properties.getProperty("readAssets_util"));
			//带参数构造
			readAssets=(readAssetsInter)clazz.newInstance();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return readAssets;
	}
}
