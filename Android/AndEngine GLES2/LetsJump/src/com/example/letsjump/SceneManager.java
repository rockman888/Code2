package com.example.letsjump;


public class SceneManager {

	// -------------------------------------------
	// SCENES
	// -------------------------------------------

	private BaseScene mSplashScene;
	private BaseScene mMenuScene;
	private BaseScene mGameScene;
	private BaseScene mLoadingScene;
	private BaseScene mCurrentScene;

	// -------------------------------------------
	// VARIABLES
	// -------------------------------------------

	private static final SceneManager INSTANCE = new SceneManager();
	
	private SceneType mCurrentSceneType;
	
	public enum SceneType {
		SCENE_SPLASH, SCENE_MENU, SCENE_GAME, SCENE_LOADING
	}

	// -------------------------------------------
	// CLASS LOGIC
	// -------------------------------------------


	// -------------------------------------------
	// SCENE
	// -------------------------------------------
	
	public BaseScene createMenuScene()
	{
		
		mMenuScene = new MainMenuScene();
		return mMenuScene;
	}
	
	public BaseScene createGameScene()
	{
		mGameScene = new GameScene();
		return mGameScene;
	}
	
	public BaseScene createLoadingScene()
	{
		mLoadingScene = new LoadingScene();
		return mLoadingScene;
	}
	
	public BaseScene createSplashScene()
	{
		mSplashScene = new SplashScene();
		return mSplashScene;
	}
	
	public void setScene(SceneType sceneType) {
		switch (sceneType) {
		case SCENE_MENU:
			setScene(createMenuScene());
			break;
		case SCENE_GAME:
			
			setScene(createLoadingScene());			
			setScene(createGameScene());
			
			break;
		case SCENE_SPLASH:
			setScene(createSplashScene());
			break;
		case SCENE_LOADING:
			setScene(createLoadingScene());
			break;
		default:
			break;
		}
	}
	
	//---------------------------------------------
    // GETTERS AND SETTERS
    //---------------------------------------------
	public void setScene(BaseScene scene) {
		ResourcesManager.getInstance().mActivity.getEngine().setScene(scene);
		mCurrentScene = scene;
		mCurrentSceneType = scene.getSceneType();
	}
	
    public static SceneManager getInstance()
    {
        return INSTANCE;
    }
    
    public SceneType getCurrentSceneType()
    {
        return mCurrentSceneType;
    }
    
    public BaseScene getCurrentScene()
    {
        return mCurrentScene;
    }
}
