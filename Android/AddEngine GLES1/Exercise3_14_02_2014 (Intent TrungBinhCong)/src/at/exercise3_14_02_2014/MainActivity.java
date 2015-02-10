
/*********************************************
 * Author		: Lý Hữu Vị
 * SkypeID		: huuvi168
 * Tên diễn đàn	: huuvi
 * Khóa học lập trình android miễn phí(Đợt 4) 14/01/2014
*********************************************/

// Bài 3: Tạo 1 ứng dụng hiện thị cho nhập 3 số a, b, c và 1 nút "gửi", 
// khi bấm nút gửi này sẽ chuyển qua 1 giao diện khác hiển thị kết quả 
// trung bình cộng của 3 số đó!

package at.exercise3_14_02_2014;


import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

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
	
	public void dialog(String Info)
	{		
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("Tính Trung Bình Cộng");
		builder.setMessage(Info);		
		builder.setPositiveButton("Đồng ý", new OnClickListener() {	// tên button
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});		
		builder.show();
	}
	
	public void tinhTrungBinhCongButtonClicked(View v)
	{
		
		EditText etA = (EditText)findViewById(R.id.et_a);
		EditText etB = (EditText)findViewById(R.id.et_b);
		EditText etC = (EditText)findViewById(R.id.et_c);		
					
		String a = etA.getText().toString();		
		String b = etB.getText().toString();		
		String c = etC.getText().toString();
		
		
		if (a.equals(""))
		{
			dialog("Bạn chưa nhập dữ liệu tại: a");			
			return;
		}

		if (b.equals(""))
		{
			dialog("Bạn chưa nhập dữ liệu tại: b");
			return;
		}
		
		if (c.equals(""))
		{
			dialog("Bạn chưa nhập dữ liệu tại: c");
			return;
		}			
		
		Intent in = new Intent(MainActivity.this, ResultActivity.class);
		
		Bundle myBundle = new Bundle();
		
		myBundle.putLong("a", Long.parseLong(a));
		myBundle.putLong("b", Long.parseLong(b));
		myBundle.putLong("c", Long.parseLong(c));
		
		in.putExtras(myBundle);
		startActivity(in);
	}

}
