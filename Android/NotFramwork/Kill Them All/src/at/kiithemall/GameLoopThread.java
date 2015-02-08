package at.kiithemall;

import android.graphics.Canvas;

public class GameLoopThread extends Thread {
	
	// giới hạn 10 FPS
	static final long FPS = 10;
	
	private GameView view;
	private boolean running = false;
	
	public GameLoopThread(GameView v)
	{
		this.view = v;
	}
	
	public void setRunning(boolean run)
	{
		running = run;
	}
	
	@Override
	public void run()
	{
		long ticksPS = 1000 / FPS;
		long startTime;
		long sleepTime;
		
		
		while(running)
		{
			Canvas c= null;
			startTime = System.currentTimeMillis();
						
			try
			{
				c = view.getHolder().lockCanvas();
				
				// We use synchronize to avoid some other thread 
				// to make conflict when we are drawing. 
				synchronized (view.getHolder()) {
					view.draw(c);		// hàm draw này ảnh hưởng đến lop81 GameView			
				}
			}
			finally
			{
				if (c != null)
					view.getHolder().unlockCanvasAndPost(c);
			}
			
			sleepTime = ticksPS - (System.currentTimeMillis() - startTime);
			
			try{
				if (sleepTime > 0)
					sleep(sleepTime);
				else
					sleep(10);
			}
			catch (Exception e)
			{}
		}
	}
	
}
