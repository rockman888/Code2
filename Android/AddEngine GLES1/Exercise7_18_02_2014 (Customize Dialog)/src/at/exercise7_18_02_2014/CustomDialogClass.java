package at.exercise7_18_02_2014;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class CustomDialogClass extends Dialog implements android.view.View.OnClickListener
{

	  public Activity m_Activity;
	  public Dialog d;
	  public Button yes, no;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		 
	    requestWindowFeature(Window.FEATURE_NO_TITLE);
	    setContentView(R.layout.custom_dialog);
	    yes = (Button) findViewById(R.id.btn_yes);
	    no = (Button) findViewById(R.id.btn_no);
	    yes.setOnClickListener(this);
	    no.setOnClickListener(this);
	}

	public CustomDialogClass(Activity context) {
		super(context);
				 
		// TODO Auto-generated constructor stub
		this.m_Activity = context;
	}

	@Override
	public void onClick(View v) 
	{
		// TODO Auto-generated method stub
		switch (v.getId())
		{
		    case R.id.btn_yes:
		      m_Activity.finish();
		      break;
		      
		    case R.id.btn_no:
		      dismiss();
		      break;
		      
		    default:
		      break;
	    }
		
	    dismiss();
	 
	}

}


 
