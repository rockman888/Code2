package com.example.flappybird;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.example.flappybird.SceneManager.SceneType;


public abstract class BaseScene extends Scene{
	
	protected final int SCREEN_WIDTH = MainActivity.CAMERA_WIDTH;
	protected final int SCREEN_HEIGHT = MainActivity.CAMERA_HEIGHT;
	
	
	protected MainActivity mActivity;
	protected org.andengine.engine.Engine mEngine;
	protected Camera mCamera;
	
	protected VertexBufferObjectManager mVertexBufferObjectManager;
	protected ResourceManager mResourceManager;
	protected SceneManager mSceneManager;
	
	public BaseScene()
	{
		mResourceManager = ResourceManager.getInstance();
		mActivity = mResourceManager.mActivity;
		mVertexBufferObjectManager = mActivity.getVertexBufferObjectManager();
		mEngine = mActivity.getEngine();
		mCamera = mEngine.getCamera();
		mSceneManager = SceneManager.getInstance();
		createScene();		
	}
	

	public abstract void createScene();
	public abstract void onBackKeyPressed();
	public abstract SceneType getSceneType();
	public abstract void disposeScene();

}
