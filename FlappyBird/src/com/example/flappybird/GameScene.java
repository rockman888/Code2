package com.example.flappybird;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.entity.util.FPSCounter;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import android.net.nsd.NsdManager.RegistrationListener;

import com.example.flappybird.SceneManager.SceneType;

public class GameScene extends BaseScene implements IOnSceneTouchListener {

	// HUD stands for heads-up display. It stays at the same position on the
	// screen and usually used for displaying score or game controls. HUD is a
	// subclass of the Scene class.
	private Text mHudText;
	private int score;
	private int most;

	// AnimatedSprite is a subclass of TiledSprite. It repeatedly changes
	// the tiles after specific duration to create an animation.
	private AnimatedSprite mBird;

	private Pipe mPipe;
	private PipePool mPipePool;

	private boolean mGameOver;
	private float mPipeWidth;

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}

	// In brief, we create new entities for ground and roof, and obtain pipe
	// from the pool. The other modification is in update handler. When pipe
	// collides with the bird we end the game and play a sound. Otherwise, we
	// update the score if the bird is able to pass through the pipes. Also, if
	// the pipe goes out of screen then we recycle it and obtain a new one from
	// the pool after shuffling it.

	@Override
	public void createScene() {
		// TODO Auto-generated method stub

		mPipeWidth = mResourceManager.mPipeTextureRegion.getWidth();

		// create entities;
		float x1 = 0;
		float y1 = SCREEN_HEIGHT
				- mResourceManager.mParallaxLayerFront.getHeight();

		float x2 = SCREEN_WIDTH;
		float y2 = mResourceManager.mParallaxLayerFront.getHeight();

		final Rectangle ground = new Rectangle(x1, y1, x2, y2,
				mVertexBufferObjectManager);
		ground.setColor(Color.TRANSPARENT);

		final Rectangle roof = new Rectangle(0, 0, SCREEN_WIDTH, 1,
				mVertexBufferObjectManager);
		roof.setColor(Color.TRANSPARENT);

		mPipePool = new PipePool(mResourceManager.mPipeTextureRegion,
				mVertexBufferObjectManager, ground.getY());
		mPipePool.batchAllocatePoolItems(10);
		mPipe = mPipePool.obtainPoolItem();

		attachChild(ground);
		attachChild(roof);
		attachChild(mPipe);

		// ******** The actual collision - checking **********
		mEngine.registerUpdateHandler(new FPSLogger());

		setOnSceneTouchListener(this);
		registerUpdateHandler(new IUpdateHandler() {

			@Override
			public void onUpdate(float pSecondsElapsed) {
				// TODO Auto-generated method stub

			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub

			}

		});

		most = mActivity.getMaxScore();

		// create HUD for score
		HUD gameHUD = new HUD();

		// create Score Text
		mHudText = new Text(SCREEN_WIDTH / 2, 50, mResourceManager.mFont5,
				"0123456789", new TextOptions(HorizontalAlign.LEFT),
				mVertexBufferObjectManager);
		mHudText.setText("0");
		mHudText.setX((SCREEN_WIDTH - mHudText.getWidth()) / 2);
		mHudText.setVisible(false);
		gameHUD.attachChild(mHudText);
		mCamera.setHUD(gameHUD);

		// create entities
		final float birdX = (SCREEN_WIDTH - mResourceManager.mBirdTextureRegion
				.getWidth()) / 2 - 50;
		final float birdY = (SCREEN_HEIGHT - mResourceManager.mBirdTextureRegion
				.getHeight()) / 2 - 30;

		mBird = new AnimatedSprite(birdX, birdY,
				mResourceManager.mBirdTextureRegion, mVertexBufferObjectManager);
		mBird.setZIndex(10); // We've set Z-index to a high value so that the
								// bird appears on top of other entities.
		mBird.animate(200);

		attachChild(mBird);
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		mSceneManager.setScene(SceneType.SCENE_MENU);
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub

	}

}
