/*
 * Analog Digital => di chuyển 8 hướng
 */

package com.example.ae_11_analog;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl.*;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.ScaleModifier;
import org.anddev.andengine.entity.modifier.SequenceEntityModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.text.TickerText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.HorizontalAlign;

import android.util.Log;

public class MainActivity extends BaseGameActivity {

	private final static int CAMERA_WIDTH = 480;
	private final static int CAMERA_HEIGHT = 800;
	private Camera mCamera;

	// font chữ
	// private Font mFontTicker;
	// private Texture mFontTextureTicker;

	// ///////////////////////////////////////
	private Texture mTexture;
	private TextureRegion mFaceTextureRegion;

	// 3 Texture quản lý 3 tấm hình: earch, onscreen_control_base,
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
		// font chữ
		/*
		 * this.mFontTextureTicker = new Texture(256, 256,
		 * TextureOptions.BILINEAR_PREMULTIPLYALPHA); this.mFontTicker = new
		 * Font(this.mFontTextureTicker, Typeface.create(Typeface.SANS_SERIF,
		 * Typeface.BOLD_ITALIC), 25, true, Color.YELLOW);
		 * this.mEngine.getTextureManager().loadTexture(mFontTextureTicker);
		 * this.mEngine.getFontManager().loadFont(mFontTicker);
		 */

		// load các ảnh trong thư mục gfx vào:
		TextureRegionFactory.setAssetBasePath("gfx/");

		this.mTexture = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFaceTextureRegion = TextureRegionFactory.createFromAsset(
				this.mTexture, this, "earth.png", 0, 0);

		this.mOnScreenControlTexture = new Texture(256, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.mOnScreenControlBaseTextureRegion = TextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_base.png", 0, 0);

		this.mOnScreenControlKnobTextureRegion = TextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_knob.png", 128, 0);

		this.mEngine.getTextureManager().loadTexture(mTexture); // phải có cái
																// dòng này mới
																// load hình dc
		this.mEngine.getTextureManager().loadTexture(mOnScreenControlTexture);

	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene(2);
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f)); // light blue scene

		// font chữ
		/*
		 * final Text t = new TickerText(16, 10, this.mFontTicker,
		 * "AE-11 Succeed!", HorizontalAlign.CENTER, 20); scene.attachChild(t);
		 */

		// ////////////////////// control

		// load ảnh
		final Sprite face = new Sprite(CAMERA_WIDTH / 2, CAMERA_WIDTH / 2,
				this.mFaceTextureRegion);

		// đăng lý face là 1 chuyển động vật lý => chuyển động mới có thể
		// control
		final PhysicsHandler physicsHandler = new PhysicsHandler(face);
		face.registerUpdateHandler(physicsHandler);
		scene.attachChild(face);

		// load control lên màn hình!

		AnalogOnScreenControl analogOnScreenControl = new AnalogOnScreenControl(
				50, CAMERA_HEIGHT
						- this.mOnScreenControlBaseTextureRegion.getHeight()
						* 2, this.mCamera,
				this.mOnScreenControlBaseTextureRegion,
				this.mOnScreenControlKnobTextureRegion, 0.1f, 200,
				new IAnalogOnScreenControlListener() {

					@Override
					public void onControlChange(
							BaseOnScreenControl pBaseOnScreenControl,
							float pValueX, float pValueY) {
						// TODO Auto-generated method stub

						Log.v("pValueX = " + pValueX, "pValueY = " + pValueY);
						physicsHandler
								.setVelocity(pValueX * 200, pValueY * 200); // tốc
																			// độ

					}

					@Override
					public void onControlClick(
							AnalogOnScreenControl pAnalogOnScreenControl) {
						// TODO Auto-generated method stub
						face.registerEntityModifier(new SequenceEntityModifier(
								new ScaleModifier(0.25f, 1, 1.5f),
								new ScaleModifier(0.25f, 1.5f, 1f)));
					}
				});

		// hai câu lệnh đăng lý vật lý bắt buộc => thủ tục
		analogOnScreenControl.getControlBase().setBlendFunction(GL10.GL_ALPHA,
				GL10.GL_ONE_MINUS_SRC_ALPHA);
		analogOnScreenControl.getControlBase().setScaleCenter(0, 128);

		// reset lại vị trí nút bấm sang trái phải / trên dưới => bỏ bấm trả về
		// vị trí cũ (ở giữa control)
		analogOnScreenControl.refreshControlKnobPosition();

		scene.setChildScene(analogOnScreenControl);

		return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}

}
