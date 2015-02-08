package at.exercise13b_1_03_2014;

// lưu trữ username, password cho lần sau khi người dùng chọn vào checkbox (Remmber password)

import android.os.Bundle;
import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;

public class MainActivity extends Activity
{
	final String xmlFile = "user";
	String data = "";
	
	private SharedPreferences pref;
	private Editor editor;
	

	
	public void WriteXML(String userName, String passWord)
	{

		
		// thêm dữ lieu với 2 key username, password 
		editor.putString("username", userName);
		editor.putString("password", passWord);
		
		// thực thi việc lưu trữ
		editor.commit();
		Log.v("Saved", "hoàn tất việc lưu trữ");		
	}
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// các đối tượng trên màn hình
		Button btnLogin = (Button)findViewById(R.id.btnLogin);
		Button btnExit = (Button)findViewById(R.id.btnExit);
		CheckBox cbRememberPassword = (CheckBox)findViewById(R.id.cb_remmemberPassword);
		final EditText etUserName = (EditText)findViewById(R.id.et_userName);
		final EditText etPassWord=  (EditText)findViewById(R.id.et_password);
							
		
		btnExit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
				
			}
		});
		
		pref = getApplicationContext().getSharedPreferences(xmlFile, 0);
		editor = pref.edit();
		String username = pref.getString("username", null);		
		if (username != "")
			etUserName.setText(username);
		
		String password = pref.getString("password", null);
		if(password != "")
			etPassWord.setText(pref.getString("password", null));
		
		
		
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		
		cbRememberPassword.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				
				if (isChecked == true)
				{
					Log.d("checkbox", "true");
					WriteXML(etUserName.getText().toString(), etPassWord.getText().toString());
				}
				else
				{
					Log.d("checkbox", "false");
					ClearXMLData();
						
				}
				
			}

			private void ClearXMLData() {
				// Xóa hết dữ liệu
				editor.clear();
				
				// thực thi việc xóa
				editor.commit();
				
				Log.v("Completed", "hoàn thành việc xóa dữ liệu!");
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
