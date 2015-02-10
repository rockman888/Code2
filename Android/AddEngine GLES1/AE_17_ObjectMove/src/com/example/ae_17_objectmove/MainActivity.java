package com.example.ae_17_objectmove;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.modifier.MoveModifier;
import org.anddev.andengine.entity.modifier.MoveXModifier;
import org.anddev.andengine.entity.modifier.MoveYModifier;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.Sprite;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegion;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;

import android.os.Bundle;
import android.app.Activity;
import android.speech.SpeechRecognizer;
import android.view.Menu;

public class MainActivity extends BaseGameActivity {

	private static final int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;
	private Camera mCamera;

	private Texture mTexture;
	private TextureRegion mTextureRegion;

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
		this.mTexture = new Texture(32, 32,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mTextureRegion = TextureRegionFactory.createFromAsset(
				this.mTexture, this, "water.png", 0, 0);

		this.mEngine.getTextureManager().loadTexture(mTexture);

	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene(2);
		scene.setBackground(new ColorBackground(0, 0, 0));

		final Sprite sprite = new Sprite(10, 10, this.mTextureRegion);
		scene.attachChild(sprite);

		// di chuyển xéo		
		 MoveModifier move = new MoveModifier(5, sprite.getX(), CAMERA_WIDTH -
		 sprite.getWidth(), sprite.getY(), CAMERA_HEIGHT - sprite.getY());
		 sprite.registerEntityModifier(move);
		 

		// di chuyển theo chiều ngang X 		 
		 MoveXModifier moveX = new MoveXModifier(2, sprite.getX(), CAMERA_WIDTH - 
	     sprite.getWidth() * 3); 
		 sprite.registerEntityModifier(moveX); 
		 sprite.setScale(3);
		 

		// di chuyển theo chiều dọc Y
		MoveYModifier moveY = new MoveYModifier(1, sprite.getY(), CAMERA_HEIGHT
				- sprite.getHeight());
		sprite.registerEntityModifier(moveY);

		return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}
}
