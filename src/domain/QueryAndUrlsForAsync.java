package domain;

import java.util.ArrayList;

public class QueryAndUrlsForAsync {
	private query userQuery;
	private ArrayList<String> queryUrlList;
	//public static Object lock;

	public QueryAndUrlsForAsync() {
		super();
	}

	public QueryAndUrlsForAsync(query userQuery, ArrayList<String> queryUrlList) {
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

	public ArrayList<String> getQueryUrlList() {
		return queryUrlList;
	}

	public void setQueryUrlList(ArrayList<String> queryUrlList) {
		this.queryUrlList = queryUrlList;
	}

}
