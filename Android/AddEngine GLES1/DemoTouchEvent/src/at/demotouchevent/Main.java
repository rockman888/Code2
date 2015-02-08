package at.demotouchevent;

import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ImageView;

public class Main extends Activity implements OnTouchListener 
{
	
	private ImageView imageView;
	private Bitmap bitmap;
	private Canvas canvas;
	private Paint paint;
	
	private float prevX = 0, prevY = 0;
	private float curX = 0, curY = 0;
	
	private final int TIME_ENTRY_REQUEST_CODE = 1;
	private int iStyle = 1;
	private String mColor = "Blue";
	
	private int iPointsSize;
	
	enum STYLE
	{
		SMALL(2),
		MEDIUM(6),
		BIG(10),
		LARGE(15);
		
		private int value;    

		  private STYLE(int value) {
		    this.value = value;
		  }

		  public int getValue() {
		    return value;
		  }
	}
	
	
	
	
	// ***************************************************
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		// TODO Auto-generated method stub
		
		switch(item.getItemId())
		{
			case R.id.clear:
					
				  if (bitmap != null && ! bitmap.isRecycled()) 
	                {
	                    bitmap.recycle();
	                    bitmap = null;
	                    imageView.setImageBitmap(bitmap);
	                    
	                    CreateEnvironment();
	                    // System.gc(); 
	                }				
			
				break;
				
			case R.id.settings:				
				
				Intent in= new Intent(this, Pen.class);
				in.putExtra("mColor", mColor);
				in.putExtra("mStyle", String.valueOf(iStyle));

				// start form ko cần có giá trị trả về  
				// startActivity(in);	 
				
				// start form có giá trị trả về
				startActivityForResult(in, TIME_ENTRY_REQUEST_CODE);		
				break;
				
			case R.id.about:
				AlertDialog.Builder builder = new Builder(this);
				builder.setTitle("Giới Thiệu");
				builder.setMessage("Nhà Phát Triển: \nViLH\nLiên Hệ: \n" + "huuvi168@gmail.com");
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

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);		
		
		imageView = (ImageView) this.findViewById(R.id.imageView1);
		
		paint = new Paint();		
		
		if (mColor.equals("Red"))
			paint.setColor(Color.RED);
		else
			paint.setColor(Color.BLUE);
		    
		CreateEnvironment();	  
	}

	private void CreateEnvironment() {
				
	    Display currentDisplay = getWindowManager().getDefaultDisplay();
	    float dw = currentDisplay.getWidth();
	    float dh = currentDisplay.getHeight();
	 
	    bitmap = Bitmap.createBitmap((int) dw, (int) dh,  Bitmap.Config.ARGB_8888);
	    canvas = new Canvas(bitmap);
	   
	    imageView.setImageBitmap(bitmap);
	    imageView.setOnTouchListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		// getMenuInflater().inflate(R.menu.main, menu);
		
		getMenuInflater().inflate(R.menu.my_menu, menu);
		return true;
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
			
		super.onActivityResult(requestCode, resultCode, data);
				
		// This check makes sure the requestCode is the Code u passed in
		if (requestCode == TIME_ENTRY_REQUEST_CODE)
		{			
			// this checks that the resultCode is RESULT_OK, 
			// since u didn't set the result code in the onCancel, this will return instead of trying to add a new item
			if (resultCode == RESULT_OK)
			{				
				// get the values from the intent				
				
				mColor = data.getStringExtra("color_value");
				String mStyle = data.getStringExtra("style_value");
				iStyle = Integer.valueOf(mStyle);
				
				
				if (mColor.equals("Red"))
					 paint.setColor(Color.RED);
				else
					 paint.setColor(Color.BLUE);
			}
		}		
		
	}

	@Override
	public boolean onTrackballEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		return super.onTrackballEvent(event);
	}


	@Override
	public boolean onTouch(View v, MotionEvent event) 
	{	
		
		STYLE s;
		switch(iStyle)
		{
			case 1:  	
				s = STYLE.SMALL;
				iPointsSize = s.getValue();
				break;
				
			case 2:  	
				s = STYLE.MEDIUM;
				iPointsSize = s.getValue();
				break;
				
			case 3:  	
				s = STYLE.BIG;
				iPointsSize = s.getValue();
				break;
				
			default:
				s = STYLE.LARGE;
				iPointsSize = s.getValue();
				break;		
		}
		
	   		
		// TODO Auto-generated method stub
		int action = event.getAction();
				
	    switch (action) 
	    {
		    case MotionEvent.ACTION_DOWN:
		      prevX = event.getX();
		      prevY = event.getY();		      		      
		      break;
		      
		     
		    case MotionEvent.ACTION_MOVE:
		      curX = event.getX();
			  curY = event.getY();
			  			  
			  paint.setStrokeWidth(iPointsSize);
			  canvas.drawLine(prevX, prevY, curX, curY, paint);
			  prevX = curX;
			  prevY = curY;
			  break;
		      		      
		    default:
		      break;
	    }
	    
	    imageView.invalidate();
	    return true;
	}	
}


