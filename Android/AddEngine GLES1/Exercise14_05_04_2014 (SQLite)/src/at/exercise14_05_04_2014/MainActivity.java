package at.exercise14_05_04_2014;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private Button btnCreateNewAcc, btnDisplayAcc;
	private TextView tvDisplay;
	private MyDatabase database = new MyDatabase(this);
	private int i= 0;
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		btnCreateNewAcc = (Button)findViewById(R.id.btnCreateAccount);
		btnDisplayAcc = (Button)findViewById(R.id.btnDisplayAccount);
		tvDisplay = (TextView)findViewById(R.id.tvDisplay);
		
		// create
		btnCreateNewAcc.setOnClickListener(new OnClickListener() {	
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				database.open();
				database.CreateData("No:" + i, "123");
				i++;
				database.close();
				
			}
		});
		
		// Display
		btnDisplayAcc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				database.open();
				String ds = database.getData();
				database.close();
				tvDisplay.setText(ds);
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
