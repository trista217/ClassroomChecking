package util;

public class dealWithUrl {

	public String getRmId(String url) {
		url = url.substring(115);
		String[] urlAfterSplit = new String[4];
		urlAfterSplit = url.split("&");
		String rmId = urlAfterSplit[0].substring(6);
		return rmId;
	}

	public String getDate(String url) {
		url = url.substring(115);
		String[] urlAfterSplit = new String[4];
		urlAfterSplit = url.split("&");
		String queryDate = urlAfterSplit[1].substring(8);
		return queryDate;
	}
}
