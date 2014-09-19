package serviceInter;

import utilInter.DBManagerInter;
import domain.ResultDetails;

public interface SearchToDetailsInter {
	public ResultDetails toDetails(String date, String roomID, DBManagerInter dbManager);
}
