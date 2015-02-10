package at.demotouchevent;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;

public class FunPanel extends SurfaceView{
	
	public FunPanel(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	class Point
	{
		int X;
		int Y;
		
		public Point()
		{
			X = Y = -1;
		}
	}
	
	private Point mCurPoint = new Point();
	private Bitmap mBitmap; 
	private ArrayList<Point> mPoints = new ArrayList<Point>();
	
	
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		if ( mPoints.size() % 5 == 0)
		{
			canvas.drawBitmap(mBitmap, mCurPoint.X, mCurPoint.Y, null);
		}
	}
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub

		mCurPoint.X = (int)event.getX() - mBitmap.getWidth() / 2;
		mCurPoint.Y = (int)event.getY() - mBitmap.getHeight() / 2;
		
		mPoints.add(mCurPoint);
		
		return super.onTouchEvent(event);
	}


}
