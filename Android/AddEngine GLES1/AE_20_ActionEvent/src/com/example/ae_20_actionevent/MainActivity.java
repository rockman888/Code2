package com.example.ae_20_actionevent;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.input.touch.TouchEvent;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;

public class MainActivity extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;

	private Camera mCamera;

	private Texture mTextureEarth;
	private TextureRegion mTextureRegionEarth;

	private Texture mTextureBird;
	private TiledTextureRegion mTiledTextRegion2Bird;

	@Override
	public Engine onLoadEngine() {
		// TODO Auto-generated method stub
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		// nằm ngang : ScreenOrientation.PORTRAIT
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
		engineOptions.getTouchOptions().setRunOnUpdateThread(true);

		// khởi tạo biến
		return new Engine(engineOptions);
	}

	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub

		TextureRegionFactory.setAssetBasePath("gfx/");

		this.mTextureEarth = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		mTextureRegionEarth = TextureRegionFactory.createFromAsset(
				this.mTextureEarth, this, "earth.png", 0, 0);
		this.mEngine.getTextureManager().loadTexture(this.mTextureEarth);

		this.mTextureBird = new Texture(256, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mTiledTextRegion2Bird = TextureRegionFactory.createTiledFromAsset(
				this.mTextureBird, this, "2bird.png", 0, 0, 2, 1);
		this.mEngine.getTextureManager().loadTexture(this.mTextureBird);
	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene();
		scene.setBackground(new ColorBackground(0, 0, 0));

		// load sprite;
		final Sprite earth = new Sprite(200, 300, this.mTextureRegionEarth);
		earth.setScale(0.5f);
		scene.attachChild(earth);

		//final PhysicsHandler mPhysicsHandler = new PhysicsHandler(anispr);
		//anispr.registerUpdateHandler(mPhysicsHandler);
		
		// load animation sprite
		final AnimatedSprite anispr = new AnimatedSprite(30,
				CAMERA_HEIGHT - 100, this.mTiledTextRegion2Bird) {
			// Touched -> phóng to ảnh

			public boolean onAreaTouched(final TouchEvent pSceneTouchEvent,
					final float pTouchAreaLocalX, final float pTouchAreaLocalY) {

				if (pSceneTouchEvent.getAction() == TouchEvent.ACTION_DOWN) {
					this.setCurrentTileIndex(1, 0); // set current ảnh = 1;

					this.setPosition(100, 100);

					Log.v("", "pTouchAreaLocalX: " + pTouchAreaLocalX
							+ " pTouchAreaLocalY: " + pTouchAreaLocalY);
					earth.setScale(1f);
				} else {
					this.setCurrentTileIndex(0, 0); // set current ảnh = 0;
					earth.setScale(0.5f);
				}
				return true;
			};
		};
	
		
		
		scene.registerTouchArea(anispr);
		scene.attachChild(anispr);

		return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}

}
