package com.example.flappybird;

import org.andengine.engine.handler.IUpdateHandler;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.util.FPSCounter;
import org.andengine.entity.util.FPSLogger;
import org.andengine.input.touch.TouchEvent;

import android.net.nsd.NsdManager.RegistrationListener;

import com.example.flappybird.SceneManager.SceneType;

public class GameScene extends BaseScene implements IOnSceneTouchListener{

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		mEngine.registerUpdateHandler(new FPSLogger());
		
		setOnSceneTouchListener(this);
		
		registerUpdateHandler(new IUpdateHandler()
		{

			@Override
			public void onUpdate(float pSecondsElapsed) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
			
		});
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		mSceneManager.setScene(SceneType.SCENE_MENU);		
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}

}
