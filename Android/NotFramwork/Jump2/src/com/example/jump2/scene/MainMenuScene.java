package com.example.jump2.scene;

import org.andengine.engine.camera.Camera;


import org.andengine.entity.scene.menu.MenuScene;
import org.andengine.entity.scene.menu.MenuScene.IOnMenuItemClickListener;
import org.andengine.entity.scene.menu.item.IMenuItem;
import org.andengine.entity.scene.menu.item.SpriteMenuItem;
import org.andengine.entity.scene.menu.item.decorator.ScaleMenuItemDecorator;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.example.jump2.base.BaseScene;
import com.example.jump2.manager.SceneManager;
import com.example.jump2.manager.SceneManager.SceneType;

public class MainMenuScene extends BaseScene implements
		IOnMenuItemClickListener {

	// ---------------------------------
	// VARIABLES
	// ---------------------------------

	private MenuScene menuChildScene;
	private final int MENU_PLAY = 0;
	private final int MENU_OPTIONS = 1;

	// ----------------------------------
	// FUNCTION
	// ----------------------------------





	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		createBackground();
		createMenuChildScene();
	}

	/*
	 * inside onBackPressed() put the code responsible for closing our app.
	 */

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		System.exit(0); // exit code
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

	
	// implement the IOnMenuItemClickListener class
	
	@Override
	public boolean onMenuItemClicked(MenuScene pMenuScene, IMenuItem pMenuItem,
			float pMenuItemLocalX, float pMenuItemLocalY) {
		// TODO Auto-generated method stub
		
		
		switch(pMenuItem.getID())
		{
		case MENU_PLAY:
			
			//Load Game Scene
			SceneManager.getInstance().loadGameScene(engine);
			return true;
			
			
		case MENU_OPTIONS:
			return true;
			
		default:
			return false;
		}
	}

	private void createBackground() {
		attachChild(new Sprite(400, 240,
				resourcesManager.menu_Background_region, vbom) {
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				// TODO Auto-generated method stub
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		});
	}
	/*
	 * We are creating two sprite menu items, for our two buttons (play &
	 * options) we also use scale menu item decorator, which makes our menu
	 * items animated (they are being scaled up while touched) it's a really
	 * easy effect, you may easily create your own, it's more than enough for
	 * the purpose of this tutorial though. We are also adding those menu items
	 * to the menu scene, and positioning those menu items, to looks good with
	 * our background.
	 */

	private void createMenuChildScene() {
		menuChildScene = new MenuScene(camera);
		menuChildScene.setPosition(400, 240);

		final IMenuItem playMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_PLAY, resourcesManager.play_region,
						vbom), 1.2f, 1);
		final IMenuItem optionsMenuItem = new ScaleMenuItemDecorator(
				new SpriteMenuItem(MENU_OPTIONS,
						resourcesManager.options_region, vbom), 1.2f, 1);

		
		/////////////////////////////
		menuChildScene.addMenuItem(playMenuItem);
		menuChildScene.addMenuItem(optionsMenuItem);
		/////////////////////////////
		
		menuChildScene.buildAnimations();
		menuChildScene.setBackgroundEnabled(false);

		playMenuItem.setPosition(playMenuItem.getX(), playMenuItem.getY() + 10);
		optionsMenuItem.setPosition(optionsMenuItem.getX(),
				optionsMenuItem.getY() - 110);

		menuChildScene.setOnMenuItemClickListener(this);

		setChildScene(menuChildScene);

	}
}
