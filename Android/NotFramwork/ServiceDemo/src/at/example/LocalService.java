package at.example;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class LocalService extends Service
{
	public class  LocalBinder extends Binder	
	{
		LocalService getService()
		{
			return LocalService.this;
		}		
	
	}
	
	/*Từ đó nạp chồng phương thức onBind bằng cách trả lại giá trị mBinder*/
	
	private final IBinder mBinder = new LocalBinder();
	@Override
	public IBinder onBind(Intent intent)
	{
		return mBinder;
	}
	
	
	
	/*Tạo một đối tượng MediaPlayer chơi nhạc đơn giản ( sử dụng để chơi file abc.mp3
			đặt trong folder res/raw ):*/
	
	MediaPlayer mMediaPlayer;
	public void startMP3Player()
	{
		mMediaPlayer = MediaPlayer.create(getApplicationContext(), R.raw.a);
		mMediaPlayer.start();
	}
	
	
	public void mp3Stop()
	{
		mMediaPlayer.stop();
		mMediaPlayer.release();
	}
	
	
}
