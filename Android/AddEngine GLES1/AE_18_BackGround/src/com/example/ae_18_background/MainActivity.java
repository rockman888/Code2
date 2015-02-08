package com.example.ae_18_background;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.AutoParallaxBackground;
import org.anddev.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

public class MainActivity extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 800;
	private static final int CAMERA_HEIGHT = 480;
	private Camera mCamera;

	private Texture mTexture;
	private Texture mTextureObject;

	private TextureRegion mParallaxLayerBack;
	private TextureRegion mParallaxLayerFront;
	private TextureRegion mParallaxLayerMid;	
	private TextureRegion mObjectsRegion; // ảnh tĩnh
	
	
	private TextureRegion mParallaxLayerNew;
	private TiledTextureRegion mTiledTextureRegion; // ảnh động

	@Override
	public Engine onLoadEngine() {
		// TODO Auto-generated method stub
		this.mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		// nằm ngang : ScreenOrientation.PORTRAIT
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.LANDSCAPE, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), this.mCamera);
		engineOptions.getTouchOptions().setRunOnUpdateThread(true);

		// khởi tạo biến
		return new Engine(engineOptions);
	}

	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub

		TextureRegionFactory.setAssetBasePath("gfx/");

		this.mTexture = new Texture(1024, 1024, TextureOptions.DEFAULT);
		this.mTextureObject = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		this.mParallaxLayerBack = TextureRegionFactory.createFromAsset(
				this.mTexture, this, "parallax_background_layer_back.png", 0,
				188);
		this.mParallaxLayerFront = TextureRegionFactory.createFromAsset(
				this.mTexture, this, "parallax_background_layer_front.png", 0,
				0);
		this.mParallaxLayerMid = TextureRegionFactory.createFromAsset(
				this.mTexture, this, "parallax_background_layer_mid.png", 0,
				669);
		this.mParallaxLayerNew = TextureRegionFactory.createFromAsset(
				this.mTexture, this, "bg2.jpg", 0, 0);

		// tĩnh
		this.mObjectsRegion = TextureRegionFactory.createFromAsset(
				this.mTextureObject, this, "bird.png", 0, 0);

		// động
		this.mTiledTextureRegion = TextureRegionFactory.createTiledFromAsset(
				mTextureObject, this, "bird2.png", 0, 0, 2, 1); // 2 cột, 1
																// dòng, vị trí
																// (0,0)

		this.mEngine.getTextureManager().loadTextures(mTexture, mTextureObject);
	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene();

		final AutoParallaxBackground bg = new AutoParallaxBackground(0, 0, 0, 5);
		/*
		 * bg.attachParallaxEntity(new ParallaxEntity(0.0f, new Sprite(0,
		 * CAMERA_HEIGHT - this.mParallaxLayerBack.getHeight(),
		 * this.mParallaxLayerBack)));
		 * 
		 * bg.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(0, 80,
		 * this.mParallaxLayerMid)) ); //-5.0 -> < 0: hình chạy từ phải sang
		 * trái và ngược lại
		 * 
		 * bg.attachParallaxEntity(new ParallaxEntity(-10f, new Sprite(0,
		 * CAMERA_HEIGHT - this.mParallaxLayerFront.getHeight(),
		 * this.mParallaxLayerFront)) );
		 */

		/*
		 * 
		 * // load sprite tĩnh
		 * 
		 * final Sprite sprite = new Sprite(10, CAMERA_HEIGHT / 5,
		 * this.mObjectsRegion); scene.attachChild(sprite);
		 */

		final AnimatedSprite s = new AnimatedSprite(10, CAMERA_HEIGHT / 5,
				this.mTiledTextureRegion);
		scene.attachChild(s);
		s.animate(new long[] { 200, 200 }, 0, 1, true);

		bg.attachParallaxEntity(new ParallaxEntity(-10f, new Sprite(0,
				CAMERA_HEIGHT - this.mParallaxLayerNew.getHeight(),
				this.mParallaxLayerNew)));

		scene.setBackground(bg);
		return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}
}
