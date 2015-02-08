package com.fsoft.android.audiovideo;


import android.app.Activity;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class MyVideoPlayer extends Activity implements
        OnBufferingUpdateListener, OnCompletionListener,
        OnPreparedListener, OnVideoSizeChangedListener, SurfaceHolder.Callback {

    private static final String TAG = "AudioVideo";
    private int mVideoWidth;
    private int mVideoHeight;
    private MediaPlayer mMediaPlayer;
    private SurfaceView mPreview;
    private SurfaceHolder holder;
    private String path;
    private Bundle extras;

    private static final String AUDIOVIDEO = "audiovideo";
    
    private static final int LOCAL_VIDEO = 4;
    private static final int STREAM_VIDEO = 5;
    private static final int RESOURCES_VIDEO = 6;
    
    private boolean mIsVideoSizeKnown = false;
    private boolean mIsVideoReadyToBePlayed = false;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.mediaplayer_2);
        mPreview = (SurfaceView) findViewById(R.id.surface);
        holder = mPreview.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        extras = getIntent().getExtras();
    }

    private void playVideo(Integer AUDIOVIDEO) {
        doCleanUp();
        try {

            switch (AUDIOVIDEO) {
                case LOCAL_VIDEO:
                    /*
                     * TODO: Set the path variable to a local media file path.
                     */
                    path = "/sdcard/sample.mov";
                    // Create a new media player and set the listeners
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setDataSource(path);
                    mMediaPlayer.setDisplay(holder);
                    mMediaPlayer.prepare();
                    mMediaPlayer.setOnBufferingUpdateListener(this);
                    mMediaPlayer.setOnCompletionListener(this);
                    mMediaPlayer.setOnPreparedListener(this);
                    mMediaPlayer.setOnVideoSizeChangedListener(this);
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    break;
                case RESOURCES_VIDEO:
                    /**
                     * TODO: Upload a video file to res/raw folder 
                     */
                    mMediaPlayer = MediaPlayer.create(this, R.raw.samplemp4);
                    mMediaPlayer.start();
                case STREAM_VIDEO:
                    /*
                     * TODO: Set path variable to progressive streamable mp4 or
                     * 3gpp format URL. Http protocol should be used.
                     * Mediaplayer can only play "progressive streamable
                     * contents" which basically means: 1. the movie atom has to
                     * precede all the media data atoms. 2. The clip has to be
                     * reasonably interleaved.
                     * 
                     */
                    path = "http://www.bogotobogo.com/Video/sample.3gp";
                    // Create a new media player and set the listeners
                    mMediaPlayer = new MediaPlayer();
                    mMediaPlayer.setDataSource(path);
                    mMediaPlayer.setDisplay(holder);
                    mMediaPlayer.prepare();
                    mMediaPlayer.setOnBufferingUpdateListener(this);
                    mMediaPlayer.setOnCompletionListener(this);
                    mMediaPlayer.setOnPreparedListener(this);
                    mMediaPlayer.setOnVideoSizeChangedListener(this);
                    mMediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    break;
            }
        } catch (Exception e) {
            Log.e(TAG, "error: " + e.getMessage(), e);
        }
    }

    public void onBufferingUpdate(MediaPlayer arg0, int percent) {
        Log.d(TAG, "onBufferingUpdate percent:" + percent);

    }

    public void onCompletion(MediaPlayer arg0) {
        Log.d(TAG, "onCompletion called");
    }

    public void onVideoSizeChanged(MediaPlayer mp, int width, int height) {
        Log.v(TAG, "onVideoSizeChanged called");
        if (width == 0 || height == 0) {
            Log.e(TAG, "invalid video width(" + width + ") or height(" + height + ")");
            return;
        }
        mIsVideoSizeKnown = true;
        mVideoWidth = width;
        mVideoHeight = height;
        if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
            startVideoPlayback();
        }
    }

    public void onPrepared(MediaPlayer mediaplayer) {
        Log.d(TAG, "onPrepared called");
        mIsVideoReadyToBePlayed = true;
        if (mIsVideoReadyToBePlayed && mIsVideoSizeKnown) {
            startVideoPlayback();
        }
    }

    public void surfaceChanged(SurfaceHolder surfaceholder, int i, int j, int k) {
        Log.d(TAG, "surfaceChanged called");

    }

    public void surfaceDestroyed(SurfaceHolder surfaceholder) {
        Log.d(TAG, "surfaceDestroyed called");
    }

    public void surfaceCreated(SurfaceHolder holder) {
        Log.d(TAG, "surfaceCreated called");
        playVideo(extras.getInt(AUDIOVIDEO));
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaPlayer();
        doCleanUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseMediaPlayer();
        doCleanUp();
    }

    private void releaseMediaPlayer() {
        if (mMediaPlayer != null) {
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    private void doCleanUp() {
        mVideoWidth = 0;
        mVideoHeight = 0;
        mIsVideoReadyToBePlayed = false;
        mIsVideoSizeKnown = false;
    }

    private void startVideoPlayback() {
        Log.v(TAG, "startVideoPlayback");
        holder.setFixedSize(mVideoWidth, mVideoHeight);
        mMediaPlayer.start();
    }
}
