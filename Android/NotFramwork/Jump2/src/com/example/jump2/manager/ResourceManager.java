package com.example.jump2.manager;

import com.example.jump2.MainActivity;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
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
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.debug.Debug;


import android.graphics.Color;


public class ResourceManager {

	// ----------------------------------
	// VARIABLES
	// ----------------------------------


	private static final ResourceManager INSTANCE = new ResourceManager();
	public Engine engine;
	public MainActivity activity;
	public Camera camera;
	public VertexBufferObjectManager vbom;
	public Font font;

	//---------------------------------------------
	// TEXTURES & TEXTURE REGIONS
	//---------------------------------------------
	public ITextureRegion menu_Background_region;
	public ITextureRegion play_region;
	public ITextureRegion options_region;

	private BuildableBitmapTextureAtlas menuTextureAtlas;

	// splash
	public ITextureRegion splash_region;
	private BitmapTextureAtlas splashTextureAtlas;






	// -----------------------------------
	// CLASS LOGIC
	// -----------------------------------

	

	public void loadMenuResources() {
		loadMenuGraphics();
		loadMenuAudio();
		loadMenuFonts();
	}

	private void loadMenuGraphics() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/menu/");

		menuTextureAtlas = new BuildableBitmapTextureAtlas(
				activity.getTextureManager(), 1024, 1024,
				TextureOptions.BILINEAR);
		menu_Background_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(menuTextureAtlas, activity,
						"menu_background.png");
		play_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				menuTextureAtlas, activity, "play.png");
		options_region = BitmapTextureAtlasTextureRegionFactory
				.createFromAsset(menuTextureAtlas, activity, "options.png");

		try {
			this.menuTextureAtlas
					.build(new BlackPawnTextureAtlasBuilder<IBitmapTextureAtlasSource, BitmapTextureAtlas>(
							0, 1, 0));
			this.menuTextureAtlas.load();

		} catch (final TextureAtlasBuilderException e) {
			Debug.e(e);
		}
	}
	public void loadMenuFonts() {
		FontFactory.setAssetBasePath("font/");
		final ITexture mainFontTexture = new BuildableBitmapTextureAtlas(
				activity.getTextureManager(), 256, 256,
				TextureOptions.BILINEAR_PREMULTIPLYALPHA);

		font = FontFactory.createStrokeFromAsset(activity.getFontManager(),
				mainFontTexture, activity.getAssets(), "HARNGTON.TTF", 50,
				true, Color.WHITE, 2, Color.BLACK);
		font.load();
	}



	private void loadMenuAudio() {

	}

	private void loadGameFonts() {

	}

	private void loadGameAudio() {

	}

	public void loadSplashScreen() {
		BitmapTextureAtlasTextureRegionFactory.setAssetBasePath("gfx/");
		splashTextureAtlas = new BitmapTextureAtlas(
				activity.getTextureManager(), 256, 256, TextureOptions.BILINEAR);
		splash_region = BitmapTextureAtlasTextureRegionFactory.createFromAsset(
				splashTextureAtlas, activity, "splash.png", 0, 0);

		splashTextureAtlas.load();
	}

	/*
	 * also lets prepare the method responsible for unloading our splash texture
	 * (which will be executed after completing the process of loading game
	 * resources and switching from the splash scene to the menu scene) Do it
	 * inside the previously created unloadSplashScreen()
	 */

	public void unloadSplashScreen() {
		splashTextureAtlas.unload();
		splash_region = null;
	}

	public void unloadMenuTextures() {
		menuTextureAtlas.unload();
	}

	public void loadMenuTextures() {
		menuTextureAtlas.load();
	}
	/*
	 * we use this method at beginning of game loading, to prepare Resources
	 * Manager properly, setting all needed parameters, so we can latter access
	 * them from different classes (eg. scenes)
	 */
	public static void prepareManager(Engine engine, MainActivity activity,
			Camera camera, VertexBufferObjectManager vbom) {

		getInstance().engine = engine;
		getInstance().activity = activity;
		getInstance().camera = camera;
		getInstance().vbom = vbom;
	}

	public static ResourceManager getInstance() {
		// TODO Auto-generated method stub
		return INSTANCE;
	}


}
