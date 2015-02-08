package com.example.letsjump;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;


import android.util.Log;

import com.example.letsjump.SceneManager.SceneType;

public class LoadingScene extends BaseScene{

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		setBackground(new Background(org.andengine.util.adt.color.Color.RED));
		attachChild(new Text(0,0, mResourceManger.mFont, "Loading ...", mVertexBufferObjectManager));
		
		Log.d("Loading -", "loading scene");
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		return;
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_LOADING;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
		
	}
	
}
