package com.example.musicgame;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;



import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore.Audio;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

// lỗi button click không có event thì ta đặt tên lại! (click button không tác dụng)
// mất id của textView Point (tính điểm) -> khắc phục delete control cũ, build lại control mới!
// dùng currentMusic để lưu lại bài hát hien tai -> click vào button cho đúng!
// error (-19,0) --> chua release, phải release tài nguyên
// soundpool -> kiến nghị dùng soundpool

public class MainActivity extends Activity{

	Button b1, b2, b3, b4;
	TextView t1,t2,t3,t4;
	Button btnStart, btnStop;
	TextView tvResult, tvPoints;
	
	int nCurrentMusic = 0;
	
	private MediaPlayer mPlayer;	
	private int currentSong = 0; 
	List<MusicInfo> lstMusicInfo;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
						
		b1 = (Button)findViewById(R.id.button1);
		b2 = (Button)findViewById(R.id.button2);
		b3 = (Button)findViewById(R.id.button3);
		b4 = (Button)findViewById(R.id.button4);
		
		t1 = (TextView)findViewById(R.id.textView1);
		t2 = (TextView)findViewById(R.id.textView2);
		t3 = (TextView)findViewById(R.id.textView3);
		t4 = (TextView)findViewById(R.id.textView4);
		
		tvResult = (TextView)findViewById(R.id.textViewResult);
		tvPoints = (TextView)findViewById(R.id.textViewPoints);
				
		btnStart = (Button)findViewById(R.id.buttonPlay);
		btnStop = (Button)findViewById(R.id.buttonStop);
				
