package at.demosufaceview;

import android.os.Bundle;
import android.app.Activity;
import android.view.Display;
import android.view.Menu;
import android.view.MotionEvent;

public class Main extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	private float lastX, lastY;
	private Ball ball, newball;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
	    Display currentDisplay = getWindowManager().getDefaultDisplay();
	    float dw = currentDisplay.getWidth();
	    float dh = currentDisplay.getHeight();
	    
		switch(event.getAction())
		{
			case MotionEvent.ACTION_DOWN:
				lastX = event.getX();
				lastY = event.getY();
				synchronized (ball) {
					newball = new Ball(getResources(), lastX, lastY, dw, dh);
					newball.setPosition(lastX, lastY);
					newball.setVelosity(0, 0);						
				}
				break;
				
			case MotionEvent.ACTION_MOVE:
				synchronized (newball) {
					newball.setPosition(event.getX(), event.getY());
				}
				
				break;
				
			case MotionEvent.ACTION_UP:
				if (newball != null)
				{
					synchronized (newball) {
						newball.setVelosity((float)((event.getX() - lastX) / 10.0)  , (float)((event.getY() - lastY) / 10.0) );
					}
				}
				break;
				
				
			default:
				break;
		}
		
		return super.onTouchEvent(event);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
