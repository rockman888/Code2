package com.example.ae_14_loadmap_vemap;

import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.DigitalOnScreenControl;
import org.anddev.andengine.engine.camera.hud.controls.BaseOnScreenControl.IOnScreenControlListener;
import org.anddev.andengine.engine.handler.physics.PhysicsHandler;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXLayer;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXProperties;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTile;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTileProperty;
import org.anddev.andengine.entity.layer.tiled.tmx.TMXTiledMap;
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

	private int iStatus;

	private Texture mTexture;
	private TiledTextureRegion mTextureRegion;

	// 2 TextureRegion quản lý 2 hình onscreen_control_base,
	// onscreen_control_knob
	private Texture mOnScreenControlTexture;
	private TextureRegion mOnScreenControlBaseTextureRegion;
	private TextureRegion mOnScreenControlKnobTextureRegion;
	
	// map
	private TMXTiledMap mTmxTiledMap;
	private TMXLayer mObjetcTMXLayer;
	private String szMapName = "map1.tmx";

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
				this.mTexture, this, "tank.png", 0, 0, 8, 1); // 8 cột, 1 dòng

		this.mEngine.getTextureManager().loadTexture(mTexture);

		// control
		this.mOnScreenControlTexture = new Texture(256, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.mOnScreenControlBaseTextureRegion = TextureRegionFactory
				.createFromAsset(this.mOnScreenControlTexture, this,
						"onscreen_control_base.png", 0, 0);

		Texture mOnScreenControlTexture1 = new Texture(256, 128,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.mOnScreenControlKnobTextureRegion = TextureRegionFactory
				.createFromAsset(mOnScreenControlTexture1, this,
						"onscreen_control_knob.png", 128, 0);

		// tao hai cái Texture để chứa không bị lỗi ảnh khi load
		this.mEngine.getTextureManager().loadTextures(mOnScreenControlTexture,
				mOnScreenControlTexture1);
	}
	
	public void loadMap(Scene scene)
	{
		// khởi tao mạp
		mTmxTiledMap = TaiBanDo.getTMXTiledMap(scene, this.mEngine, this, szMapName, this);
		
		ArrayList<TMXLayer> mapLayers = mTmxTiledMap.getTMXLayers();
		for (TMXLayer layer : mapLayers)
		{
			
			if (layer.getName().equals("vatcan"))
			{
				mObjetcTMXLayer = layer;
			}
			scene.attachChild(layer);
		}		
	}	

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene(2);
		scene.setBackground(new ColorBackground(0.09804f, 0.6274f, 0.8784f)); // light

		// loal sprites
		final AnimatedSprite sprite = new AnimatedSprite(CAMERA_WIDTH / 2 - 50,
				CAMERA_HEIGHT / 2 + 50, this.mTextureRegion);
		final PhysicsHandler physicHandler = new PhysicsHandler(sprite);

		// sprite.setScale(2);
		sprite.registerUpdateHandler(physicHandler);
		
		
		// khởi tao mạp
		loadMap(scene);
		
		scene.attachChild(sprite);
		
		/*mTmxTiledMap = TaiBanDo.getTMXTiledMap(scene, this.mEngine, this, szMapName, this);
		
		ArrayList<TMXLayer> mapLayers = mTmxTiledMap.getTMXLayers();
		for (TMXLayer layer : mapLayers)
		{
			if (layer.getName().equals("tuong"))
			{
				mObjetcTMXLayer = layer;
			}
			scene.attachChild(layer);
		}		
		*/
		
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

						if (pValueX != 0 || pValueY != 0) { // 他的妈妈！ ~.~'
						
							float pX = 0, pY = 0;
							pX = sprite.getX() + 16;
							pY = sprite.getY() + 16;
							
							if (pValueX > 0) // sang phải
							{
								if (iStatus == 0)// chưa chuyển động => cho nó
													// chuyển động
								{
									sprite.animate(new long[] { 200, 200, 200,
											200, 200, 200, 200 }, 1, 7, true);
									iStatus = 1;

								} else {
									sprite.setRotation(0);
								}
							}

							else if (pValueX < 0) // sang trái
							{
								if (iStatus == 0) {
									sprite.animate(new long[] { 200, 200, 200,
											200, 200, 200, 200 }, 1, 7, true);
									iStatus = 1;
									sprite.setRotation(180);

								} else {
									sprite.setRotation(180);
								}
							}

							else if (pValueY < 0)// lên trên
							{
								if (iStatus == 0) {
									sprite.animate(new long[] { 200, 200, 200,
											200, 200, 200, 200 }, 1, 7, true);
									iStatus = 1;
									sprite.setRotation(270);
								}

								else {
									sprite.setRotation(270);
								}

							}

							else if (pValueY > 0)// xuống dưới
							{
								if (iStatus == 0) {
									sprite.animate(
											new long[] { 200, 200, 200, 
												200, 200, 200, 200}, 1, 7, true);
									iStatus = 1;
									sprite.setRotation(90);
								}

								else {
									sprite.setRotation(90);
								}
							}
							
							// xét 1 bitmap, tại pX. pY
							/*
							TMXTile TMXTile = mObjetcTMXLayer.getTMXTileAt(pX, pY);
							try
							{
								if (TMXTile != null)
								{
									TMXProperties<TMXTileProperty> mTMXProperties = TMXTile.getTMXTileProperties(mTmxTiledMap);
									TMXTileProperty mTmxProperty = mTMXProperties.get(0);
									
								
									if (mTmxProperty.getName().equals("vatcan"))	// <property name="vatcan" value="vatcan"/>
									{
										Log.v("Stop", "chạm phải vật cản rồi!");
										
										// di chuyển bằng control
										physicHandler.setVelocity(pValueX * 0,
												pValueY * 0);
									}
								
								} 
							}
							catch (Exception e)
							{
								Log.v("Running ...", "không có vật cản");
								physicHandler.setVelocity(pValueX * 50, pValueY * 50);
							}							
							*/
							physicHandler.setVelocity(pValueX * 50, pValueY * 50);
						

						} else // ko chuyển động
						{
							sprite.animate(new long[]{200}, new int[]{0}, 10000);
							physicHandler.setVelocity(pValueX * 0, pValueY * 0);
							iStatus = 0;
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