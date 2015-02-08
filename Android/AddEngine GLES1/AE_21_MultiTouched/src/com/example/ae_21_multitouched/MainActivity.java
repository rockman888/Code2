/*
 * Khi sử dụng libs 4.2.2 thì có 1 project mới tự sinh ra
 * Project đó tên là: appcompat_v7
 *  
 */

package com.example.ae_21_multitouched;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.AnalogOnScreenControl.IAnalogOnScreenControlListener;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.extension.input.touch.controller.MultiTouch;
import org.anddev.andengine.extension.input.touch.controller.MultiTouchController;
import org.anddev.andengine.extension.input.touch.exception.MultiTouchException;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.MathUtils;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;

	private Camera mCamera;

	// Facebook
	private Texture mTexture;
	private TextureRegion mFaceTextureRegion;

	// control
	private Texture mOnScreenControlTexture;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private TextureRegion mOnScreenControlKnobTextureRegion;

	@Override
	public Engine onLoadEngine() {
		// TODO Auto-generated method stub

		// khai báo camera
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

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
							"MultiTouch detected,  but your device has problem " +
							"distinguishing between fingers.\n\n Controls are placed " +
							"at different vertical locations",
							Toast.LENGTH_LONG).show();

			} else
				Toast.makeText(
						this,
						"Sorry your device does NOT SUPPORT MultiTouch!\n\nFalling " +
						"back to singleTouch\n\n Controls are placed at different " +
						"verrical localtions",
						Toast.LENGTH_LONG).show();

		} catch (final MultiTouchException e) {
			Toast.makeText(
					this,
					"version andorid ko hỗ trợ đa điểm (chỉ sử dung đơn điểm, chỉ sử " +
					"dụng 1 điều khiển trong 1 thời điểm",
					Toast.LENGTH_LONG).show();

		}

		return engine;
	}

	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub

		TextureRegionFactory.setAssetBasePath("gfx/");

		// image
		mTexture = new Texture(32, 32, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mFaceTextureRegion = TextureRegionFactory.createFromAsset(
				this.mTexture, this, "face_box.png", 0, 0);

		// control
		this.mOnScreenControlTexture = new Texture(256, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mOnScreenControlBaseTextureRegion = TextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_base.png", 0, 0);
		this.mOnScreenControlKnobTextureRegion = TextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_knob.png", 128, 0);

		this.mEngine.getTextureManager().loadTextures(
				this.mOnScreenControlTexture, this.mTexture);

	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene();
		scene.setBackground(new ColorBackground(0.09804f, 0.627f, 0.8784f));

		final int centerX = (CAMERA_WIDTH - this.mFaceTextureRegion.getWidth()) / 2;
		final int centerY = (CAMERA_HEIGHT - this.mFaceTextureRegion
				.getHeight()) / 2;
		final Sprite face = new Sprite(centerX, centerY,
				this.mFaceTextureRegion);

		final PhysicsHandler physicsHandler = new PhysicsHandler(face);
		face.registerUpdateHandler(physicsHandler);

		face.setScale(4);
		scene.attachChild(face);

		// velocity control (left)
		final int x1 = 0;
		final int y1 = CAMERA_HEIGHT
				- this.mOnScreenControlBaseTextureRegion.getHeight();
		final AnalogOnScreenControl velocityOnScreenControl = new AnalogOnScreenControl(
				x1, y1, this.mCamera, this.mOnScreenControlBaseTextureRegion,
				this.mOnScreenControlKnobTextureRegion, 0.1f,
				new IAnalogOnScreenControlListener() {

					@Override
					public void onControlChange(
							BaseOnScreenControl pBaseOnScreenControl,
							float pValueX, float pValueY) {
						// TODO Auto-generated method stub
						physicsHandler
								.setVelocity(pValueX * 100, pValueX * 100);
					}

					@Override
					public void onControlClick(
							AnalogOnScreenControl pAnalogOnScreenControl) {
						// TODO Auto-generated method stub

					}
				});

		velocityOnScreenControl.getControlBase().setBlendFunction(
				GL10.GL_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		velocityOnScreenControl.getControlBase().setAlpha(0.5f);

		scene.setChildScene(velocityOnScreenControl);

		// rotation control (right)
		final int y2 = CAMERA_HEIGHT
				- this.mOnScreenControlBaseTextureRegion.getHeight() - 25;
		final int x2 = CAMERA_WIDTH
				- this.mOnScreenControlBaseTextureRegion.getWidth();
		final AnalogOnScreenControl rotationOnScreenControl = new AnalogOnScreenControl(
				x2, y2, this.mCamera, this.mOnScreenControlBaseTextureRegion,
				this.mOnScreenControlKnobTextureRegion, 0.1f,
				new IAnalogOnScreenControlListener() {

					@Override
					public void onControlChange(
							BaseOnScreenControl pBaseOnScreenControl,
							float pValueX, float pValueY) {
						// TODO Auto-generated method stub

						if (pValueX == x1 && pValueY == x1)
							face.setRotation(x1);

						else
							face.setRotation(MathUtils.radToDeg((float) Math
									.atan2(pValueX, -pValueY)));

					}

					@Override
					public void onControlClick(
							AnalogOnScreenControl pAnalogOnScreenControl) {
						// TODO Auto-generated method stub

					}
				});

		rotationOnScreenControl.getControlBase().setBlendFunction(
				GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		rotationOnScreenControl.getControlBase().setAlpha(0.5f);

		velocityOnScreenControl.setChildScene(rotationOnScreenControl);

		return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}

}