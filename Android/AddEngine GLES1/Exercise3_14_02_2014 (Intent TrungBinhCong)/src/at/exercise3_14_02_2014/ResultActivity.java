package at.exercise3_14_02_2014;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class ResultActivity extends Activity 
{	
	// @Override 

	public void backButtonClicked(View v)
	{
		finish();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		
		Bundle data = getIntent().getExtras();
		Long a = data.getLong("a");
		Long b = data.getLong("b");
		Long c = data.getLong("c");
				
		Long result = (a+b+c) / 3;
		
		EditText et_result = (EditText)findViewById(R.id.et_result);
		et_result.setText(String.valueOf(result));
	}

}
