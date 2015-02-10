package com.example.jump2;

import com.example.jump2.manager.ResourceManager;
import com.example.jump2.manager.SceneManager;

import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.WakeLockOptions;
import org.andengine.engine.options.resolutionpolicy.FillResolutionPolicy;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.Background;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.engine.camera.Camera;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;

import android.view.KeyEvent;

public class MainActivity extends BaseGameActivity {

	private org.andengine.engine.camera.Camera camera;

	/*
	 * There are separate methods responsible for loading graphics, fonts and
	 * sounds, also methods responsible for unloading textures and loading them
	 * again (needed for memory saving purposes while switching between menu and
	 * game scenes) There is also a prepareManager method, which assigns
	 * required parameters for our manager, we have to use this method while
	 * initializing our game.
	 */

	private ResourceManager resourceManager;

	@Override
	public EngineOptions onCreateEngineOptions() {
		// TODO Auto-generated method stub

		/*
		camera = new Camera(0, 0, 480, 800);
		EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE_FIXED, new RatioResolutionPolicy(
						480, 800), this.camera);
		engineOptions.getAudioOptions().setNeedsMusic(true).setNeedsSound(true);
		engineOptions.setWakeLockOptions(WakeLockOptions.SCREEN_ON);
		return engineOptions;
		*/
		
		 camera = new Camera(0, 0, 400, 800);
		    EngineOptions engineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_FIXED, 
		    new FillResolutionPolicy(), camera);
		    return engineOptions;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		// TODO Auto-generated method stub

		/*
		ResourceManager.prepareManager(mEngine, this, this.camera,
				getVertexBufferObjectManager());
		resourceManager = ResourceManager.getInstance();
		*/
		
		/*
		BitmapTextureAtlas yourTexture;
		ITextureRegion yourTextureRegion;
		
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
	    yourTexture = new BitmapTextureAtlas(getTextureManager(), 256, 256, TextureOptions.DEFAULT);
	    yourTextureRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(yourTexture, this, "splash.png", 0, 0);
	    yourTexture.load();  */
		
		
		
		pOnCreateResourcesCallback.onCreateResourcesFinished();

	}

	// execute the createSplashScene() -> open SplashScene
	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		// TODO Auto-generated method stub

		// SceneManager.getInstance().createSplashScene(pOnCreateSceneCallback);
		
		Scene scene = new Scene();
	     scene.setBackground(new Background(0.09804f, 0.6274f, 0.8784f));
	     // return scene;
	}

	/*
	 * perform certain tasks while displaying splash (inside onPopulateScene in
	 * your Activity)(non-Javadoc)
	 * 
	 * @see
	 * org.andengine.ui.IGameInterface#onPopulateScene(org.andengine.entity.
	 * scene.Scene, org.andengine.ui.IGameInterface.OnPopulateSceneCallback)
	 */

	/* (non-Javadoc)
	 * @see android.app.Activity#onKeyDown(int, android.view.KeyEvent)
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		
		if (keyCode == KeyEvent.KEYCODE_BACK)
			SceneManager.getInstance().getCurrentScene().onBackKeyPressed();
		
		return false;
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		// TODO Auto-generated method stub

		mEngine.registerUpdateHandler(new TimerHandler(2f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub

						mEngine.unregisterUpdateHandler(pTimerHandler);
						
						// load menu resources, create menu scene, set menu
						// scene using scene manager disposeSplashScene();
						
						SceneManager.getInstance().createMenuScene();			

					}
				}));
		pOnPopulateSceneCallback.onPopulateSceneFinished();
	}

	/* (non-Javadoc)
	 * @see org.andengine.ui.activity.BaseGameActivity#onDestroy()
	 */
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		System.exit(0);
	}
}
