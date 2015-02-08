package de.vogella.android.sensor;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class SensorTestActivity extends Activity implements SensorEventListener{

	 private SensorManager sensorManager;
	 private boolean color = false;
	 private View view;
	 private long lastUpdate;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
/*		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sensor_test);
		*/
		
		 requestWindowFeature(Window.FEATURE_NO_TITLE);
		    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		        WindowManager.LayoutParams.FLAG_FULLSCREEN);

		    super.onCreate(savedInstanceState);
		    setContentView(R.layout.activity_sensor_test);
		    view = findViewById(R.id.textView);
		    view.setBackgroundColor(Color.YELLOW);

		    sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
		    lastUpdate = System.currentTimeMillis();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.sensor_test, menu);
		return true;
	}

	@Override
	public void onAccuracyChanged(Sensor arg0, int arg1) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		// TODO Auto-generated method stub
		 if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
		      getAccelerometer(event);
		    }
	}

	private void getAccelerometer(SensorEvent event) {
		// TODO Auto-generated method stub
		float[] values = event.values;
	    // Movement
	    float x = values[0];
	    float y = values[1];
	    float z = values[2];

	    float accelationSquareRoot = (x * x + y * y + z * z)
	        / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH);
	    long actualTime = System.currentTimeMillis();
	    if (accelationSquareRoot >= 2) //
	    {
	      if (actualTime - lastUpdate < 200) {
	        return;
	      }
	      lastUpdate = actualTime;
	      Toast.makeText(this, "Device was shuffed", Toast.LENGTH_SHORT)
	          .show();
	      if (color) {
	        view.setBackgroundColor(Color.YELLOW);

	      } else {
	        view.setBackgroundColor(Color.BLUE);
	      }
	      color = !color;
	    }
		
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		 sensorManager.unregisterListener(this);
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
		// register this class as a listener for the orientation and
	    // accelerometer sensors
	    sensorManager.registerListener(this,
	        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
	        SensorManager.SENSOR_DELAY_NORMAL);
	}

}
