package com.example.letsjump;

import org.andengine.opengl.font.Font;
import org.andengine.opengl.font.FontFactory;
import org.andengine.opengl.texture.ITexture;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.BuildableBitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.source.IBitmapTextureAtlasSource;
import org.andengine.opengl.texture.atlas.buildable.builder.BlackPawnTextureAtlasBuilder;
import org.andengine.opengl.texture.atlas.buildable.builder.ITextureAtlasBuilder.TextureAtlasBuilderException;
import org.andengine.opengl.texture.region.ITextureRegion;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.util.debug.Debug;

import android.graphics.Color;

public class ResourcesManager {

	// -------------------------------------
	// VARIABLES
	// -------------------------------------

	private static final ResourcesManager INSTANCE = new ResourcesManager();
	public MainActivity mActivity;
	public Font mFont;

	// -------------------------------------
	// MENU TEXTURES && MENU TEXTURE REGIONS
	// -------------------------------------

	public ITextureRegion mSplashRegion;
	private BitmapTextureAtlas mSplashTextureAtlas;

	public ITextureRegion mMenuBackGroundRegion;
	public ITextureRegion mPlayRegion;
	public ITextureRegion mOptionRegion;

	private BitmapTextureAtlas mMenuTextureAtlas;
	private BitmapTextureAtlas mButtonPlayTextureAtlas;
	private BitmapTextureAtlas mButtonOptionTextureAtlas;
	
	// -------------------------------------
	// GAME BACKGROUND
	// -------------------------------------
	public BitmapTextureAtlas mBackgroundAtlas;
	
	public ITextureRegion mBackgroundRegion;
	
	// -------------------------------------
	// GAME TEXTURES && GAME TEXTURE REGIONS
	// -------------------------------------

	public BuildableBitmapTextureAtlas mGameTextureAtlas;

	public ITextureRegion mPlatform1Region;
	public ITextureRegion mPlatform2Region;
	public ITextureRegion mPlatform3Region;
	public ITextureRegion mCoinRegion;

	// -------------------------------------
	// PLAYER RESOURCE
	// -------------------------------------
	public ITiledTextureRegion mPlayerRegion;

	// -------------------------------------
	// RATING RESOURCE
	// -------------------------------------
	public ITextureRegion mCompleteWindowRegion;
	public ITiledTextureRegion mCompleteStarsRegion; // ảnh động
	
	// -------------------------------------
	// CLASS LOGIC
	// -------------------------------------
	public void loadMenuFonts() {
		FontFactory.setAssetBasePath("font/");
		ITexture mainFontTexture = new BitmapTextureAtlas(
				mActivity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR);

		mFont = FontFactory.createStrokeFromAsset(mActivity.getFontManager(),
				mainFontTexture, mActivity.getAssets(), "font.ttf", 50, true,
				Color.WHITE, 2, Color.BLACK);
		mFont.load();
	}

	public void loadMenuResources() {
		loadMenuGraphics();
		loadMenuFonts();
		loadMenuAudio();
	}

	public void loadGameResources() {
		
		loadGameGraphics();
		loadGameFonts();
		loadGameAudio();
	}
	
	public void loadBackground()
	{
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
		mBackgroundAtlas = new BitmapTextureAtlas(mActivity.getTextureManager(), 2048, 1024, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mBackgroundRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(mBackgroundAtlas, mActivity, "bg.jpg", 0, 0);
		mBackgroundAtlas.load();
	}

	private void loadMenuGraphics() {

		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");
		mMenuTextureAtlas = new BitmapTextureAtlas(
				mActivity.getTextureManager(), 1024, 512,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mMenuBackGroundRegion = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mMenuTextureAtlas, mActivity,
						"menu_background.png", 0, 0);
		mMenuTextureAtlas.load();

		mButtonPlayTextureAtlas = new BitmapTextureAtlas(
				mActivity.getTextureManager(), 256, 128,
				TextureOptions.BILINEAR);
		mPlayRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mButtonPlayTextureAtlas, mActivity, "play.png", 0, 0);

		mButtonPlayTextureAtlas.load();

		mButtonOptionTextureAtlas = new BitmapTextureAtlas(
				mActivity.getTextureManager(), 256, 128,
				TextureOptions.BILINEAR);
		mOptionRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mButtonOptionTextureAtlas, mActivity, "options.png", 0, 0);
		mButtonOptionTextureAtlas.load();

	}

	private void loadMenuAudio() {

	}

	private void loadGameGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/game/");
		mGameTextureAtlas = new BuildableBitmapTextureAtlas(
				mActivity.getTextureManager(), 1024, 1024,
				TextureOptions.BILINEAR);

		mPlatform1Region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mGameTextureAtlas, mActivity, "platform1.png");
		mPlatform2Region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mGameTextureAtlas, mActivity, "platform2.png");
		mPlatform3Region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(mGameTextureAtlas, mActivity, "platform3.png");

		mCoinRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mGameTextureAtlas, mActivity, "coin.png");
		
		mCompleteWindowRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset
				(mGameTextureAtlas, mActivity, "levelCompleteWindow.png");
		mCompleteStarsRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mGameTextureAtlas,mActivity, "star.png",2, 1);
				

		// player có 3 trạng thái -> dùng createTiledFromAsset
		mPlayerRegion = BitmapTextureAtlasTextureRegionFactory
				.createTiledFromAsset(mGameTextureAtlas, mActivity,
						"player.png", 3, 1);

		try {
			this.mGameTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.mGameTextureAtlas.load();
		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}

	}

	private void loadGameFonts() {

	}

	private void loadGameAudio() {
		
		
	}

	public void loadSplashResource() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		mSplashTextureAtlas = new BitmapTextureAtlas(
				mActivity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		mSplashRegion = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				mSplashTextureAtlas, mActivity, "splash.png", 0, 0);
		mSplashTextureAtlas.load();
	}

	public void unloadSplashResources() {
		mSplashTextureAtlas.unload();
		mSplashRegion = null;
	}

	public void unloadMenuTextures() {
		mMenuTextureAtlas.unload();
		mButtonPlayTextureAtlas.unload();
		mButtonOptionTextureAtlas.unload();
	}

	public void loadMenuTextures() {
		mMenuTextureAtlas.load();
	}

	/**
	 * We use this method at beginning of game loading, to prepare Resources
	 * Manager properly, setting all needed parameters, so we can latter access
	 * them from different classes (eg. scenes)
	 */

	public void prepare(MainActivity activity) {
		INSTANCE.mActivity = activity;
	}

	// -------------------------------------
	// GETTERS AND SETTERS
	// -------------------------------------

	public static ResourcesManager getInstance() {
		return INSTANCE;	// tạo biến instance để lớp khác có thể gọi nó (ko cần new)
	}
}
