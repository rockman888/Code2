package com.example.letsjump;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.example.letsjump.SceneManager.SceneType;

public class MainMenuScene extends BaseScene implements IOnMenuItemClickListener{

	private MenuScene mMenuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;
	
	private void createMenuChildScene()
	{
		mMenuChildScene = new MenuScene(mCamera);
		mMenuChildScene.setPosition(0, 0);
		
		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_PLAY, mResourceManger.mPlayRegion, mVertexBufferObjectManager), 1.2f, 1);
		final IMenuItem optionMenuItem = new ScaleMenuItemDecorator(new SpriteMenuItem(MENU_OPTIONS, mResourceManger.mOptionRegion, mVertexBufferObjectManager),1.2f, 1);
		mMenuChildScene.addMenuItem(playMenuItem);
		mMenuChildScene.addMenuItem(optionMenuItem);

		mMenuChildScene.buildAnimations();
		mMenuChildScene.setBackgroundEnabled(false);
		
		playMenuItem.setPosition(100, 250);
		optionMenuItem.setPosition(500, 250);
		
		playMenuItem.setAnchorCenter(0f, 0f);
		optionMenuItem.setAnchorCenter(0f, 0f);
		
		mMenuChildScene.setOnMenuItemClickListener(this);
		setChildScene(mMenuChildScene);
	}	
	
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();	// backrgound nền xanh
		createMenuChildScene();	// load play, option button
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_MENU;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
	private void createBackground()
	{		
		Sprite s = new Sprite(0,0, mResourceManger.mMenuBackGroundRegion, mVertexBufferObjectManager)	
		{
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				// TODO Auto-generated method stub
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		
		s.setPosition(0, 0);
		s.setAnchorCenter(0, 0);	// thiết lập hình ở chính giữa		
		attachChild(s);	
	}

	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		
		switch(pMenuItem.getID())
		{
		case MENU_PLAY:
			
			// Load Game Scene
			mSceneManager.setScene(SceneType.SCENE_GAME);	// mở màn hình game
			return true;
			
		case MENU_OPTIONS:
			return true;
			
		default:
			return false;
		}		
	}

	

}
