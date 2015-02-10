package at.android;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Path.Direction;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

// trong android: để vẽ một hình bất kỳ cần
// - 1 đối tượng kiểu Bitmap giữ các pixel cần vẽ
// - 1 đối tượng chứa nét vẽ cần vẽ ra (rect, Path, Bitmap, ...)
// - 1 đối tượng kiểu Paint dùng để định nghĩa màu sắc, style,
// - 1 đối tượng Canvas để thực thi lệnh vẽ


public class CustomView extends View
{
	
	TimeCountThread timeCountThread = new TimeCountThread();

	public CustomView(Context context) {
		super(context);
		timeCountThread.start();
		
	}
	
	// dùng để khởi tạo các thuộc tính bên dưới trong file xml 
	//  android:id="@+id/graphicsView"
	/*	android:layout_width="fill_parent"
		android:layout_height="fill_parent"
	*/ 
	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		timeCountThread.start();
	}

	private void DrawRedBall(Canvas canvas)
	{
		Paint cPaint = new Paint();
		cPaint.setColor(Color.RED);
		
		Path cPath = new Path();
		cPath.addCircle(100, 100, 20, Direction.CW);
		
		canvas.drawPath(cPath, cPaint);
	}
	
	private int degree = 0;
	
	private void DrawBall(Canvas canvas)
	{		
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ball);
		canvas.drawBitmap(bitmap, x, y, new Paint());
		
		if (degree < 180)
			degree+=50;
		else
			degree=0;
		
		
		// xoay 
		/*Matrix matrix = new Matrix();		
		matrix.setRotate(degree,  x, y);
		canvas.drawBitmap(bitmap, matrix, null);*/		
		
		// đường kinh trái banh
		diameter = bitmap.getWidth();
		
		// Di chuyen banh
		BallMoving();
		
		// invalidate();
	}
	
	////////////////////////////// DI CHUYEN BANH
	public int x = 0;
	public int y = 0;
	private int Vx = 20, Vy = 20;
	private int diameter;
	
	private int iCount = 2000;
	
	protected void BallMoving()
	{
		x = x + Vx;
		y = y + Vy;
		
		if ( (x <= 0) || (x > getWidth() - diameter) )
			Vx = -Vx;
		
		if ( (y <=0) || (y > getHeight() - diameter) )
			Vy = -Vy;
		
		invalidate();
	}
	
	
	
	////////////////////////////// DI CHUYEN BANH
	
	@Override
	protected void onDraw(Canvas canvas) 
	{
		Paint paint = new Paint();
		
		if (iCount > 0)
		{
			
			paint.setColor(Color.RED);
			
			canvas.drawText("Time: " + iCount, 5, 20, paint);
			
			DrawBall(canvas);
		}
		else
		{
			paint.setColor(Color.YELLOW);
			paint.setTextSize(20);
			
			canvas.drawText("YOU'RE LUCKY BOY ^^!", 10, 180, paint);
		}
	}
	
	
	public class TimeCountThread extends Thread 
	{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			while (iCount > 0)
			{
				try
				{
					iCount --;
					sleep(1000);
					
				}catch(InterruptedException e)
				{
					e.printStackTrace();
				}
				
			}
			
		}
		

	}
}