		int x = 0;
		tvPoints.setText(String.valueOf(x));		
		createListeners();
		
		
	}
	
	private void playRandomMusic()
	{		
		Random rLucky = new Random();
		int nLucky = rLucky.nextInt(4) + 1; // random từ  1->4
		
		// 1,2,3,4,5,6,7,8
		nCurrentMusic = lstMusicInfo.get(nLucky-1).getnId();	// phải giảm 1
		playMusic(nCurrentMusic);
		
	}	
	
	// hát bài hát theo số thứ tự đã định sẵn, mỗi bài hát sẽ có những đoạn random khác nhau
	// không nhất thiết hát từ ban đầu
	private void playMusic(int nNum)
	{
		switch(nNum)
		{
			case 1: // boom boom boom
				mPlayer = MediaPlayer.create(MainActivity.this, R.raw.song1);				
				currentSong = R.raw.song1;
				
				
				break;
				
			case 2: // sha la la
				mPlayer = MediaPlayer.create(MainActivity.this, R.raw.song2);		
				currentSong = R.raw.song2;
				break;
				
			case 3: // stand by me
				mPlayer = MediaPlayer.create(MainActivity.this, R.raw.song3);		
				currentSong = R.raw.song3;
				break;
				
			case 4: // head the world
				mPlayer = MediaPlayer.create(MainActivity.this, R.raw.song4);		
				currentSong = R.raw.song4;
				break;			
			
			case 5: //我是不是该安静的走开
				mPlayer = MediaPlayer.create(MainActivity.this, R.raw.song5);		
				currentSong = R.raw.song5;
				break;
				
			case 6: //chinito
				mPlayer = MediaPlayer.create(MainActivity.this, R.raw.song6);		
				currentSong = R.raw.song6;
				break;
		
			case 7: //one love
				mPlayer = MediaPlayer.create(MainActivity.this, R.raw.song7);		
				currentSong = R.raw.song7;
				break;
				
			case 8: //happy Arabi
				mPlayer = MediaPlayer.create(MainActivity.this, R.raw.song8);				
				currentSong = R.raw.song8;
				break;
		}
		
		 
		int msec = mPlayer.getDuration();		
		Random r = new Random();
		int iCur = r.nextInt(msec-100)  + 1;	// random từ 1 -> msec-100 // không cho random hết
		mPlayer.seekTo(iCur);		
		mPlayer.start();		
		
		t1.setText( String.valueOf(msec) );
		t2.setText( String.valueOf(iCur) );
		
		
		// khi kết thúc bài hát sẽ vào hàm này
		/*mPlayer.setOnCompletionListener(new OnCompletionListener() {
			
			@Override
			public void onCompletion(MediaPlayer mp) {
				// TODO Auto-generated method stub
				mp.release();
				Log.d("RELEASE", "stop music") ;
				
			}
		});*/
				
		// error (-19,0) --> chua release, phải release tài nguyên
		// soundpool -> kiến nghị dùng soundpool		
	}	
	
	/* (non-Javadoc)
	 * @see android.app.Activity#onPause()
	 */
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mPlayer.release();
		finish();
		
		//mp3.release();
		//finish();
	}

	private String getCurrentMusicName()
	{
		String strMusicName = "";
		for (int i=0; i<lstMusicInfo.size(); i++)
			if (lstMusicInfo.get(i).getnId() == nCurrentMusic)
			{
				strMusicName =lstMusicInfo.get(i).getStrName();
				break;
			}
				
				
		return strMusicName; 
	}	
	
	private void setPointsForUser(Button button) {
		String szTemp = tvPoints.getText().toString();
		int nPoints = Integer.parseInt(szTemp);
		
		// nếu button giống y chang như tên của music
		if ( button.getText() == getCurrentMusicName() )
		{
			tvResult.setText("Đúng rồi, bạn hay quá");
			stopMusic();
			nPoints = nPoints + 5;
			
			btnStart.setEnabled(false);
			b1.setEnabled(true);
			b2.setEnabled(true);
			b3.setEnabled(true);
			b4.setEnabled(true);			
			
			ChooseRandomMusic(4, 8); // chọn ra 4 bài trong 8 bài hát!
		}
		else
		{
			tvResult.setText("Sai rồi!, >_< ");
			nPoints = nPoints - 10;
		}
		
		// để vậy mới đúng!
		// String.valueOf
		tvPoints.setText(String.valueOf(nPoints));	// vay moi dung
		
		// sai
		// tvPoints.setText(x); với x = number
	}
	
	private String getMusicName(int k)
	{
		String strName = "";
		switch(k)
		{
			case 1:
				strName = "boom boom boom";
				break;
				
			case 2:
				strName = "stand by me";
				break;
				
			case 3: 
				strName = "shalala";
				break;
				
			case 4:
				strName = "heal the world";
				break;
				
			case 5:
				strName = "我是不是该安静的走开";
				break;
				
			case 6:
				strName = "chinito";
				break;
				
			case 7:
				strName = "one love";
				break;
				
			case 8:
				strName = "happy Arabi";
				break;						
		}
		
		return strName;
		
		/*
		case 1: // boom boom boom			
		case 2: // stand by me			
		case 3: // shalala			
		case 4: // head the world		
		case 5: // 我是不是该安静的走开			
		case 6: // chinito		
		case 7: // one love			
		case 8: // happy Arabi
		*/	
	}
	

	private void stopMusic() {
		if (mPlayer == null)
			return;
		
		if (mPlayer.isPlaying())
		{
			mPlayer.stop();
			mPlayer.release();
			btnStart.setEnabled(true);
			b1.setEnabled(false);
			b2.setEnabled(false);
			b3.setEnabled(false);			
			b4.setEnabled(false);
		}
	}
	
	// return true; trùng nhau!
	private boolean isSame(List<MusicInfo>lst, int nValue)
	{
		for (int i = 0; i < lst.size(); i++)
			if (lst.get(i).getnId() == nValue)	// trùng nhau
				return true;
		
		return false;
	}
		
	private void ChooseRandomMusic(int nNum, int total)	// số lượng bài hát random / tổng số
	{
		// random nNum trong total;
		Random r  = new Random();
		int nRate = r.nextInt(total) + 1;		// random từ 1 -> total;
				
		lstMusicInfo = new ArrayList<MusicInfo>();
		MusicInfo info = new MusicInfo();
		info.setnId(nRate);			
		info.setStrName(getMusicName(nRate));
		
		lstMusicInfo.add(info);
				
		int i = 1;
		
		while(lstMusicInfo.size() < nNum)
		{			
			nRate = r.nextInt(total) + 1;
			
			if (isSame(lstMusicInfo, nRate) == false)	// không trùng
			{
				info = new MusicInfo();
				info.setnId(nRate);
				info.setStrName(getMusicName(nRate));		
				lstMusicInfo.add(info);
			}				
		}
		
		// giả sử lst = 8,1,2,6
		// thiết lập button name
		for (int j = 0; j < lstMusicInfo.size(); j++)
		{
			if (j == 0)				
			{
				b1.setText(lstMusicInfo.get(j).getStrName());			
			}
			
			else if (j == 1)
				b2.setText(lstMusicInfo.get(j).getStrName());
			
			else if (j == 2)
				b3.setText(lstMusicInfo.get(j).getStrName());
			
			else if (j == 3)
				b4.setText(lstMusicInfo.get(j).getStrName());				
		}
		
		// play 4 music này
		playRandomMusic();						
	}
	
	private void createListeners()
	{				
		b1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//startSecondActivity(1);	
				
					
					/*
					case 1: // boom boom boom			
					case 2: // stand by me			
					case 3: // shalala			
					case 4: // head the world		
					case 5: // 我是不是该安静的走开			
					case 6: // chinito		
					case 7: // one love			
					case 8: // happy Arabi
					*/	
				
				
				setPointsForUser(b1);	
			}			
		});
		
		b2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//startSecondActivity(2);
				setPointsForUser(b2);
			}
		});
		
		b3.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				setPointsForUser(b3);	
			}
		});
		
		b4.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub				
				setPointsForUser(b4);	
			}
		});	
	
		
		// click vào button Start		
		btnStart.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				// chọn ra 4 bài hát bất kỳ -> load tên lên button
				// random 4 bai hat do hát lên, cho user chọn
								
				btnStart.setEnabled(false);
				b1.setEnabled(true);
				b2.setEnabled(true);
				b3.setEnabled(true);
				b4.setEnabled(true);
				
				ChooseRandomMusic(4, 8); // chọn ra 4 bài trong 8 bài hát!
				
				
			}
		});
		
		btnStop.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stopMusic();				
			}
		});
	}
	
	/*private void startSecondActivity(int buttonNum)
	{
		Intent intent = new Intent(this, SeconceActivity.class);
		intent.putExtra("BUTTON NUMBER", buttonNum);
		
		// open form
		startActivity(intent);
	}*/

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
}
