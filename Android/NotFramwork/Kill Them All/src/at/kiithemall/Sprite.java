package at.kiithemall;

import java.util.Random;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

public class Sprite {
	
	private static final int BMP_ROWS = 4;
	private static final int BMP_COLUMNS = 3;
	private static final int MAX_SPEED = 30;
	
	private int x = 0;
	private int y = 0;
	
	private int currentFrame = 0;
	private int width;
	private int height;
		
	private int xSpeed, ySpeed;
	private GameView gameView;
	private Bitmap bmp;
	
	public Sprite(GameView gameView, Bitmap bmp)
	{
		this.gameView = gameView;
		this.bmp = bmp;
		
		
		this.width = bmp.getWidth() / BMP_COLUMNS;
		this.height = bmp.getHeight() / BMP_ROWS;
		
		
		Random rnd = new Random(System.currentTimeMillis());
		
		x = rnd.nextInt(gameView.getWidth() - width);
		y = rnd.nextInt(gameView.getHeight() - height);
		xSpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
		ySpeed = rnd.nextInt(MAX_SPEED * 2) - MAX_SPEED;
	}
	
	private void update()
	{
		if (x >= gameView.getWidth() - width - xSpeed || x + xSpeed <= 0 )
			xSpeed = -xSpeed;
		
		x = x + xSpeed;
		
		if (y >= gameView.getHeight() - height - ySpeed || y + ySpeed  <= 0)
			ySpeed = -ySpeed;
		
		y = y + ySpeed;
		currentFrame = ++currentFrame % BMP_COLUMNS;
	}
	
	// direction = 0 up, 1 left, 2 down, 3 right,
    // animation = 3 back, 1 left, 0 front, 2 right
    int[] DIRECTION_TO_ANIMATION_MAP = { 3, 1, 0, 2 };
    
    //       x  y  atan2(x, y) atan2(x, y) / (PI/2)  ((atan2(x,y)/(PI/2)+2) %4) 	bmp row (from 0) 
    // up    0 -1	PI or -PI        2 or -2              4 OR 0                 		3
    // right 1  0     PI/2              1                   3                           2
    // down  0  1       0               0                   2                           0
    // left -1  0    -PI/2             -1                   1                           1
    
	private int getAnimationRow()
	{		
		double dirDouble = (Math.atan2(xSpeed, ySpeed) / (Math.PI / 2) + 2 );		
		int direction = (int)Math.round(dirDouble) % BMP_ROWS;
		return DIRECTION_TO_ANIMATION_MAP[direction];
	} 
	
	public boolean isCollition(float x2, float y2)
	{
		
		// return true khi nó nằm trong khoảng này!
		return (x < x2 && x2 < x + width &&
				y < y2 && y2 < y + height);	
	}
	
	public void draw(Canvas canvas)
	{
		update();
		
		int srcX = currentFrame * width;
		// int srcY = 0 * height;	// hình dòng 0;
		
		int srcY = getAnimationRow() * height;	// tổng cộng có 4 dòng;
		Rect src = new Rect (srcX, srcY, srcX + width, srcY + height);
		Rect dst = new Rect (x, y, x + width, y + height);
		
		// vẽ canvas tại ô có kích thước srcX, srcY
		canvas.drawBitmap(bmp,  src,  dst, null);
		
		// canvas.drawBitmap(bmp, x, 10, null);
	}	
}
