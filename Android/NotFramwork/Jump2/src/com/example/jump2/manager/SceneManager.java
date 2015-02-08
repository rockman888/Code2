package com.example.jump2.manager;

import org.andengine.engine.Engine;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.ui.IGameInterface.OnCreateSceneCallback;

import com.example.jump2.base.BaseScene;
import com.example.jump2.scene.GameScene;
import com.example.jump2.scene.LoadingScene;
import com.example.jump2.scene.MainMenuScene;
import com.example.jump2.scene.SplashScene;
/*
 * It will be a really important class in our game, responsible for
 * switching between scenes and keeping track of the currently displayed
 * scene. We will use SINGLETON HOLDER, which means we will be able to use
 * this manger from the global level. It will also have an enum, containing
 * our scene types. We also will create 4 BaseScene objects, for our scenes
 * (splash, loading, menu and game scenes)
 */

public class SceneManager {

	// ------------------------------------------------
	// SCENES
	// ------------------------------------------------

	private BaseScene splashScene;
	private BaseScene menuScene;
	private BaseScene gameScene;
	private BaseScene loadingScene;

	// ------------------------------------------------
	// VARIABLES
	// ------------------------------------------------

	

	private static final SceneManager INSTANCE = new SceneManager();

	private SceneType currentSceneType = SceneType.SCENE_SPLASH;

	private BaseScene currentScene;

	private Engine engine = ResourceManager.getInstance().engine;

	public enum SceneType {
		SCENE_SPLASH, 
		SCENE_MENU, 
		SCENE_GAME, 
		SCENE_LOADING,
	}
	// -------------------------------------------------
	// CLASS LOGIC
	// -------------------------------------------------
	
	public void setScene(BaseScene scene) {
		engine.setScene(scene);
		currentScene = scene;
		currentSceneType = scene.getSceneType();
	}
	
	public void setScene(SceneType sceneType) {
		switch (sceneType) {
		case SCENE_MENU:
			setScene(menuScene);
			break;

		case SCENE_GAME:
			setScene(gameScene);
			break;

		case SCENE_SPLASH:
			setScene(splashScene);
			break;

		case SCENE_LOADING:
			setScene(loadingScene);
			break;

		default:
			break;
		}
	}

	public void createMenuScene()
	{
		ResourceManager.getInstance().loadMenuResources();
		menuScene = new MainMenuScene();
		
		loadingScene = new LoadingScene();
		SceneManager.getInstance().setScene(menuScene);		
		
		// setScene(menuScene);
		disposeSplashScene();
	}
	
	public void createSplashScene(OnCreateSceneCallback pOnCreateSceneCallback)
	{
		ResourceManager.getInstance().loadSplashScreen();
		splashScene = new SplashScene();
		currentScene = splashScene;
		pOnCreateSceneCallback.onCreateSceneFinished(splashScene);
	}
	
	private void disposeSplashScene()
	{
		ResourceManager.getInstance().unloadSplashScreen();
		splashScene.disposeScene();
		splashScene = null;
	}
	
	/*
	 * create method inside SceneManager responsible for displaying loading
	 * scene, while initializing game scene and loading its resources, and
	 * unloading menu texture.
	 */
	public void loadGameScene(final Engine mEngine)
	{		
		setScene(loadingScene);
		ResourceManager.getInstance().unloadMenuTextures();
		mEngine.registerUpdateHandler(new TimerHandler(0.1f, new ITimerCallback() {
			
			@Override
			public void onTimePassed(TimerHandler pTimerHandler) {
				// TODO Auto-generated method stub
				mEngine.unregisterUpdateHandler(pTimerHandler);
				ResourceManager.getInstance().loadMenuResources();
				gameScene = new GameScene();
				setScene(gameScene);				
			}
		}));
	}
	
	
	/*
	 * bên class MainActivity gọi
	 * SceneManager.getInstance().createMenuScene();
	 */
	

	// ---------------------------------------------
	// GETTERS AND SETTERS
	// ---------------------------------------------

	/*
	 * There are also handy getters to receive current scene type and current
	 * scene references, which might be useful in your further game development
	 * stages. There is the most important method setScene(SceneType sceneType)
	 * which takes care about displaying another scene and keeping track of it,
	 * by storing references of the displayed scene and its type.
	 */

	public static SceneManager getInstance() {
		return INSTANCE;
	}
	
	public SceneType getCurrentSceneType()
	{
		return currentSceneType;
	}
	
	public BaseScene getCurrentScene()
	{
		return currentScene;
	}
}
