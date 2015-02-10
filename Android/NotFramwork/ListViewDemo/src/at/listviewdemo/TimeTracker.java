package at.listviewdemo;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

public class TimeTracker extends Activity {

	private TimeTrackerAdapter timeTrackerAdapter;
	private TimeTrackerDatabaseHelper databaseHelper;
	
	
	/// the request code constant
	public static final int TIME_ENTRY_REQUEST_CODE = 1;
	
	// test
	public void test()
	{
		Cursor cursor = databaseHelper.getAllTimeRecords();
		
		// move the cursor to the first row, checking the boolean
		// response before continuing
		
		if (cursor.moveToFirst())
		{
			do
			{
				String time = cursor.getString(1);
				String notes = cursor.getString(2);
				Log.d("DB Value: ",  time + " - " + notes);
				
			}while (cursor.moveToNext());	// move to next if there are more rows
			
		}
		
		// always make sure to close the cursor when u're done
		if (!cursor.isClosed())
			cursor.close();
			
	}
	
	
	// hàm callback
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		
		// This check makes sure the requestCode is the Code u passed in
		if (requestCode == TIME_ENTRY_REQUEST_CODE)
		{
			
			// this checks that the resultCode is RESULT_OK, 
			// since u didn't set the result code in the onCancel, this will return instead of trying to add a new item
			if (resultCode == RESULT_OK)
			{				
				// get the values from the intent
				String notes = data.getStringExtra("notes");
				String time = data.getStringExtra("time");
				
				
				// SAVE DATABASE:
				// save the new time record in the database, and
				// update the cursor in the adapter
				databaseHelper.saveTimeRecord(time, notes);
				timeTrackerAdapter.changeCursor(databaseHelper.getAllTimeRecords());
				
				// Create a new TimeRecord and add it to the list adapter
				//TimeRecord t = new TimeRecord(time, notes);
				//timeTrackerAdapter.addTimeRecord(t);
				
				// This method lets the list know the data has changed and to update the display
				//timeTrackerAdapter.notifyDataSetChanged();
			}
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list_view_demo);
		
		
		// instantiating your custom open helper will call cause the database to be created.
		databaseHelper = new TimeTrackerDatabaseHelper(this);
				
		
		// Get a reference the ListView
		ListView listView = (ListView)findViewById(R.id.times_list);
		
		// Instantiable the adapter
		// pass in the Cursor and the context to the adapter 
		timeTrackerAdapter = new TimeTrackerAdapter(this, databaseHelper.getAllTimeRecords());
		
		// COnfigure the ListView to use the adapter;
		listView.setAdapter(timeTrackerAdapter);
		
				
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.

		
		super.onCreateOptionsMenu(menu);
		
		// add menu cach 1 - add truc tiep bang code
		// menu.add(0, Menu.FIRST, 0, "Delete").setIcon(android.R.drawable.ic_delete);
		
		
		// add menu cach 2 - voi file xml
		getMenuInflater().inflate(R.menu.time_list_menu, menu);
		
		return true;
	}

	//chọn menu ngữ cảnh :D
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		// check the item ID to see if the add action was selected
		switch(item.getItemId())
		{
			case R.id.add_time_menu_item:
				// Create and new intent to select AddTimeActivity and then start it
				Intent intent = new Intent(this, AddTimeActivity.class);
				
				
				//startActivity(intent);

				// ket qua tra ve tai day ...
				// replace the startActivity call with a call to startActivityForResult
				startActivityForResult(intent, TIME_ENTRY_REQUEST_CODE);
				
				// return true to indicate the select event was processed
				// return true;

				break;
			
			case R.id.test_menu_item:
				test();
				break;
		}
		
//		if (item.getItemId() == R.id.add_time_menu_item)
//		{
//			// Create and new intent to select AddTimeActivity and then start it
//			Intent intent = new Intent(this, AddTimeActivity.class);
//			
//			
//			//startActivity(intent);
//
//			// ket qua tra ve tai day ...
//			// replace the startActivity call with a call to startActivityForResult
//			startActivityForResult(intent, TIME_ENTRY_REQUEST_CODE);
//			
//			// return true to indicate the select event was processed
//			return true;
//
//		}
		
				
		// 
		return super.onMenuItemSelected(featureId, item);
	}


}
