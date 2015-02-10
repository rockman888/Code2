package de.vogella.android.sensor.compass;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	
	 private static SensorManager sensorService;
	  private MyCompassView compassView;
	  private Sensor sensor;
	  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
/*		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);*/
		
		super.onCreate(savedInstanceState);
	    compassView = new MyCompassView(this);
	    setContentView(compassView);

	    sensorService = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    sensor = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	    if (sensor != null) 
	    {
	      sensorService.registerListener(mySensorEventListener, sensor,
	    		  						SensorManager.SENSOR_DELAY_NORMAL);
	      Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");

	    } else {
	      Log.e("Compass MainActivity", "Registerered for ORIENTATION Sensor");
	      Toast.makeText(this, "ORIENTATION Sensor not found",
	          Toast.LENGTH_LONG).show();
	      finish();
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		
		if (sensor != null) {
		      sensorService.unregisterListener(mySensorEventListener);
		    }
	}


	private SensorEventListener mySensorEventListener = new SensorEventListener() {
		
		
		@Override
		public void onSensorChanged(SensorEvent event) {
			// TODO Auto-generated method stub
			// angle between the magnetic north direction
		      // 0=North, 90=East, 180=South, 270=West
		      float azimuth = event.values[0];
		      compassView.updateData(azimuth);
			
		}
		
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			// TODO Auto-generated method stub
			
		}
		
	};

}
