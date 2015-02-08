package at.exercise14_05_04_2014;

import java.sql.SQLClientInfoException;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MyDatabase 
{
	// tên database
	private static final String DATABASE_NAME = "DB_USER";
		
	// version database
	private static final int DATABASE_VERSION = 1;
	
	// Tên table và các column trong database
	private static final String TABLE_ACCOUNT 	 = "ACCOUNT";
	private static final String COLUMN_ID 		 = "_id";
	private static final String COLUMN_ACCOUNT   = "AccountName";
	private static final String COLUMN_PASSWORD = "Password";
	private static final String COLUMN_NAME		 = "Name";
	
	// Các đối tượng khác
	private static Context context;
	static SQLiteDatabase db;
	private OpenHelper openHelper;
	
	public MyDatabase(Context c)
	{
		MyDatabase.context = c;
	}
	
	// Hàm mở kết nối tới DB
	public MyDatabase open() throws SQLException
	{
		openHelper = new OpenHelper(context);
		db = openHelper.getWritableDatabase();
		return this;
	}
	
	// hàm đóng kết nối với database;
	public void close()
	{
		openHelper.close();
	}
	
	public long CreateData(String tenDN, String matkhau)
	{
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_ACCOUNT, tenDN);
		cv.put(COLUMN_PASSWORD, matkhau);
		cv.put(COLUMN_NAME, "nodata");
		
		return db.insert(TABLE_ACCOUNT, null,  cv);
	}
	
	// Hàm getData trả về toàn bộ dữ liệu của table ACCOUNT của database dưới 1 chuỗi!
	public String getData()
	{
		String[] columns = new String[] {COLUMN_ID, COLUMN_ACCOUNT, COLUMN_PASSWORD, COLUMN_NAME};
		Cursor c = db.query(TABLE_ACCOUNT, columns, null, null, null, null, null);
		
		
		String result = "";
		
		// getColumnsIndex(COLUMN_ID) ;=> lấy chỉ số, vị trí của cột COLUMN_ID,
		int iRow = c.getColumnIndex(COLUMN_ID);
		int iAcc = c.getColumnIndex(COLUMN_ACCOUNT);
		int iPass = c.getColumnIndex(COLUMN_PASSWORD);
		int iName= c.getColumnIndex(COLUMN_NAME);
		
		// vòng lặp lấy dữ liệu của con trỏ
		for (c.moveToFirst(); !c.isAfterLast(); c.moveToNext())
		{
			result += "" + c.getString(iRow);
			result += " -id: " + c.getString(iAcc);
			result += " -pass: " + c.getString(iPass);
			result += " -Name: " + c.getString(iName) +  "\n";
			
		}
		
		c.close();
		return result;
		
	}
	
	// hàm đăng nhập với đối số đầu vào là tên acc và mật khẩu?
	public boolean checkLogin(String acc, String mk)
	{
		String sql = "SELECT * FROM " + TABLE_ACCOUNT + " WHERE "; 
		sql += COLUMN_ACCOUNT + " = ? and ";
		sql += COLUMN_PASSWORD + "= ?";
		Cursor c = db.rawQuery(sql, new String[] { acc,mk });
		
		if (c.getCount() == 1)
		{
			c.close();
			return true;
		}
		else
		{
			c.close();
			return false;
		}
		
	}
	
	// Hàm xóa một tài khoản với đối số đầu vào là acc cần xóa
	public int deleteAcc(String acc)
	{
		return db.delete(TABLE_ACCOUNT, null,null);		
	}
	
	// Hàm cập nhật tên người dùng với đầu vào là acc, pass và name cần thay đổi
	public boolean setNameHienThi (String acc, String pw, String data)	
	{
		ContentValues cv = new ContentValues();
		cv.put(COLUMN_NAME,  data);
		
		String whereClause = COLUMN_ACCOUNT + "='" + acc + "'";
		long kq = db.update(TABLE_ACCOUNT, cv, whereClause, null);
		
		if (kq == 0)
			return false;
		else
			return true;
	}
	
	// hàm lấy về chuỗi String là tên của tài khoản
	public String getNameHienThi(String acc, String pw)
	{
		String sql = "SELECT * FROM " + TABLE_ACCOUNT + " WHERE ";
		sql += COLUMN_ACCOUNT + " = ? AND ";
		sql += COLUMN_PASSWORD + " = ?";
		Cursor c = db.rawQuery(sql, new String[]{acc, pw} );
		
		String data = "";
		
		if (c.getCount() == 1)
		{
			int i = c.getColumnIndex(COLUMN_NAME);
			c.moveToFirst();
			data = c.getString(i);
			c.close();
		}
		else
		{
			c.close();
			data = "Nothing ...";
		}

		return data;
	}
	
	
	
	// --------------------- Class OpenHelper ----------------------
	private static class OpenHelper extends SQLiteOpenHelper
	{

		// Tạo mới database
		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			
			String sql = "CREATE TABLE " + TABLE_ACCOUNT + " (";
			sql	+= COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, ";
			sql += COLUMN_ACCOUNT + " TEXT NOT NULL, ";
			sql += COLUMN_PASSWORD + " TEXT NOT NULL, ";
			sql += COLUMN_NAME + " TEXT NOT NULL);";
			
			db.execSQL(sql);
			
		}

		// kiển tra phiên bản database nếu khác sẽ thay đổi
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			
			String sql = "DROP TABLE IF EXISTS " + TABLE_ACCOUNT;
			db.execSQL(sql);
			
		}

		public OpenHelper(Context context){
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}	
	}
}
