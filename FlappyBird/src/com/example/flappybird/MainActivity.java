// http://www.appsrox.com/android/tutorials/flappychick/6/
// 19 HUB

package com.example.flappybird;

import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.TextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.ui.activity.SimpleBaseGameActivity;
import org.andengine.util.color.Color;

import com.example.flappybird.SceneManager.SceneType;

/*public class MainActivity extends BaseGameActivity{

	public static final int CAMERA_WIDTH = 400;	//320
	public static final int CAMERA_HEIGHT = 800;	//480

	private org.andengine.engine.camera.Camera mCamera;
	private ResourceManager mResourceManager;
	private SceneManager mSceneManager;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		mCamera = new org.andengine.engine.camera.Camera(0, 0, CAMERA_WIDTH,
				CAMERA_HEIGHT);
		
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	
				
		engineOptions.getAudioOptions().setNeedsSound(true).setNeedsMusic(true);
		return engineOptions;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		// TODO Auto-generated method stub
		
		mResourceManager = ResourceManager.getInstance();	// thieu biloi
		mResourceManager.prepare(this);	// thieu bi loi
		mResourceManager.loadSplashResources();		
		mSceneManager = SceneManager.getInstance();
		
		
		pOnCreateResourcesCallback.onCreateResourcesFinished();
		
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		// TODO Auto-generated method stub
		Scene pScene = mSceneManager.createSplashScene();
		
		pOnCreateSceneCallback.onCreateSceneFinished(pScene);
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		// TODO Auto-generated method stub
		
		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				
				mEngine.unregisterUpdateHandler(pTimerHandler);
				mResourceManager.loadGameResources();
				mSceneManager.setScene(SceneType.SCENE_MENU);
				mResourceManager.unloadSplashResources();
				
			}
		}));
		
		pOnPopulateSceneCallback.onPopulateSceneFinished();
		
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		
		if (mSceneManager.getCurrentScene() != null)
		{
			mSceneManager.getCurrentScene().onBackKeyPressed();
			return;
		}		
		super.onBackPressed();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.exit(0);
	}	
}
*/

public class MainActivity extends SimpleBaseGameActivity {
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		
		if (mResourceManager.mMusic != null && mResourceManager.mMusic.isPlaying())
			mResourceManager.mMusic.pause();
		
		super.onPause();
	}

	public static final int CAMERA_WIDTH = 320;	//320
	public static final int CAMERA_HEIGHT = 480;	//480

	private org.andengine.engine.camera.Camera mCamera;
	private ResourceManager mResourceManager;
	private SceneManager mSceneManager;
	private SplashScene splashScene;
	
	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub
		mCamera = new org.andengine.engine.camera.Camera(0, 0, CAMERA_WIDTH,
				CAMERA_HEIGHT);
		
		final EngineOptions engineOptions = new EngineOptions(true,
			ScreenOrientation.PORTRAIT_FIXED, new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);
	
				
		engineOptions.getAudioOptions().setNeedsSound(true).setNeedsMusic(true);
		return engineOptions;
	}
	
	TextureRegion regImage;
	BitmapTextureAtlas texImage;
	@Override
	protected void onCreateResources() {
		// TODO Auto-generated method stub
		
		mResourceManager = ResourceManager.getInstance();	// thieu bi loi
		mResourceManager.prepare(this);	// thieu bi loi
		mResourceManager.loadSplashResources();		
		mSceneManager = SceneManager.getInstance();		
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
		System.exit(0);
	}

	@Override
	protected Scene onCreateScene() {
		// TODO Auto-generated method stub		

		mEngine.registerUpdateHandler(new TimerHandler(2f, new ITimerCallback() {						
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				
				mEngine.unregisterUpdateHandler(pTimerHandler);
				mResourceManager.loadGameResources();
				mSceneManager.setScene(SceneType.SCENE_MENU);
				mResourceManager.unloadSplashResources();				
			}
		}));
		
		
		return mSceneManager.createSplashScene();	
		// lỗi ko load full resource là do đây vì chỉ load resource ko có scene thì sẽ bị lỗi ~.~
		// phải làm cho cái scene hiểu thì mới load ko bị sai -> vào SceneManager -> fix lại
	}

	/*
	 * The abstract methods must be implemented by the sub types. Most of these
	 * methods would be invoked in SceneManager but onBackKeyPressed() needs to
	 * be tied to MainActivity for back button behaviour in a particular scene.
	*/ 
	
	@Override
	public void onBackPressed()
	{
		if (mSceneManager.getCurrentScene() != null)
		{
			mSceneManager.getCurrentScene().onBackKeyPressed();
			return;
		}
		
		super.onBackPressed();
	}
}