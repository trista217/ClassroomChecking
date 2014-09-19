package utilInter;

import java.util.ArrayList;
import java.util.Map;

import util.StdDBHelper;

public interface QuerySqlInter {
	public Map<String, ArrayList<String>> sqlQuery(String sql,StdDBHelper dbHelper);
}
