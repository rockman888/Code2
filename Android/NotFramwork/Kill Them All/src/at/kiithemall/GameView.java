package at.kiithemall;

import java.security.acl.LastOwnerException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;

// kế thừa lớp Surface View
public class GameView extends SurfaceView{
		
	//private Bitmap bmp;
	private SurfaceHolder holder;
	private GameLoopThread gameLoopThread;
	private long lastClick = 0;
	private Bitmap bmpBlood;

	//private Sprite sprite;
	private List<Sprite> sprites = new ArrayList<Sprite>();
	private List<TempSprite> tempSprites = new ArrayList<TempSprite>();
	
	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		gameLoopThread = new GameLoopThread(this);
		
		holder = getHolder();
		//bmp = BitmapFactory.decodeResource(getResources(), R.drawable.bad1);
		
		bmpBlood = BitmapFactory.decodeResource(getResources(), R.drawable.blood1);
		
		
		holder.addCallback(new SurfaceHolder.Callback() 
		{
			
			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				
				boolean retry = true;
				gameLoopThread.setRunning(false);
				
				while (retry)
				{
					try{
						gameLoopThread.join();
						retry = false;
					}
					catch(InterruptedException e){
					}				
				}				
			}
			
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				// TODO Auto-generated method stub
				createSprites();
				gameLoopThread.setRunning(true);
				gameLoopThread.start();				
				
				/*Canvas c = holder.lockCanvas(null);
				draw(c);	// gọi hàm draw trên cùng, hàm này là một trong các hàm trong SurfaceView
				holder.unlockCanvasAndPost(c);*/

			}
			
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format, int width,
					int height)	{				
				// TODO Auto-generated method stub				
			}
		});		
	
		
		// thêm sprite
		//sprite = new Sprite(this,  bmp);
	}	

	private Sprite createSprite(int resource)
	{
		Bitmap bmp = BitmapFactory.decodeResource(getResources(), resource);
		return new Sprite(this, bmp);				
	}	
	
	public void createSprites()
	{
		sprites.add(createSprite(R.drawable.bad1));
		sprites.add(createSprite(R.drawable.bad2));
		sprites.add(createSprite(R.drawable.bad3));
		sprites.add(createSprite(R.drawable.bad4));
		sprites.add(createSprite(R.drawable.bad5));
		sprites.add(createSprite(R.drawable.bad6));
		sprites.add(createSprite(R.drawable.good1));
		sprites.add(createSprite(R.drawable.good2));
		sprites.add(createSprite(R.drawable.good3));
		sprites.add(createSprite(R.drawable.good4));
		sprites.add(createSprite(R.drawable.good5));
		sprites.add(createSprite(R.drawable.good6));
		
	}
	
	/* (non-Javadoc)
	 * @see android.view.SurfaceView#draw(android.graphics.Canvas)
	 */
	@Override
	public void draw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.draw(canvas);
		canvas.drawColor(Color.BLACK);

		// load máu
		for (int i=tempSprites.size() - 1; i >= 0; i--)
			tempSprites.get(i).draw(canvas);
		
		
		// load 1 cái		
		//sprite.draw(canvas);	// sprite.draw; view.draw (GameLoopThread) view.draw(GameView)
		
		// load 1 list
		for (Sprite spr : sprites)
			spr.draw(canvas);
	}

	/* (non-Javadoc)
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
				
		if (System.currentTimeMillis() - lastClick > 500)
		{
			lastClick = System.currentTimeMillis();
			float x = event.getX();
			float y = event.getY();
			synchronized (getHolder()) 
			{
				for (int i=sprites.size() - 1; i>=0; i--)
				{
					Sprite s = sprites.get(i);
					if (s.isCollition(x, y))
					{
						sprites.remove(s);
						tempSprites.add(new TempSprite(tempSprites, this, x, y, bmpBlood));
						break;
					}							
				}				
			}
		}
		
		return super.onTouchEvent(event);
	}					
		
	// public void draw(Canvas canvas) {		
		// chưa có xSpeed
		/*if (x < getWidth() - bmp.getWidth())
			x++;*/
		
		
		// Có xSpeed
		// đụng vào gốc là đi ngược lại
/*		if (x == getWidth() - bmp.getWidth())
		{
			xSpeed = -1;
		}
		
		if (x == 0)
		{
			xSpeed = 1;
		}
		
		
		x = x + xSpeed;
		
		canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(bmp,  x,  10, null);*/
		
		/*canvas.drawColor(Color.BLACK);
		canvas.drawBitmap(bmp, 10, 10, null);*/
	// }
}

/*
 Kế thừa lớp View
  
 public class GameView extends View{
 

	private Bitmap bmp;	
	
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		
		canvas.drawColor(Color.WHITE);	// tô nền
		canvas.drawBitmap(bmp, 10, 10, null);	// vẽ hình
	}	
	
	public GameView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		
		// local icon trong resource có sẵn
		bmp = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
	}
	
	

}
*/