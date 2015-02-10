package at.LearnAndroidGame;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

// Title -> call Game class (đăng ký trong AndroidManifest.xml) -> Game
// -> setContentView(new GameView(this));

public class Title extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_title);		
		init();
	}
	
	private void init()
	{
		Button start = (Button)findViewById(R.id.btnStart);
		start.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(v.getContext(), Game.class);
				startActivityForResult(intent, 0);
			}
		});
		
		
		Button input = (Button)findViewById(R.id.btnInputTest);
		input.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent  = new Intent(v.getContext(), keyClick.class);
				startActivityForResult(intent,  0);
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.title, menu);
		return true;
	}

}
