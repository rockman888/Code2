/*
 * Digital Screen Control => di chuyển 4 hướng
 * 
 */

package com.example.ae_12_digitalscreencontrol;

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

	private final static int CAMERA_WIDTH = 480;
	private final static int CAMERA_HEIGHT = 800;

	private Camera mCamera;

	private Texture mTexture;
	private TiledTextureRegion textureRegionTank; 	// quản lý hình ảnh chuyển
													// động tank
	private TiledTextureRegion textureRegionNangTienCa; // quản lý hình ảnh
														// chuyển động nàng tiên
														// cá

	private int status = 0; // 0: ko chuyển động; 1: chuyển động

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

		// load các ảnh trong thư mục gfx vào:
		TextureRegionFactory.setAssetBasePath("gfx/");

		// tank image
		this.mTexture = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		Texture mTextureCa = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.textureRegionTank = TextureRegionFactory.createTiledFromAsset(
				this.mTexture, this, "tank.png", 0, 0, 8, 1); // 8 cột, 1 dòng
		this.textureRegionNangTienCa = TextureRegionFactory
				.createTiledFromAsset(mTextureCa, this, "nangtienca.png", 0, 0,
						5, 1); // 5 cột, 1 dòng 
		
		
		/*this.textureRegionNangTienCa = TextureRegionFactory
				.createTiledFromAsset(mTextureCa, this, "role.png", 0, 0,
						3, 4); // 3 cột, 4 dòng*/

		// control image
		this.mOnScreenControlTexture = new Texture(256, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mOnScreenControlBaseTextureRegion = TextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_base.png", 0, 0);
		this.mOnScreenControlKnobTextureRegion = TextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_knob.png", 128, 0);

		this.mEngine.getTextureManager().loadTextures(mTexture, mTextureCa,
				mOnScreenControlTexture); // phải có cái dòng này mới load hình
											// dc

	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene(2);
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f)); // light
																				// blue
																				// scene

		// load ảnh lên màn hình game
		final AnimatedSprite car = new AnimatedSprite(CAMERA_WIDTH / 2,
				CAMERA_HEIGHT / 2, this.textureRegionTank);
		final PhysicsHandler physcicsHandler = new PhysicsHandler(car);
		car.setScale(2);
		car.registerUpdateHandler(physcicsHandler);

		final AnimatedSprite ca = new AnimatedSprite(CAMERA_WIDTH / 3,
				CAMERA_HEIGHT / 3, this.textureRegionNangTienCa);
		final PhysicsHandler physicsHandlerCa = new PhysicsHandler(ca);
		ca.setScale(2);
		ca.registerUpdateHandler(physicsHandlerCa);

		scene.attachChild(car);
		scene.attachChild(ca);

		// Digital On SCreen Control
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
						Log.v("pValueX = " + pValueX, " pValueY = " + pValueY);

						if (pValueX != 0 || pValueY != 0) {
							// chuyển động sang phải
							if (pValueX > 0) {
								
								//ca.animate(new long[]{200,200,200}, 6,8,true);
								
								if (status == 0)// ko chuyển động => cho xe
												// chuyển động
								{
									// xe
									car.animate(new long[] { 200, 200, 200,
											200, 200, 200, 200 }, 1, 7, true); // hình
																				// ảnh
																				// khi
																				// chuyển
																				// động
																				// qua
																				// phải
									car.setRotation(0);

									// nàng tiên cá!
									ca.animate(
											new long[] { 200, 200, 200, 200 },
											1, 4, true);
									ca.setFlippedHorizontal(true);

									status = 1;
								} else if (status == 1) // chuyển động
								{
									car.setRotation(0);
								}
							}

							// chuyển động sang trái
							else if (pValueX < 0) {
								if (status == 0) {
									car.animate(new long[] { 200, 200, 200,
											200, 200, 200, 200 }, 1, 7, true);

									ca.animate(
											new long[] { 200, 200, 200, 200 },
											1, 4, true);

									status = 1;
								} else if (status == 1) {
									car.setRotation(180);
									ca.setFlippedHorizontal(true);
								}
							}

							// chuyển động xuống dưới
							else if (pValueY > 0) {
								if (status == 0) {
									car.animate(new long[] { 200, 200, 200,
											200, 200, 200, 200 }, 1, 7, true);
									car.setRotation(90); // hướng xuống

									ca.animate(
											new long[] { 200, 200, 200, 200 },
											1, 4, true);

									ca.setFlippedVertical(true);
									status = 1;
								} else if (status == 1) {
									car.setRotation(90);

									ca.setFlippedVertical(true);
								}
							}

							// chuyển động lên trên
							else if (pValueY < 0) {
								if (status == 0) {
									car.animate(new long[] { 200, 200, 200,
											200, 200, 200, 200 }, 1, 7, true);
									car.setRotation(270); // hướng lên*/

									ca.animate(
											new long[] { 200, 200, 200, 200 },
											1, 4, true);
									ca.setFlippedVertical(true);
									status = 1;
								} else if (status == 1) {
									car.setRotation(270);
									ca.setFlippedVertical(true);
								}
							}
							physcicsHandler.setVelocity(pValueX * 50,
									pValueY * 50);
							physicsHandlerCa.setVelocity(pValueX * 50,
									pValueY * 50);
						} else // ko ngừng nếu ko có đoạn này
						{
							car.animate(new long[] { 200 }, new int[] { 0 },
									10000);
							status = 0;

							ca.animate(new long[] { 200 }, new int[] { 0 },
									10000);
							ca.setFlippedHorizontal(true);
							physcicsHandler.setVelocity(pValueY * 0,
									pValueY * 0);
							physicsHandlerCa.setVelocity(pValueX * 0,
									pValueY * 0);
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
