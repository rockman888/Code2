package com.example.jump2.scene;

import org.andengine.entity.scene.background.Background;
import org.andengine.entity.text.Text;
import org.andengine.util.color.Color;

import com.example.jump2.base.BaseScene;
import com.example.jump2.manager.SceneManager.SceneType;

public class LoadingScene extends BaseScene {

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		setBackground(new Background(Color.WHITE));

		attachChild(new Text(400, 240, resourcesManager.font, "Loading ...",
				vbom));
	}

	/*
	 * As you probably noticed, inside onBackKeyPressed() we just return, which
	 * means we do not perform any actions (because we do not want to do
	 * anything while the loading scene is being displayed and the player
	 * touches the phone`s back button)
	 */

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
