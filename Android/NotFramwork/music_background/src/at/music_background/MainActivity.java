package at.music_background;

import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	MediaPlayer mp3;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		Typeface myface = Typeface.createFromAsset(getAssets(), "font/VNI-THUPHAP1.TTF");
		TextView tv = (TextView)findViewById(R.id.textView1);
		tv.setTypeface(myface);
		
		mp3 = MediaPlayer.create(MainActivity.this, R.raw.feelgood);
		mp3.start();
	}

	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	
	// tắt toàn bộ âm thanh thì dùng hàm này
	// nếu ko có hàm này khi thoát chương trình thì nó vẫn còn resource
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mp3.release();
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
