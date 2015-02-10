package at.demosufaceview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class GamePanel extends SurfaceView implements SurfaceHolder.Callback  
{
	
	public Ball ball;
	public GameThread gameThread = new GameThread();
	
	public GamePanel(Context context)
	{
		super(context);
		getHolder().addCallback(this);
	}
	
	public void drawGamePanel(Canvas canvas)
	{
		canvas.drawColor(Color.BLACK);
		synchronized(ball)
		{
			ball.drawBall(canvas);
		}
	}
	
	public void changeGamePanel()
	{
		synchronized(ball)		
		{
			ball.moveBall();
		}
	}

	public GamePanel(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		ball = new Ball(getResources(), 0, 0, this.getWidth(), this.getHeight());
		ball.setVelosity(5, 5);
		
		if (!gameThread.isAlive())
		{
			gameThread = new GameThread();
			gameThread.setRunning(true);
			gameThread.start();
		}
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		
		if (gameThread.isAlive())
			gameThread.setRunning(false);
	}
	
	
	public class GameThread extends Thread
	{
		private SurfaceHolder mHolder;
		private boolean mRun = false;
		
		public GameThread()
		{
			mHolder = GamePanel.this.getHolder();
		}
		
		public void setRunning (boolean run)
		{
			mRun = run;
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
		
			Canvas canvas = null;
			while (mRun)
			{
				canvas = mHolder.lockCanvas();
				
				if (canvas != null)
				{
					GamePanel.this.changeGamePanel();
					GamePanel.this.drawGamePanel(canvas);
					mHolder.unlockCanvasAndPost(canvas);
					
				}
			}
		}
		
	}
	
	
	
}
