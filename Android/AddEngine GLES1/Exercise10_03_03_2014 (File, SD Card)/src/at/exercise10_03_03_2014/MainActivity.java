package at.exercise10_03_03_2014;

/*********************************************
 * Author		: Lý Hữu Vị
 * SkypeID		: huuvi168
 * Tên diễn đàn	: huuvi
 * Khóa học lập trình android miễn phí(Đợt 4) 14/01/2014
*********************************************/

// Bài 10: Đọc, ghi dữ liệu vào file
// EditText -> nhập một đoạn text -> lưu lại trong SD Card
// TextView -> hiển thị dữ liệu trong SD Card với đường dẫn cho sẵn

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;

import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	
	public EditText etMessage;
	public TextView tvMessage;
	Button btnSave;
	Button btnView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etMessage = (EditText)findViewById(R.id.editTextMessage);
		tvMessage = (TextView)findViewById(R.id.textViewMessage);
		btnSave = (Button)findViewById(R.id.btnSave);		
		saveButtonClicked();
				
		btnView = (Button)findViewById(R.id.btnView);
		viewButtonClicked();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	public void saveButtonClicked()
	{	
		btnSave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				 try {
	                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/file.txt";
	                    Log.v("", "path = " + path);
	                    File myFile = new File(path);
	                    myFile.createNewFile();
	                    FileOutputStream fOut = new FileOutputStream(myFile);
	                    OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
	                    myOutWriter.append(etMessage.getText().toString());
	                    myOutWriter.close();
	                    fOut.close();
	                    Toast.makeText(getBaseContext(), "Hoàn thành lấy dữ liệu từ SDCard trong 'file.txt'", Toast.LENGTH_SHORT).show();
	                } catch (Exception e) {
	                    Toast.makeText(getBaseContext(), "Thất bại", Toast.LENGTH_SHORT).show();	                    
	                }
			}
		});
	}
	
	public void viewButtonClicked()
	{
		btnView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try
				{
					String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/file.txt";
					
					File f = new File(path);
					FileInputStream in = new FileInputStream(f);
								
					
					// kích thước tập tin
					int size = in.available();
					byte [] buffer = new byte[size];
					in.read(buffer);
					in.close();
					String str = new String (buffer);
					tvMessage.setText(str);				
					
				}
				catch(Exception ex)
				{
					
				}
				
			}
		}); 	
	}

}
