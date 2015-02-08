package com.example.letsjump;

import org.andengine.engine.Engine;
import org.andengine.engine.camera.BoundCamera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import com.example.letsjump.SceneManager.SceneType;

public abstract class BaseScene extends Scene{
	
	// -------------------------------------------
	// VARIABLES
	// -------------------------------------------

	protected MainActivity mActivity;
	protected Engine mEngine;
	protected BoundCamera mCamera;
	
	protected VertexBufferObjectManager mVertexBufferObjectManager;
	protected ResourcesManager mResourceManger;
	protected SceneManager mSceneManager;
	
	// -------------------------------------------
	// CONSTRUCTOR
	// -------------------------------------------
	
	public BaseScene()
	{
		mResourceManger = ResourcesManager.getInstance();
		mActivity = mResourceManger.mActivity;
		mVertexBufferObjectManager = mActivity.getVertexBufferObjectManager();
		mEngine = mActivity.getEngine();				
		mCamera = (BoundCamera) mEngine.getCamera();
		mSceneManager = SceneManager.getInstance();		
		createScene();	
			
	}
	
	// -------------------------------------------
	// ABSTRACTION
	// -------------------------------------------
		
	public abstract void createScene();
	public abstract void onBackKeyPressed();
	public abstract SceneType getSceneType();
	public abstract void disposeScene();
}
