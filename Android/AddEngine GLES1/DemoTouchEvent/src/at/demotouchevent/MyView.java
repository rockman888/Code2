package at.demotouchevent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.Log;
import android.view.*;


public class MyView extends View 
{	
	public int width;
	public int height;
	
	private Bitmap mBitmap;
	private Canvas mCanvas;
	private Path mPath;
	private Paint mBitmapPaint;
	
	Context context;
	private Paint circlePaint;
	private Path circlePath;
	
	
	private float mX, mY;
	private static final float TOUCH_TOLERANCE = 4;
	private boolean mDrawPoint;
	
	private void touch_start(float x, float y)
	{
		mPath.reset();
		mPath.moveTo(x, y);
		
		mCanvas.drawPath(mPath, mBitmapPaint ); /////////
		mX = x;
		mY = y;
		
		mDrawPoint = true;
	}
	
	private void touch_move(float x, float y)
	{
		
		float dx = Math.abs(x - mX);
		float dy = Math.abs(y - mY);
		if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE)
		{
			mPath.quadTo(mX, mY, (x+mX)/2, (y+mY)/2);
						
			mX = x;
			mY = y;
			
			mDrawPoint = false;
			
			//circlePath.reset();
			//circlePath.addCircle(mX,  mY, 20 , Path.Direction.CW);
		}
	}
	
	
	public void touch_up()
	{
		if (mDrawPoint = true)
		{
			mCanvas.drawPoint(mX, mY, mBitmapPaint);
		}
		/*else
		{		
			mPath.lineTo(mX, mY);
			circlePath.reset();
			// commit the path to our offscreen
			mCanvas.drawPath(mPath, mBitmapPaint);
			
			// Kill this so we don't double draw
			mPath.reset();
		}*/
	}
	
	public MyView(Context c) 
	{
		super(c);
		context = c;
		mPath = new Path();
		mBitmapPaint = new Paint (Paint.DITHER_FLAG);
		circlePaint = new Paint();
		circlePath = new Path();
		circlePaint.setAntiAlias(true);
		circlePaint.setColor(Color.BLUE);
		circlePaint.setStyle(Paint.Style.STROKE);
		circlePaint.setStrokeJoin(Paint.Join.MITER);
		circlePaint.setStrokeWidth(4f);
		
		
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);
		//canvas.drawPath(mPath, mBitmapPaint);
		// canvas.drawPath(circlePath, circlePaint);
		
		canvas.drawLine(downx, downy, upx, upy, mBitmapPaint);
			
	}

	
	private float downx, downy, upx, upy;
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		
		float x = event.getX();
		float y = event.getY();
		
		int i = event.getAction();
		
		switch(i)
		{
			case MotionEvent.ACTION_DOWN:
				touch_start(x, y);
				
			
				break;
		
			case MotionEvent.ACTION_MOVE:
				touch_move(x, y);				
				break;
						
			
			case MotionEvent.ACTION_UP:
				
			
				touch_up();
				break;
		}
		
		
		invalidate();
		return true;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh) {
		// TODO Auto-generated method stub
		super.onSizeChanged(w, h, oldw, oldh);
		
		mBitmap = Bitmap.createBitmap(w,h, Bitmap.Config.ARGB_8888);
		mCanvas = new Canvas(mBitmap);
	}
	
	

}
