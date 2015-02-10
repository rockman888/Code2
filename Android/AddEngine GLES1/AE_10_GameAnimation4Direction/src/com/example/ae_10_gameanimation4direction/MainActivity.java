package com.example.ae_10_gameanimation4direction;

import org.anddev.andengine.engine.Engine;
import org.anddev.andengine.engine.camera.Camera;
import org.anddev.andengine.engine.options.EngineOptions;
import org.anddev.andengine.engine.options.EngineOptions.ScreenOrientation;
import org.anddev.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.anddev.andengine.entity.scene.Scene;
import org.anddev.andengine.entity.scene.background.ColorBackground;
import org.anddev.andengine.entity.sprite.AnimatedSprite;
import org.anddev.andengine.entity.text.Text;
import org.anddev.andengine.entity.text.TickerText;
import org.anddev.andengine.entity.util.FPSLogger;
import org.anddev.andengine.opengl.font.Font;
import org.anddev.andengine.opengl.texture.Texture;
import org.anddev.andengine.opengl.texture.TextureOptions;
import org.anddev.andengine.opengl.texture.region.TextureRegionFactory;
import org.anddev.andengine.opengl.texture.region.TiledTextureRegion;
import org.anddev.andengine.ui.activity.BaseGameActivity;
import org.anddev.andengine.util.HorizontalAlign;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;

public class MainActivity extends BaseGameActivity {

	private final static int CAMERA_WIDTH = 480;
	private static final int CAMERA_HEIGHT = 800;

	private Texture mTexture;
	private TiledTextureRegion mTextureRegion;

	// font chữ
	private Font mFontTicker;
	private Texture mFontTextureTicker;

	@Override
	public Engine onLoadEngine() {
		// TODO Auto-generated method stub
		final Camera camera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);

		// nằm ngang : ScreenOrientation.PORTRAIT
		final EngineOptions engineOptions = new EngineOptions(true,
				ScreenOrientation.PORTRAIT, new RatioResolutionPolicy(
						CAMERA_WIDTH, CAMERA_HEIGHT), camera);
		engineOptions.getTouchOptions().setRunOnUpdateThread(true);

		// khởi tạo biến
		return new Engine(engineOptions);
	}

	@Override
	public void onLoadResources() {
		// TODO Auto-generated method stub

		// font chữ
		this.mFontTextureTicker = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mFontTicker = new Font(this.mFontTextureTicker, Typeface.create(
				Typeface.SANS_SERIF, Typeface.BOLD_ITALIC), 25, true,
				Color.YELLOW);

		this.mEngine.getTextureManager().loadTexture(mFontTextureTicker);
		this.mEngine.getFontManager().loadFont(mFontTicker);

		// load sprites
		TextureRegionFactory.setAssetBasePath("gfx/");
		this.mTexture = new Texture(256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		this.mTextureRegion = TextureRegionFactory.createTiledFromAsset(
				mTexture, this, "move.png", 0, 0, 3, 4); // 3 cột, 4 dòng
		this.mEngine.getTextureManager().loadTexture(mTexture);

	}

	@Override
	public Scene onLoadScene() {
		// TODO Auto-generated method stub
		this.mEngine.registerUpdateHandler(new FPSLogger());
		final Scene scene = new Scene(2);
		scene.setBackground(new ColorBackground(0, 0, 0)); // black scene

		// font chữ
		final Text t = new TickerText(16, 10, this.mFontTicker,
				"AE-10 Succeed!", HorizontalAlign.CENTER, 20);
		scene.attachChild(t);

		// sprites
		final AnimatedSprite s = new AnimatedSprite(250, 250,
				this.mTextureRegion);
		s.setScale(3);
		scene.attachChild(s);

		// diễu hoạt
		s.animate(new long[] { 200, 200, 200, 200, 200, 200, 200, 200, 200,
				200, 200, 200 }, 0, 11, true);

		return scene;
	}

	@Override
	public void onLoadComplete() {
		// TODO Auto-generated method stub

	}

}
