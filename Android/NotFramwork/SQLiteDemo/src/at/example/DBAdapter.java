package at.example;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBAdapter 
{
	public static final String TAG ="DBAdapter";
	public static final String KEY_ID = "_id";
	public static final String KEY_NAME = "name";
	
	
	private DatabaseHelper mDbHelper;
	private SQLiteDatabase mDB;

	
	private static final String DATABASE_CREATE ="Create table users (_id integet primary key autoincrement, name text not null);";
			
	
	private static final String DATABASE_NAME = "Database_Demo";	
	private static final String DATABASE_TABLE = "users";
	private static final int DATABASE_VERSION = 2;
	private  Context mContext;
	
	private static class DatabaseHelper extends SQLiteOpenHelper
	{
		public DatabaseHelper(Context context, String name, CursorFactory factory, int version)
		{
			super(context, name, factory, version);
			
			// TODO Auto-generated constructor stub
		}	
		
		@Override
		public void onCreate(SQLiteDatabase db)
		{
			db.execSQL(DATABASE_CREATE);				
		}
		
		@Override 
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
		{
			Log.i(TAG, "Upgrading DB");
			db.execSQL("DROP TABLE IF EXISTS users");
			onCreate(db);
		}
	
	}

	
	public DBAdapter open()
	{
		mDbHelper = new DatabaseHelper(mContext, DATABASE_NAME, null, DATABASE_VERSION);
		mDB = mDbHelper.getWritableDatabase();
		return this;
		
	}
	
	public long createUser(String name)
	{
		ContentValues inititalValues = new ContentValues();
		inititalValues.put(KEY_NAME, name);
		return mDB.insert(DATABASE_TABLE, null, inititalValues);
	}
	
	// get toàn bộ data hay có thể get data theo ID (tiện cho việc chỉnh sửa hay cập nhật thông tin của từng bản ghi)
	public Cursor getAllUser()
	{
		return mDB.query(DATABASE_NAME, new String[]{KEY_ID, KEY_NAME}, null, null, null, null, null);
	}
	
	public void Close()
	{
		mDbHelper.close();
	}
	
	
	public boolean deleteUser(long rowId)
	{
		return mDB.delete(DATABASE_TABLE, KEY_ID + "=" + rowId, null) >0;
	}

	
	// test CSDL
		/*public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_main);
			mDB = new DBAdapter(this);
			mDB.open();
			mDB.createUser("Ly Huu Vi");
			getData();
		}
		
		private void getData()
		{
			mCursor = mDB.getAllUsers();
			startManagingCursor(mCursor);
			String[] from = new String[] {DBAdapter.KEY_NAME};
			int[] to = new int[] {R.id.text1};
			SimpleCursorAdapter users = new SimpleCursorAdapter(this,  R.layout.usesr_row, mCursor, from, to, flags)
			
			SetListAdapter(users);
		}*/
}
