package com.example.letsjump;

import org.andengine.engine.camera.BoundCamera;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.ui.activity.SimpleBaseGameActivity;

import android.view.KeyEvent;

import com.example.letsjump.SceneManager.SceneType;

public class MainActivity extends SimpleBaseGameActivity {

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		// return super.onKeyDown(keyCode, event);

		if (keyCode == KeyEvent.KEYCODE_BACK)
			SceneManager.getInstance().getCurrentScene().onBackKeyPressed();

		return false;
	}

	// ---------------------------------------------
	// VARIABLES
	// ---------------------------------------------

	
	private Camera mCamera;
	private ResourcesManager mResourceManager;
	private SceneManager mSceneManager;

	public final int WIDTH = 800; // 438
	public final int HEIGHT = 480; // 729

	@Override
	public EngineOptions onCreateEngineOptions() {
		
		// We will set camera bound to set certain limits in camera movement, so for
		// example camera will not follow player after following down and dying.
		// Will stop on 0 coordinate on the X axis. First thing we need to do, is to
		// change our camera type to BoundCamera instead of Camera.
		
		mCamera = new BoundCamera(0, 0, WIDTH, HEIGHT);
						

		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						WIDTH, HEIGHT), mCamera);

		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
	}

	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
		mResourceManager = ResourcesManager.getInstance();
		mResourceManager.prepare(this);
		mResourceManager.loadSplashResource();
		mResourceManager.loadMenuResources();
		mResourceManager.loadGameResources();
		mSceneManager = SceneManager.getInstance();
	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub

		// hai giây sẽ load màn hình SceneType.SCENE_MENU;
		// mEngine.registerUpdateHandler(new TimerHandler(1.0f, new
		// ITimerCallback() {
		float time = General.GAMESCENE_TIME;
		mEngine.registerUpdateHandler(new TimerHandler(time,
				new ITimerCallback() {
					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub

						mEngine.unregisterUpdateHandler(pTimerHandler);
						mSceneManager.setScene(SceneType.SCENE_MENU);
						mResourceManager.unloadSplashResources();
					}
				}));

		return mSceneManager.createSplashScene();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.exit(0);
	}
}