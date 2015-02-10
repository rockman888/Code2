package at.demosurfaceview2;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.SurfaceView;

public class CustomView extends SurfaceView{

	private static final boolean Enabled = true;

	public CustomView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see android.view.SurfaceView#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		
		
		
		 if (Enabled) {
	            Paint p = new Paint();
	            boolean isSelected = false;
				if (!isSelected) {
	                p.setColor(Color.parseColor("#3773D2"));
	                canvas.drawRect(5, 5, getWidth() - 5, getHeight() - 5, p);
	                Log.d("veeeeeee", "ve o vuong trang!");
	            } else {
	                p.setColor(Color.parseColor("#6096eb"));
	                canvas.drawRect(1, 1, getWidth() - 1, getHeight() - 1, p);
	                p.setColor(Color.WHITE);
	                p.setTextSize(getWidth()*.65f);
	                String text = "abc";
					canvas.drawText(text, getWidth() / 2 - p.measureText(text) / 2, getHeight() / 2 + p.measureText(text) / 1.5f, p);
					
					Log.d("veeeeeee", "ve o den!");
	            }
	        }
	}

}
