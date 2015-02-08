// *****************
// form add Time
// *****************


package at.listviewdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;



public class AddTimeActivity extends Activity 
{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_time_view);
	}
	
	public void onCancelButtonClicked(View v)
	{
		// khong nen goi nhu vay
		//intent = new Intent(this, ListViewDemo.class);		
		// start the activity
		//startActivity(intent);
		
		// call finish in the Activity base class
		finish();
	}
	
	public void onSaveButtonClicked(View v)
	{
		// calling getIntent() retrieves the starting intent from a running activity 
		Intent intent = getIntent();
		
		// get a reference to the time EditText, and put its value in the intent using the string constant
		EditText timeView= (EditText)findViewById(R.id.time_view);
		intent.putExtra("time", timeView.getText().toString());
		
		// get a reference to the notes EditText, and put its value in the intent using the string constant
		EditText notesView = (EditText)findViewById(R.id.notes_view);
		intent.putExtra("notes", notesView.getText().toString());
		
		// set the result to OK and pass in the intent
		this.setResult(RESULT_OK, intent);
		
		// finish the activity
		finish();
		
	}

	// trả kết quả về cho main form
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		
		super.onActivityResult(requestCode, resultCode, data);
	}
}
