package com.example.democlient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends Activity {

	
	// variables
	private Socket client;
	private PrintWriter printwriter;
	private EditText etMsg, etIP, etPort;
	private Button button;
	private String message;
	int port = 0;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		etIP = (EditText)findViewById(R.id.et_Address);
		etPort = (EditText)findViewById(R.id.et_Port);
		etMsg = (EditText)findViewById(R.id.et_Message);
		button = (Button)findViewById(R.id.button_Send);
		
		ButtonEvent();
	}
	
	
	public void ButtonEvent()
	{
		button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				message = etMsg.getText().toString();
				etMsg.setText("");
				
				port = Integer.parseInt(etPort.getText().toString());
				
				// run Thread
				new Thread(new Runnable() 
				{					
					@Override
					public void run() 
					{
						// TODO Auto-generated method stub
						
						try
						{
							client = new Socket(etIP.getText().toString(), port);
							
							printwriter = new PrintWriter(client.getOutputStream());
							printwriter.write(message);
							printwriter.flush();
							printwriter.close();
							
							client.close();
							
						}
						catch(UnknownHostException ex)
						{
							ex.printStackTrace();
						}
						catch(IOException e)
						{
							e.printStackTrace();
						}
						
					}
				}).start();
						
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
