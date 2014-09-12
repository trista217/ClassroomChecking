package domain;

import java.util.ArrayList;
import java.util.Map;

public class QueryAndUrlsForAsync {
	private query userQuery;
	private Map<String,ArrayList<String>> queryUrlList;
	//public static Object lock;

	public QueryAndUrlsForAsync() {
		super();
	}

	public QueryAndUrlsForAsync(query userQuery, Map<String,ArrayList<String>> queryUrlList) {
		this.userQuery = userQuery;
		this.queryUrlList = queryUrlList;
		//this.lock=lock;
	}

	public query getUserQuery() {
		return userQuery;
	}

	public void setUserQuery(query userQuery) {
		this.userQuery = userQuery;
	}

	public Map<String,ArrayList<String>> getQueryUrlList() {
		return queryUrlList;
	}

	public void setQueryUrlList(Map<String,ArrayList<String>> queryUrlList) {
		this.queryUrlList = queryUrlList;
	}

}
