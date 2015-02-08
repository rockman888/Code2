package at.compass;


import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener 
{

	// define the display assemble compass picture
	private ImageView image;
	
	// record the compass picture angle turned
	private float currentDegree = 0f;
	
	// device sensor manager
	private SensorManager mSensorManager;
	
	TextView tvHeading;
	TextView tvHeadingChinese;
		
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
			
		setContentView(R.layout.activity_main);
		
		image = (ImageView)findViewById(R.id.imageViewCompass);
		
		// TextView that will tell the user what degree is he heading
		tvHeading = (TextView)findViewById(R.id.tvHeading);
		tvHeadingChinese = (TextView)findViewById(R.id.tvHeadingChinese);
		
		// initialize your android device sensor capabilities
		mSensorManager = (SensorManager)getSystemService(SENSOR_SERVICE);
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
				
		switch(item.getItemId())
		{
			case R.id.about:
				AlertDialog.Builder builder = new Builder(this);
				builder.setTitle("Giới Thiệu");
				builder.setMessage("Nhà Phát Triển: \nViLH\nLiên Hệ: \n" + "huuvi168@gmail.com");
				builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				
				builder.setIcon(android.R.drawable.btn_star_big_on);
				builder.show();
				break;
				
			default:
				break;
		}
		
		return super.onMenuItemSelected(featureId, item);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.my_menu, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		
		// to stop the listener and save battery
		mSensorManager.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		// for the system's orientation sensor registered listeners
		mSensorManager.registerListener(this, mSensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION), SensorManager.SENSOR_DELAY_GAME);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		
		float degree = Math.round(event.values[0]);
		tvHeading.setText("Heading " + Float.toString(degree) + " degrees");
		tvHeadingChinese.setText("面向 " + Float.toString(degree) + " 度");
		
		// Create a rotation animation (reverse turn degree degrees)
		RotateAnimation ra = new RotateAnimation(
				currentDegree,
				-degree,
				Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF,
				0.5f);
		
		// how long the animation will take place
		ra.setDuration(210);
		
		// set the animation after the end of the reservation status
		ra.setFillAfter(true);
		
		// start the animation
		image.startAnimation(ra);
		currentDegree = -degree;
		
		Log.d("rotation ..", String.valueOf(currentDegree));
				
	}

}
