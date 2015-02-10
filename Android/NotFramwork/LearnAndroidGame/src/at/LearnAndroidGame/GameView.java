package at.LearnAndroidGame;

import java.util.Random;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Rect;
import android.view.View;

public class GameView extends View{

	private int x = 0;
	private int y = 0;
	private Random rnd;
	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	public void init()
	{
		x = 0;
		y = 0;
		rnd = new Random();
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		// paint
		Paint p = new Paint();
		p.setARGB(150, 255, 0, 255);
		p.setColor(Color.YELLOW);
		p.setStyle(Paint.Style.FILL);
		Rect ourRect = new Rect();
		ourRect.set(0,0, canvas.getWidth() / 2, 180);		
		// gọi hàm này de vẽ
		canvas.drawRect(ourRect, p);
				
		// Text
		Paint t = new Paint();
		t.setTextSize(x);
		t.setColor(Color.CYAN);
		t.setTextAlign(Align.CENTER);		
		// goi hàm viết chữ
		canvas.drawText("Score: ", 70, 80, t);
		canvas.drawCircle(123, 234, 35, p);
		canvas.drawLine(37, 155, 24, 266, t);
				
		// Draw bitmap:
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.drawable.rockman);
		
		if (x < canvas.getWidth() && y < canvas.getHeight())
		{
			x = x + 10;
			y = y + 11;
		}
		else
		{
			x = 0;
			y = 0;
		}
		
		canvas.drawBitmap(bmp, x, y, null);		
		invalidate();				
	}
}
