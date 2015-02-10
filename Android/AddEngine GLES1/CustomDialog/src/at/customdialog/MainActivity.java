package at.customdialog;

import android.os.Bundle;
import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.widget.Button;
import android.widget.DialerFilter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button buttonClick;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		buttonClick = (Button)findViewById(R.id.buttonClick);
		
		buttonClick.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.d("ssss", "click!");
				customDialog2();
			}
		});
	}
	
	public void customDialog2()
	{
		final Dialog dialog = new Dialog(MainActivity.this);
		// dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		dialog.setContentView(R.layout.custom_dialog);
		
		dialog.show();
//		
//		final EditText editText = (EditText)dialog.findViewById(R.id.editText1);
//		Button button = (Button)dialog.findViewById(R.id.button1);
//		button.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				editText.setText("good luck!");
//				
//			}
//		});				
	}

	public void customDialog1()
	{
		// create custom dialog Object
		final Dialog dialog = new Dialog(MainActivity.this);
		
		// Include dialog.xml
		dialog.setContentView(R.layout.dialog);
		
		// set dialog title
		dialog.setTitle("Custom Dialog");
		
		// set values for custom dialog components
		TextView text = (TextView)dialog.findViewById(R.id.textDialog);
		text.setText("Custom dialog Android example");
		
		ImageView image = (ImageView)dialog.findViewById(R.id.imageDialog);
		image.setImageResource(R.drawable.ic_launcher);
		
		dialog.show();
		
		Button declineButton = (Button)dialog.findViewById(R.id.declineButton);
		
		// if decline button is clicked, close the custom dialog
		declineButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			
			}
		});
	}
		
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
