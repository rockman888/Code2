package at.example;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

// kế thừa lớp View để hiển thị ra ngoài màn hình
public class MyView extends View
{
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Path mPath;
	private Paint mBitmapPaint;
	
	public MyView(Context c)
	{
		// khởi tạo lớp Context
		super(c);
		
		Log.w("myApp", "vilh create!");
		
		// phủ kín màn hình 320, 480
		mBitmap = Bitmap.createBitmap(320,480,Bitmap.Config.ARGB_8888);
		
		mCanvas = new Canvas(mBitmap);
		
		// giữ lại vị trí ngón tay mà ta vẽ trên màn hình
		mPath = new Path();
		
		// bút vẽ
		mBitmapPaint = new Paint(Paint.DITHER_FLAG);
	}
	
	
	public void OnDraw(Canvas canvas)
	{
		canvas.drawColor(0xFFAAAAAA);
		
		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
		canvas.drawPath(mPath, mBitmapPaint);
	}	
	
	// sự kiện vẽ trên màn hình, người dùng chạm tay vào màn hình
	public boolean onTouchEvent(MotionEvent event)
	{
		float x = event.getX();
		float y = event.getY();
						
		Log.w("myApp", "x,y = " + x + ", " + y);
		
		switch(event.getAction())
		{
		
			case MotionEvent.ACTION_DOWN:
				touch_start(x, y);
				invalidate();// redraw(), vẽ lại màn hình! 
				break;
				
			case MotionEvent.ACTION_MOVE:
				//touch_move(x, y);
				invalidate();
				break;
			
			case MotionEvent.ACTION_UP:
				touch_up();
				invalidate();
				break;
		}
		
		return true;
	}


	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return super.toString();
	}


	@Override
	public void addFocusables(ArrayList<View> views, int direction,
			int focusableMode) {
		// TODO Auto-generated method stub
		super.addFocusables(views, direction, focusableMode);
	}


	@Override
	public void clearFocus() {
		// TODO Auto-generated method stub
		super.clearFocus();
	}


	private void touch_up() {
		// TODO Auto-generated method stub
		
	}


	private void touch_start(float x, float y) {
		// TODO Auto-generated method stub
		
		mCanvas.drawColor(0xFFAAAAAA);
		
		mCanvas.drawBitmap(mBitmap, x, y, mBitmapPaint);
		mCanvas.drawPath(mPath, mBitmapPaint);
	}
}
