package com.example.jump2.base;



import org.andengine.engine.Engine;
import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.app.Activity;

// import ResourceManager / SceneType;
import com.example.jump2.manager.ResourceManager;
import com.example.jump2.manager.SceneManager.SceneType;

public abstract class BaseScene extends Scene{

	// ----------------------------------------
	// VARIABLES
	// ----------------------------------------
	
	protected Engine engine;
	protected Activity activity;
	protected ResourceManager resourcesManager;
	protected VertexBufferObjectManager vbom;
	protected Camera camera;
	
	// ------------------------------------------
	// CONSTRUCTOR
	// ------------------------------------------
	public BaseScene()
	{
		this.resourcesManager  = ResourceManager.getInstance();
		this.engine = resourcesManager.engine;
		this.activity = resourcesManager.activity;
		this.vbom = resourcesManager.vbom;
		this.camera = resourcesManager.camera;
		createScene();		
	}
	
	// ------------------------------------------
	// ABSTRACTION
	// ------------------------------------------
	public abstract void createScene();
	
	public abstract void onBackKeyPressed();
	
	public abstract SceneType getSceneType();
		
	public abstract void disposeScene();
}
