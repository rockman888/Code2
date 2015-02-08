package at.listviewdemo;

// database store in this: /data/data/<package-name>/ databases<database-name>.

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class TimeTrackerDatabaseHelper {
	
	private static final int DATABASE_VERSION = 3;
	private static final String DATABASE_NAME = "timetracker.db";
	private static final String TABLE_NAME = "timerecords";
	
	private static final String TIMETRACKER_COLUMN_ID = "_id";
	private static final String TIMETRACKER_COLUMN_NAME = "time";
	private static final String TIMETRACKER_COLUMN_NOTES = "notes";

	// store variables for the OpenHelper and the database it opens
	private TimeTrackerOpenHelper openHelper;
	
	// open database it opens
	private SQLiteDatabase database;
	
	public TimeTrackerDatabaseHelper(Context context)
	{
		openHelper = new TimeTrackerOpenHelper(context);
		
		// GEt the writable db from the open helper
		database = openHelper.getWritableDatabase();		
	}
	
	
	// luu tru CSDL
	public void saveTimeRecord (String time, String notes)
	{
		// cach 1
		/*String sql = "INSERT INTO TIMERECORDS (TIME, NOTES) VALUES ('" + time + "', '" + notes + "')";
		database.execSQL(sql);*/
		
		// cach 2
		ContentValues contentValues = new ContentValues();
		
		contentValues.put(TIMETRACKER_COLUMN_NAME, time);
		contentValues.put(TIMETRACKER_COLUMN_NOTES, notes);
		
		database.insert(TABLE_NAME,null, contentValues);
		
	}
	
	public Cursor getAllTimeRecords()
	{
		String sql = "SELECT * FROM " + TABLE_NAME;
		return database.rawQuery(sql, null);
	}


	public class TimeTrackerOpenHelper extends SQLiteOpenHelper{
	
		public TimeTrackerOpenHelper(Context context)
		{
			// "timetracker.db": pass the name of the database to super
			// 1: pass the version number to super as well
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
			
			
		}
	
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
			// pass in the SQL statement to create the timerecords table
 
			// db.execSQL("CREATE TABLE timerecords (" + TIMETRACKER_COLUMN_ID + " INTEGER PRIMARY KEY, time TEXT, notes TEXT);");
			
			String sql = "CREATE TABLE timerecords (";
			sql += TIMETRACKER_COLUMN_ID + " INTEGER PRIMARY KEY, ";
			sql += TIMETRACKER_COLUMN_NAME + " TEXT, ";			
			sql += TIMETRACKER_COLUMN_NOTES + " TEXT);";
			
			db.execSQL(sql);
		}
	
		
		// when you updated the database version, which will drop and recreate the database, destroying all of stored
		// data. if you found this on a production system with real users and real data, this is when you would override onUpgrade 
		// to migrate the information from the old database format to the new one
		
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
			// Drop the tables if they exist and then call onCreate
			//String sql = "DROP TABLE IF EXISTS timerecords;";
			db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
			onCreate(db);
			
		}		
		
	}
}