package com.example.democallurl;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button btn = (Button)findViewById(R.id.button1);
		
		btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent browser = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com"));
				startActivity(browser);
				
			}
		});
		
		
		/*btn.setOnClickListener(new View.OnClickListener() { //set onclicklistener for button2
			public void onClick(View v) {
			Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.facebook.com"));
			startActivity(browserIntent);
			}
			});*/
	}
	
	public void onCancelButtonClicked(View v)
	{
		Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.vnexpress.net"));
		startActivity(browserIntent);		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	

}
