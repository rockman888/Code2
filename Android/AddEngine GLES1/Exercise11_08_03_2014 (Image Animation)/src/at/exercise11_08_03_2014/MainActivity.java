package at.exercise11_08_03_2014;

// http://android.vn/threads/tao-anh-dong-animation-cho-imageview-trong-lap-trinh-ung-dung-android.24710/

/*********************************************
 * Author		: Lý Hữu Vị
 * SkypeID		: huuvi168
 * Tên diễn đàn	: huuvi
 * Khóa học lập trình android miễn phí(Đợt 4) 14/01/2014
*********************************************/


// Bài 11:
// hiển thị ảnh động!

import android.os.Bundle;
import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {

	private Button btnStart;
	private ImageView img;
	private AnimationDrawable frameAnimation;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnStart = (Button)findViewById(R.id.btnStart);
		img = (ImageView) findViewById(R.id.img_animation);
		
		
		
		if (img != null)
		{
			img.setVisibility(View.VISIBLE);
			frameAnimation = (AnimationDrawable)img.getDrawable();	
			frameAnimation.setCallback(img);
			frameAnimation.setVisible(true, true);
		}
		
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				if (!frameAnimation.isRunning())
				{
					frameAnimation.start();
					btnStart.setText("Stop");
					Log.v("Stop --", "Stop --");
				}
				else
				{
					frameAnimation.stop();
					btnStart.setText("Start");
					Log.v("Start", "Start");
				}
					
				
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
