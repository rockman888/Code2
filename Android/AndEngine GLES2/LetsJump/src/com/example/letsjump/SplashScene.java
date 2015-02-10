package com.example.letsjump;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.Sprite;
import org.andengine.opengl.util.GLState;

import com.example.letsjump.SceneManager.SceneType;

public class SplashScene extends BaseScene{
	
	private Sprite mSprite;
	
	@Override
	public void createScene() {
		// TODO Auto-generated method stub
				
		mSprite = new Sprite(0, 0, mResourceManger.mSplashRegion, mVertexBufferObjectManager)
		{
			@Override
			protected void preDraw(GLState pGLState, Camera pCamera) {
				// TODO Auto-generated method stub
				super.preDraw(pGLState, pCamera);
				pGLState.enableDither();
			}			
		};
		
		mSprite.setScale(1.0f);
		
		//float x = mResourceManger.mActivity.WIDTH / 2;
		//float y = mResourceManger.mActivity.HEIGHT / 2;		
		mSprite.setPosition(400, 240); 
		
		attachChild(mSprite);		
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
		mSprite.detachSelf();
		mSprite.dispose();
		this.detachSelf();
		this.dispose();
	}
	

}
