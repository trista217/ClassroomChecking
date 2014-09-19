package util;

import utilInter.readAssetsInter;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class StdDBHelper extends SQLiteOpenHelper{

	private static final String DATABASE_NAME = "ClassroomChecking.db";
	private static final String TABLE1_NAME = "Classroom";
	private static final String TABLE2_NAME = "OrderRecord";
	private static final int DATABASE_VERSION = 6;
	private Context context_;

	public StdDBHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		context_ = context;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.v("log:create local classroom table","create classroom table");
		String createTable1 = "CREATE TABLE "
				+ TABLE1_NAME
				+ "(room String primary key,num integer, "
				+ "numCode integer no null,typeCode integer no null,"
				+ "ClassroomName String,type String no null,NumRange String no null)";
		db.execSQL(createTable1);
		Log.v("log:create local search record table","create search record table");
		String createTable2 = "CREATE TABLE "
				+ TABLE2_NAME
				+ " (room varchar(10), date char(8), bestOverlap char(4), bestOverlapFloat float,bestAvailableStartTime char(4), bestAvailableEndTime char(4),"
				+ "recordType text, recordStartTime char(4), recordEndTime char(4), recordOverlap char(4), recordName text, recordDepartment text, recordStatus text, recordContent text)";
		db.execSQL(createTable2);
		Log.v("log:read classroom info from txt and write into classroom table","read classroom info and write into database");
		readAssetsInter classroomQuery = utilFactory.getreadAssets();
		String classroom_info = classroomQuery.getFromAssets(
				"classroom_full.txt", context_);
		String[] info = classroom_info.split("\n");

		for (int i = 0; i < info.length; i++) {
			long id;
			ContentValues cv = new ContentValues();
			String[] fields = info[i].split("\t");
			cv.put("room", fields[0]);
			cv.put("num", Integer.parseInt(fields[1]));
			cv.put("numCode", Integer.parseInt(fields[2]));
			cv.put("typeCode", Integer.parseInt(fields[3]));
			cv.put("ClassroomName", fields[4]);
			cv.put("type", fields[5]);
			cv.put("NumRange", fields[6]);
			id = db.insert(TABLE1_NAME, null, cv);
		}
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE1_NAME);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE2_NAME);
		onCreate(db);
	}

}
