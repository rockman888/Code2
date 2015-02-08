
/*********************************************
 * Author		: Lý Hữu Vị
 * SkypeID		: huuvi168
 * Tên diễn đàn	: huuvi
 * Khóa học lập trình android miễn phí(Đợt 4) 14/01/2014
*********************************************/


// Bài 7: Tạo 1 ứng dụng Customize Dialog,

package at.exercise7_18_02_2014;

import android.os.Bundle;
import android.app.Activity;


import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity 
{	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		// Toast.makeText(MainActivity.this, "Ban vua chon nut Selected", Toast.LENGTH_SHORT).show();
		
		/*if (keyCode == KeyEvent.ACTION_DOWN)
		{		
			Toast.makeText(MainActivity.this, "Ban vua chon nut selected", Toast.LENGTH_SHORT).show();
		}*/
		
		return super.onKeyDown(keyCode, event);
	}


	public void showCustomDialogClicked(View v)
	{
		/*
		
		final Dialog dialog = new Dialog(MainActivity.this);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.custom_dialog);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.argb(50, 255, 255, 255))); // code tạo transparency
		dialog.show();
		
		*/
		
		CustomDialogClass dialog = new CustomDialogClass(MainActivity.this);
		dialog.show();
	}
	

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
