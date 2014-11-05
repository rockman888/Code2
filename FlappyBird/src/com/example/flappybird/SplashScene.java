package com.example.flappybird;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.opengl.util.GLState;
import org.andengine.util.HorizontalAlign;

import com.example.flappybird.SceneManager.SceneType;

public class SplashScene extends BaseScene {
		
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		
		Sprite splash = new Sprite(0, 0, mResourceManager.mSplashTextureRegion,
				mVertexBufferObjectManager) 	
		{
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}
		};
		
		splash.setScale(1.0f);
		attachChild(splash);

		Text copyrightText = new Text(0, 0, mResourceManager.mFont1,
				"(c) 2011-2014", new TextOptions(HorizontalAlign.LEFT),
				mVertexBufferObjectManager);

		copyrightText.setPosition(copyrightText);		
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub
		mActivity.finish();
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_SPLASH;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub
	}
}
