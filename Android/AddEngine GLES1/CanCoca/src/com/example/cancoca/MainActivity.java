package com.example.cancoca;

import java.util.LinkedList;
import java.util.Random;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.BoundCamera;
import org.anddev.andengine.engine.handler.timer.ITimerCallback;
import org.anddev.andengine.engine.handler.timer.TimerHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.MoveXModifier;
import org.anddev.andengine.entity.modifier.MoveYModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.input.touch.controller.MultiTouch;
import org.anddev.andengine.extension.input.touch.controller.MultiTouchController;
import org.anddev.andengine.extension.input.touch.exception.MultiTouchException;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends BaseGameActivity {

	private static int CAMERA_WIDTH = 480;
	private static int CAMERA_HEIGHT = 800;

	private BoundCamera mCamera;

	private Texture mTextureTarget;
	private TiledTextureRegion mTiledTextureRegion;
	
	private Texture mTextureTargetShenLong;
	private TiledTextureRegion mTiledTextureRegionShenLong;
	
	private LinkedList<AnimatedSprite> targetLLChinaShip;
	private LinkedList<AnimatedSprite> targetsToBeAddedChinaShip;

	@Override
	public Engine onLoadEngine() {
		// TODO Auto-generated method stub
		// lay gia tri he thong man hinh
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		// quy doi ra pixels
		int width = dm.widthPixels;
		int height = dm.heightPixels;

		CAMERA_WIDTH = width;
		CAMERA_HEIGHT = height;

		Log.v("GetWindowManager", "width = " + width + " , height = " + height);

		this.mCamera = new BoundCamera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		// nằm ngang : ScreenOrientation.PORTRAIT
		// khai báo sử dụng đa điểm trong app
		final Engine engine = new Engine(new EngineOptions(true,
				ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera));

		try {
			if (MultiTouch.isSupported(this)) {
				engine.setTouchController(new MultiTouchController());

				if (MultiTouch.isSupportedDistinct(this))
					Toast.makeText(this,
							"Cảm ứng đa điểm - các điều khiển hoạt động tốt!",
							Toast.LENGTH_LONG).show();

				else
					Toast.makeText(
							this,
							"MultiTouch detected,  but your device has problem distinguishing between fingers.\n\n Controls are placed at different vertical locations",
							Toast.LENGTH_LONG).show();

			} else
				Toast.makeText(
						this,
						"Sorry your device does NOT SUPPORT MultiTouch!\n\nFalling back to singleTouch\n\n Controls are placed at different verrical localtions",
						Toast.LENGTH_LONG).show();

		} catch (final MultiTouchException e) {
			Toast.makeText(
					this,
					"version andorid ko hỗ trợ đa điểm (chỉ sử dung đơn điểm, chỉ sử dụng 1 điều khiển trong 1 thời điểm",
					Toast.LENGTH_LONG).show();
		}

		return engine;

	}
	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub
		
		TextureRegionFactory.setAssetBasePath("gfx/");
		
		// load target hero
		this.mTextureTarget = new Texture(256, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA );
		// this.mTiledTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTextureTarget, this, "coca.png", 0, 0, 1, 1);
		
		this.mTiledTextureRegion = TextureRegionFactory.createTiledFromAsset(this.mTextureTarget, this, "hero2.png", 0, 0, 3, 1);
				
		this.mTextureTargetShenLong = new Texture(1024, 256, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mTiledTextureRegionShenLong = TextureRegionFactory.createTiledFromAsset(this.mTextureTargetShenLong, this, "shenlong.png", 0, 0, 5, 1);
		
		this.mEngine.getTextureManager().loadTextures(this.mTextureTarget, this.mTextureTargetShenLong);
	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene();
		scene.setBackground(new ColorBackground(0.09804f, 0.627f, 0.8784f));
		
		targetsToBeAddedChinaShip = new LinkedList<AnimatedSprite>();
		
		createSpriteSpawnTimeHandlerTarget(scene);
		return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}
	private void createSpriteSpawnTimeHandlerTarget(final Scene scene)
	{
		TimerHandler spriteTimerHandler;
		float mEffectSpawnDelay = 1f;
		
		spriteTimerHandler = new TimerHandler(mEffectSpawnDelay, true, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				add_Target(scene);
			}
		} );
		
		this.getEngine().registerUpdateHandler(spriteTimerHandler);
	}
	
	public void add_Target(final Scene scene)
	{
		Random rand = new Random();		
		
		int minX = 5;
		int maxX = CAMERA_WIDTH - mTiledTextureRegion.getWidth();
		
		int rangeX = maxX - minX;
		int x = rand.nextInt(rangeX) + minX;
		int y = CAMERA_HEIGHT / 8;
		
		// phát sinh random trong khoảng!
		AnimatedSprite target = new AnimatedSprite(x, y, this.mTiledTextureRegion);
		target.animate(new long[] {200,200,200}, 0, 2, true );
		scene.attachChild(target);
		target.setScale(1f);
		
		AnimatedSprite targetShenLong = new AnimatedSprite(x, y, this.mTiledTextureRegionShenLong);
		targetShenLong.animate(new long[]{200,200,200,200, 200}, 0, 4, true);
		scene.attachChild(targetShenLong);
		target.setScale(1.5f);	
				
		int minDuration = 3; // tốc độ lon coca bay chậm nhất
		int maxDuration = 8; // tốc độ lon coca bay nhanh nhất
		
		int rangeDuration = maxDuration - minDuration;
		int actualDuration = rand.nextInt(rangeDuration) + minDuration;
		
		MoveYModifier mod = new MoveYModifier(actualDuration, target.getY(), CAMERA_HEIGHT);
		target.registerEntityModifier(mod);
		
		MoveYModifier modShenLong = new MoveYModifier(actualDuration, targetShenLong.getY(), CAMERA_HEIGHT);
		targetShenLong.registerEntityModifier(modShenLong);
		
		
		
		
		
	/*	int nLucky = rand.nextInt(10) + 1;
		if (nLucky < 5)	// random theo hàng ngang
		{
			MoveYModifier mod = new MoveYModifier(actualDuration,
					target.getY(), CAMERA_HEIGHT);
			target.registerEntityModifier(mod);
		}
		else	// random theo hàng dọc
		{
			MoveXModifier xMod = new MoveXModifier(actualDuration, target.getX(), CAMERA_WIDTH);
			target.registerEntityModifier(xMod);
		}	*/	
		
		targetsToBeAddedChinaShip.add(target);		
	}	
}
