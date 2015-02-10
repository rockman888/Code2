package at.exericise6_22_02_2014;

/*********************************************
 * Author		: Lý Hữu Vị
 * SkypeID		: huuvi168
 * Tên diễn đàn	: huuvi
 * Khóa học lập trình android miễn phí(Đợt 4) 14/01/2014
*********************************************/

// Tạo 1 ứng dụng có sử dụng menu gồm các item sau "google, facebook, android.vn, youtube, gmail" 
// và khi click vào item đó sẽ load ra trang chủ tương ứng với tiêu đề.

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;

public class MainActivity extends Activity
{

	private WebView browser;
	private String strURL;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		browser = (WebView)findViewById(R.id.webkit);
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		
		getMenuInflater().inflate(R.menu.my_menu, menu);
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId())
		{
			case R.id.androidvn:
				strURL = "http://android.vn";		
				browser.loadUrl(strURL);
				break;
			
			case R.id.facebook:
				strURL = "http://facebook.com/";
				browser.loadUrl(strURL);
				break;				
				
			case R.id.gmail:
				strURL = "http://gmail.com/";
				browser.loadUrl(strURL);
				break;
				
			case R.id.google:
				strURL = "https://www.google.com/";
				browser.loadUrl(strURL);
				break;
				
			case R.id.youtube:
				strURL = "https://www.youtube.com/";
				browser.loadUrl(strURL);
				break;
				
			case R.id.about:
				AlertDialog.Builder builder = new Builder(this);
				builder.setTitle("Giới Thiệu");
				builder.setMessage("Nhà Phát Triển: \nHữu Vị\nLiên Hệ: \n" + "huuvi168@gmail.com");
				builder.setPositiveButton("Close", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						
					}
				});
				
				builder.setIcon(android.R.drawable.btn_star_big_on);
				builder.show();
				break;
		}
		
		return super.onMenuItemSelected(featureId, item);
	}

}
