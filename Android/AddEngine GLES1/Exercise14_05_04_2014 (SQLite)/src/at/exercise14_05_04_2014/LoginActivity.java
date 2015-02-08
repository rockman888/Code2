package at.exercise14_05_04_2014;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity 
{
	private Button btnLogin, btnDisplay, btnUpdate;
	private TextView tvList;
	private EditText etLogin, etPassword;
	private MyDatabase database = new MyDatabase(this);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		btnLogin = (Button)findViewById(R.id.btnLogin);
		btnDisplay = (Button)findViewById(R.id.btnList);
		btnUpdate = (Button)findViewById(R.id.btnUpdate);
		
		tvList = (TextView)findViewById(R.id.tvList);
		etLogin = (EditText)findViewById(R.id.etLogin);
		etPassword = (EditText)findViewById(R.id.etPassword);
		
		checkLoginButtonClick();		
		displayButtonClick();
		
		// button 
		btnUpdate.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				database.open();
				Boolean kq = database.checkLogin(etLogin.getText().toString(), etPassword.getText().toString());
				database.close();
				
				if (kq)
				{
					AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
					builder.setTitle("Update new name");
					builder.setMessage("Please input new name:");
					
					final EditText input = new EditText(LoginActivity.this);
					builder.setView(input);
					builder.setCancelable(false);
					builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() 
					{						
						public void onClick(DialogInterface dialog, int which) 
						{
							// TODO Auto-generated method stub
							database.open();
							Boolean kq = database.setNameHienThi(etLogin.getText().toString(), etPassword.getText().toString(), input.getText().toString());
							
							database.close();
							
							if (kq)
								Toast.makeText(LoginActivity.this, "Succeed!", Toast.LENGTH_SHORT).show();
							else
								Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
							
						}
					
					});
					
					builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							
						}

					});
					
					AlertDialog dialog = builder.create();
					dialog.show();
				}
			}
		});		
	}

	private void displayButtonClick() {
		// button display
		btnDisplay.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				database.open();
				String ds = database.getData();
				database.close();
				tvList.setText(ds);
				
			}
		});
	}

	private void checkLoginButtonClick() {
		// button login
		btnLogin.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) 
			{
				// TODO Auto-generated method stub
				database.open();
				Boolean kq = database.checkLogin(etLogin.getText().toString(), etPassword.getText().toString());
				database.close();
				
				if (kq)
					Toast.makeText(LoginActivity.this, "Succeed", Toast.LENGTH_SHORT).show();	
				else
					Toast.makeText(LoginActivity.this, "Fail", Toast.LENGTH_SHORT).show();
			
				
			}
		});
	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		//return super.onContextItemSelected(item);
		
		switch (item.getItemId())
		{
		case R.id.i1:
			Intent intent = new Intent(LoginActivity.this, MainActivity.class);
			startActivity(intent);
			finish();
			break;
			
		case R.id.i2:
			finish();
			break;
			
		}
		
		return false;
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		super.onCreateOptionsMenu(menu);
		
		MenuInflater blowUp = getMenuInflater();
		blowUp.inflate(R.menu.main, menu);
		menu.findItem(R.id.i1).setTitle("Home");
		menu.findItem(R.id.i2).setTitle("Exit");
				
		return true;
	}
	

}
