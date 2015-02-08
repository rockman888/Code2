package com.example.ae_13_digitalscreencontrol3x4;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.anddev.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.util.Log;

public class MainActivity extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	private Camera mCamera;
	private DIRECTION mDirection = DIRECTION.BOTTOM;

	private Texture mTexture;
	private TiledTextureRegion mTextureRegion;

	private enum DIRECTION {
		LEFT, RIGHT, UP, BOTTOM
	}

	// 2 TextureRegion quản lý 2 hình onscreen_control_base,
	// onscreen_control_knob
	private Texture mOnScreenControlTexture;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private TextureRegion mOnScreenControlKnobTextureRegion;

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
		// load các ảnh trong thư mục gfx vào:
		TextureRegionFactory.setAssetBasePath("gfx/");

		// tank image
		this.mTexture = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.mTextureRegion = TextureRegionFactory.createTiledFromAsset(
				this.mTexture, this, "role.png", 0, 0, 3, 4); // 3 cột, 4 dòng
		
		
		/*this.mTextureRegion = TextureRegionFactory.createTiledFromAsset(
				this.mTexture, this, "nangtienca.png", 0, 0, 5, 1); // 5 cột, 1 dòng
		 */
		
		this.mEngine.getTextureManager().loadTexture(mTexture);

		// control
		this.mOnScreenControlTexture = new Texture(256, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.mOnScreenControlBaseTextureRegion = TextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_base.png", 0, 0);

		this.mOnScreenControlKnobTextureRegion = TextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_knob.png", 128, 0);

		this.mEngine.getTextureManager().loadTexture(mOnScreenControlTexture);
	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene(2);
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f)); // light

		// loal sprites
		final AnimatedSprite sprite = new AnimatedSprite(CAMERA_WIDTH / 2,
				CAMERA_HEIGHT / 2, this.mTextureRegion);
		final PhysicsHandler physicHandler = new PhysicsHandler(sprite);

		sprite.setScale(2);
		sprite.registerUpdateHandler(physicHandler);
		scene.attachChild(sprite);

		// control
		final DigitalOnScreenControl digitalOnScreenControl = new DigitalOnScreenControl(
				0, CAMERA_HEIGHT
						- this.mOnScreenControlBaseTextureRegion.getHeight(),
				this.mCamera, this.mOnScreenControlBaseTextureRegion,
				this.mOnScreenControlKnobTextureRegion, 0.1f,
				new IOnScreenControlListener() {

					@Override
					public void onControlChange(
							BaseOnScreenControl pBaseOnScreenControl,
							float pValueX, float pValueY) {
						// TODO Auto-generated method stub

						if (pValueX != 0 || pValueY != 0) {	// 他的妈妈！							
							if (pValueX > 0) // sang phải
							{
								sprite.animate(new long[] { 200, 200, 200 }, 6,
										8, true);
								
								/*sprite.animate(new long[] { 200, 200, 200, 200 }, 1,
										4, true);*/
								mDirection = DIRECTION.RIGHT;
							}


							else if (pValueX < 0) // sang trái
							{								
								sprite.animate(new long[] { 200, 200, 200 }, 3,
										5, true);
								mDirection = DIRECTION.LEFT;
							}

							else if (pValueY < 0)// lên trên
							{
								sprite.animate(new long[] { 200, 200, 200 }, 9,
										11, true);
								mDirection = DIRECTION.UP;
							}

							else if (pValueY > 0)// xuống dưới
							{
								sprite.animate(new long[] { 200, 200, 200 }, 0,
										2, true);
								mDirection = DIRECTION.BOTTOM;
							}
							
							// di chuyển bằng control
							physicHandler.setVelocity(pValueX * 100,
									pValueY * 100);
							
							//Log.v("aaaaaaaaaaaaaaa", "physicallssssssssssssss = " + pValueX * 100);

						} else // ko chuyển động
						{

							if (mDirection == DIRECTION.LEFT)
								sprite.animate(new long[] { 200 },
										new int[] { 3 }, 10000);

							else if (mDirection == DIRECTION.RIGHT)
								sprite.animate(new long[] { 200 },
										new int[] { 6 }, 10000);

							else if (mDirection == DIRECTION.UP)
								sprite.animate(new long[] { 200 },
										new int[] { 9 }, 10000);

							else
								sprite.animate(new long[] { 200 },
										new int[] { 0 }, 10000);

							physicHandler.setVelocity(pValueX * 0, pValueY * 0);
						}
					}
				});

		digitalOnScreenControl.getControlBase().setBlendFunction(
				GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		digitalOnScreenControl.getControlBase().setAlpha(0.5f);
		digitalOnScreenControl.getControlBase().setScaleCenter(0, 128);
		digitalOnScreenControl.getControlBase().setScale(1.25f);
		digitalOnScreenControl.getControlKnob().setScale(1.25f);

		// reset lại trang thái
		digitalOnScreenControl.refreshControlKnobPosition();
		scene.setChildScene(digitalOnScreenControl);

		return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub
	}
}
