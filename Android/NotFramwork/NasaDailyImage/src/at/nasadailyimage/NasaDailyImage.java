package at.nasadailyimage;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NasaDailyImage extends Activity {

	
	private IotdHandle iot;
	private Handler handler;
	private ProgressDialog dialog;
	
	public void newsDisplay()
	{
		// Create the handler
		
		dialog = ProgressDialog.show(this, "Loading", "Loading the Image of the Day!");
		
		Thread th  =  new Thread()
		{
			public void run()
			{
				if (iot == null)
					iot = new IotdHandle();
				
				iot.ProcessFeed();
				handler.post(
						new Runnable() {
							
							@Override
							public void run() {
								// TODO Auto-generated method stub
								// This method is a helper method u're about to write to populate the fields on screen with a parsed data
								resetDisplay(iot.getTitle(), iot.getDate(), iot.getImage(), iot.getDescription());
								
								dialog.dismiss();
								
							}
						});
			}
		};
		th.start();
	
		
	}
	
	
	private void resetDisplay(String title, String date, Bitmap image, StringBuffer description)
	{
		// Get a reference to the title view and set the text to the cached value from the handler
		TextView titleView = (TextView)findViewById(R.id.imageTitle);
		titleView.setText(title);
		
		
		// same deal with date View:get the View reference and set the text to the value from the parser
		TextView dateView = (TextView)findViewById(R.id.imageDate);
		dateView.setText(date);
		
		// get a reference to the imageView
		ImageView imageView = (ImageView)findViewById(R.id.imageDisplay);
		imageView.setImageBitmap(image); // Use the image from the feed parser and set it on the image view
		
		// finish up by getting the description View reference and settings the text with the cached description value
		TextView desciptionView = (TextView)findViewById(R.id.imageDescription);
		desciptionView.setText(description); 
		
	}
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// ProgressDialog.show(this, "Loading", "Loading the Image of the Day!");
		// Toast.makeText(this, "hello", Toast.LENGTH_SHORT).show();
		
		newsDisplay();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
