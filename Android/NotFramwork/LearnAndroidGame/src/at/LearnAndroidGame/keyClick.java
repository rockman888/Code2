package at.LearnAndroidGame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.widget.TextView;

// Cứ có extends Activity thì phải add nó vào AndroidMainfest.xml!
public class keyClick extends Activity implements OnKeyListener{
	
	StringBuilder builder = new StringBuilder();
	TextView tv;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		tv = new TextView(this);
		tv.setOnKeyListener(this);
		tv.setText("Press those keys");
		tv.setFocusableInTouchMode(true);
		tv.requestFocus();
		setContentView(tv);
	}
	
	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		builder.setLength(0);
		switch(event.getAction())
		{
			case KeyEvent.ACTION_DOWN:
				builder.append("down, ");
				break;
				
			case KeyEvent.ACTION_UP:
				builder.append("up, ");
				break;		
		}		
		
		builder.append(event.getAction());
		builder.append(", ");
		builder.append((char) event.getUnicodeChar());
		
		String text = builder.toString();
		Log.d("KeyClick", text);	
		tv.setText(text);
		
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK)
			return false;			
		
		return true;
	}
}
