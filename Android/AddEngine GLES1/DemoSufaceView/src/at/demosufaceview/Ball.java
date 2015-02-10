package at.demosufaceview;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;

public class Ball {
	
	// chiều rộng, cao của game Panel
	private float gamePanelWidth = 0;
	private float gamePanelHeight = 0;

	// Attr xác định tọa độ (x,y) hiện tại của trái banh
	private float x;
	private float y;
	
	// đường kính trái banh
	private float diameter;
	
	
	// vận tốc phương ngang, phương dọc
	private float Vx;
	private float Vy;
	
	// đối tượng bitmap chứa hình ảnh trái banh
	private Bitmap bitmap;

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public void setVelosity(float Vx, float Vy) {
		this.Vx = Vx;
		this.Vy = Vy;
	}

	public void setPosition(float Vx, float Vy) {
		this.Vx = Vx;
		this.Vy = Vy;
	}
		
	public Ball(Resources res, float x, float y, float gamePanelWidth, float gamePanelHeight)
	{
		bitmap = BitmapFactory.decodeResource(res, R.drawable.ball);
		this.x = x;
		this.y = y;
		
		diameter = bitmap.getWidth();
		Vx = 0;
		Vy = 0;
		
		this.gamePanelHeight = gamePanelHeight;
		this.gamePanelWidth = gamePanelWidth;
	}
	
	// phương thức vẽ trái banh bằng đối tượng canvas
	public void drawBall (Canvas canvas)
	{
		canvas.drawBitmap(bitmap, x, y, null);
	}
	
	public void moveBall()
	{
		x += Vy;
		y += Vy;
		checkBallPosition();
	}
	
	// kiểm tra banh đổi hướng banh lại khi đụng biên 
	public void checkBallPosition()
	{
		if (x <= 0 || (x >= gamePanelWidth - diameter) )
			Vx = - Vx;
		
		if (y <= 0 || (y >= gamePanelHeight - diameter) )
			Vy = - Vy;
		
	}
	
}

