package at.example;

import android.os.Bundle;
import android.os.IBinder;
import android.app.Activity;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class Main extends Activity {

	private ServiceConnection mConnection = new ServiceConnection() {
		
		LocalService mBoundService;
		
		@Override
		public void onServiceDisconnected(ComponentName arg0) {
			// TODO Auto-generated method stub
			mBoundService = null;
			Toast.makeText(Main.this, R.string.local_service_disconnected, Toast.LENGTH_SHORT).show();
			
		}
		
		@Override
		public void onServiceConnected(ComponentName arg0, IBinder arg1) {
			// TODO Auto-generated method stub
			
			mBoundService= ((LocalService.LocalBinder)arg1).getService();
			Toast.makeText(Main.this, R.string.local_service_connected, Toast.LENGTH_SHORT).show();
			
		}
	};
	
	private boolean mIsBound = false;
	private Button mPlayButton;
	
	
	private OnClickListener mBindListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			bindService(new Intent(Main.this, LocalService.class), mConnection, Context.BIND_AUTO_CREATE);
			
			mIsBound = true;
			mPlayButton.setEnabled(true);
			
			
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
