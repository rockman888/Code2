package com.example.flappybird;

import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.ParallaxBackground.ParallaxEntity;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.animator.SlideMenuAnimator;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.TextMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ColorMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import android.opengl.GLES20;

import com.example.flappybird.SceneManager.SceneType;

public class MainMenuScene extends BaseScene implements
		IOnMenuItemClickListener {

	protected MenuScene mMenuScene;
	private MenuScene mSubMenuScene;
	
	protected static final int MENU_PLAY = 0;
	protected static final int MENU_RATE = 1;
	protected static final int MENU_EXTRAS = 2;
	protected static final int MENU_QUIT = 3;    
	
	// submenu
	protected static final int MENU_READ = 4;
	protected static final int MENU_MORE = 5;

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		
		
		switch(pMenuItem.getID())
		{
		case MENU_PLAY:
			
			if (mResourceManager.mMusic.isPlaying())
				mResourceManager.mMusic.pause();
			
			mMenuScene.closeMenuScene();
			mSceneManager.setScene(SceneType.SCENE_GAME);
			return true;
			
			
		case MENU_RATE:
			return true;
			
			
		case MENU_EXTRAS:
			pMenuScene.setChildSceneModal(mSubMenuScene);
			return true;
			
		case MENU_QUIT:
			// End Activity;
			mActivity.finish();
			return true;
			
		case MENU_READ:
			mSubMenuScene.back();
			return true;
						
			
		case MENU_MORE:
			mSubMenuScene.back();
			return true;
			
		default: 
			return false;
		}		
	}

	@Override
	public void createScene() {
		// TODO Auto-generated method stub

	
		AutoParallaxBackground autoPallaxBackground = new AutoParallaxBackground(
				0, 0, 0, 0);
		
		float x = 0;
		float y = SCREEN_HEIGHT - mResourceManager.mParallaxLayerBack.getHeight();		
		autoPallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f,
				new Sprite(x, y, mResourceManager.mParallaxLayerBack, mVertexBufferObjectManager)));
		
		x = 0;
		y = SCREEN_HEIGHT - mResourceManager.mParallaxLayerFront.getHeight();	
		autoPallaxBackground.attachParallaxEntity(new ParallaxEntity(0.0f,
				new Sprite(x, y, mResourceManager.mParallaxLayerFront, mVertexBufferObjectManager)));

		
		// name
		Text nameText = new Text(0, 0, mResourceManager.mFont2, "Flappy Chip", new TextOptions(HorizontalAlign.LEFT), mVertexBufferObjectManager);
		x = SCREEN_WIDTH - nameText.getWidth();
		y = 75;		
		nameText.setPosition(x/2f, 75);
		attachChild(nameText);
		    
	    // bird
		x = (SCREEN_WIDTH - mResourceManager.mBirdTextureRegion.getWidth() ) /2;
		y = nameText.getY() + nameText.getHeight() + 25;
		TiledSprite bird = new TiledSprite(x, y, mResourceManager.mBirdTextureRegion, mVertexBufferObjectManager);
        bird.setRotation(-15);
	    attachChild(bird);
	         
	    
	    ////
	    autoPallaxBackground = new AutoParallaxBackground(0, 0, 0, 10);
	    x = 0;
	    y = SCREEN_HEIGHT - mResourceManager.mParallaxLayerBack.getHeight();
	    autoPallaxBackground.attachParallaxEntity(new ParallaxEntity(-5.0f, new Sprite(x, y, mResourceManager.mParallaxLayerBack , mVertexBufferObjectManager)));

	    x = 0;
	    y = SCREEN_HEIGHT - mResourceManager.mParallaxLayerFront.getHeight();
	    autoPallaxBackground.attachParallaxEntity(new ParallaxEntity(-10.0f, new Sprite(x, y, mResourceManager.mParallaxLayerFront, mVertexBufferObjectManager)));
	    setBackground(autoPallaxBackground);
	    
	    
	    mMenuScene = createMenuScene();
	    mSubMenuScene = createSubMenuScene();
	         
	    /* Attach the menu. */
	    this.setChildScene(mMenuScene, false, true, true);
	         
	    if (!mResourceManager.mMusic.isPlaying()) {
	    	mResourceManager.mMusic.play();
	    }
		
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub

		if (mMenuScene.hasChildScene())
			mSubMenuScene.back();
		else
			mActivity.finish();
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub

	}
	
		
	protected MenuScene createMenuScene() {
	    //TODO implement
		
		final MenuScene menuScene = new MenuScene(mCamera);
		
		TextMenuItem pMenuItem;
		Color pSelectedColor, pUnselectedColor;
		
		pSelectedColor = new Color(1,1,1);
		pUnselectedColor = new Color(0.0f, 0.2f, 0.4f);		
		pMenuItem = new TextMenuItem(MENU_PLAY, mResourceManager.mFont3, "Play", mVertexBufferObjectManager);

		final IMenuItem playMenuItem = new ColorMenuItemDecorator(pMenuItem, pSelectedColor, pUnselectedColor);
		playMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
	    menuScene.addMenuItem(playMenuItem);
	 
	    pMenuItem = new TextMenuItem(MENU_RATE, mResourceManager.mFont3, "Rate", mVertexBufferObjectManager);
	    final IMenuItem rateMenuItem = new ColorMenuItemDecorator(pMenuItem, pSelectedColor, pUnselectedColor);	    
	    rateMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
	    menuScene.addMenuItem(rateMenuItem);
	     
	    pMenuItem = new TextMenuItem(MENU_EXTRAS, mResourceManager.mFont3, "Extras", mVertexBufferObjectManager);
	    final IMenuItem extrasMenuItem = new ColorMenuItemDecorator(pMenuItem, pSelectedColor, pUnselectedColor);
	    extrasMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
	    menuScene.addMenuItem(extrasMenuItem);
	 
	    pMenuItem = new TextMenuItem(MENU_QUIT, mResourceManager.mFont3, "Quit", mVertexBufferObjectManager);
	    final IMenuItem quitMenuItem = new ColorMenuItemDecorator(pMenuItem, pSelectedColor, pUnselectedColor);
	    quitMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
	    menuScene.addMenuItem(quitMenuItem);   
	    menuScene.buildAnimations();		 
	    menuScene.setBackgroundEnabled(false);	 
	    menuScene.setOnMenuItemClickListener(this);	 
	    
	   
	    return menuScene;
	}
	     
	protected MenuScene createSubMenuScene() {
	        //TODO implement
		 // submenu	 
	    final MenuScene subMenuScene = new MenuScene(mCamera);
	    TextMenuItem pMenuItem;
	    Color pSelectedColor;
	    Color pUnselectedColor;	    
	    
	    pSelectedColor = new Color(1,1,1);
		pUnselectedColor = new Color(0.0f, 0.2f, 0.4f);		
	    
	    pMenuItem = new TextMenuItem(MENU_MORE, mResourceManager.mFont3, "More Apps", mVertexBufferObjectManager);	    
	    final IMenuItem moreMenuItem = new ColorMenuItemDecorator(pMenuItem, pSelectedColor, pUnselectedColor);
	    moreMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
	    subMenuScene.addMenuItem(moreMenuItem);
	 
	    pMenuItem = new TextMenuItem(MENU_READ, mResourceManager.mFont3, "Read Tutorial", mVertexBufferObjectManager);
	    final IMenuItem readMenuItem = new ColorMenuItemDecorator(pMenuItem, pSelectedColor, pUnselectedColor);
	    readMenuItem.setBlendFunction(GLES20.GL_SRC_ALPHA, GLES20.GL_ONE_MINUS_SRC_ALPHA);
	    subMenuScene.addMenuItem(readMenuItem);
	     
	    subMenuScene.setMenuAnimator(new SlideMenuAnimator());
	    subMenuScene.buildAnimations();  
	    subMenuScene.setBackgroundEnabled(false);
	    subMenuScene.setOnMenuItemClickListener(this);
	    
	    return subMenuScene;
	}

}
