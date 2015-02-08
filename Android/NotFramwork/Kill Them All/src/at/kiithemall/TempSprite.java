/*
 * Load blood image
 */

package at.kiithemall;

import java.util.List;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class TempSprite {
	private float x;
	private float y;
	private Bitmap bmp;
	private int life = 15;
	private List<TempSprite> temps;
	
	public TempSprite(List<TempSprite> temps, GameView gv, float x, float y, Bitmap bmp)
	{
		float f1 = Math.max(x - bmp.getWidth() / 2, 0);
		float f2 = gv.getWidth() - bmp.getWidth();		
		this.x = Math.min(f1, f2);
		
		f1 = Math.max(y - bmp.getHeight() / 2, 0);
		f2 = gv.getHeight() - bmp.getHeight();		
		this.y = Math.min(f1, f2);
		
		this.bmp = bmp;
		this.temps = temps;
	}
	
	public void draw(Canvas c)
	{
		update();
		c.drawBitmap(bmp, x, y, null);
	}
	
	private void update()
	{
		if (--life < 1)
			temps.remove(this);
	}
}
