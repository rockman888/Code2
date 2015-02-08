package at.demotouchevent;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;

public class Pen extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pen);	
		
		// init Interface
		Intent in = getIntent();
		String mColor = in.getStringExtra("mColor");
		String mStyle = in.getStringExtra("mStyle");	
		
		// Log.d("vilh", mStyle);
		
		SeekBar sbStyle = (SeekBar)findViewById(R.id.seekBar_Style);
		EditText evStyle = (EditText)findViewById(R.id.editView_Style);
		RadioButton rbRed = (RadioButton)findViewById(R.id.radio_Red);
		RadioButton rbBlue = (RadioButton)findViewById(R.id.radio_Blue);
		
		if (mColor.equals("Red"))
			rbRed.setChecked(true);
		else
			rbBlue.setChecked(true);
					
		sbStyle.setProgress((Integer.valueOf(mStyle) - 1) * 20);
		evStyle.setText(mStyle);
		
		
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	public void onAgreeButtonClicked(View v)
	{
		Intent in = getIntent();
		String value;
		
		RadioGroup rg = (RadioGroup)findViewById(R.id.radioGroupColor);
		value = ((RadioButton)findViewById(rg.getCheckedRadioButtonId())).getText().toString();				
		in.putExtra("color_value", value);
		
		EditText evStyle = (EditText)findViewById(R.id.editView_Style);
		value = evStyle.getText().toString();
		in.putExtra("style_value", value);
				
				
		this.setResult(RESULT_OK, in);
		
		// finish the activity
		finish();
		
		

		
	}
	

}
