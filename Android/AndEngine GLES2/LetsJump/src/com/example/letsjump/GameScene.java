package com.example.letsjump;

import java.io.IOException;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.engine.handler.timer.ITimerCallback;
import org.andengine.engine.handler.timer.TimerHandler;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.AutoParallaxBackground;
import org.andengine.entity.scene.background.Background;
import org.andengine.entity.scene.background.ParallaxBackground;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.input.touch.TouchEvent;
import org.andengine.util.SAXUtils;
import org.andengine.util.adt.align.HorizontalAlign;
import org.andengine.util.adt.color.Color;
import org.andengine.util.level.constants.LevelConstants;

import android.util.Log;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.example.letsjump.LevelCompleteWindow.StarsCount;
import com.example.letsjump.SceneManager.SceneType;

import org.andengine.util.level.EntityLoader;
import org.andengine.util.level.simple.SimpleLevelEntityLoaderData;
import org.andengine.util.level.simple.SimpleLevelLoader;
import org.xml.sax.Attributes;

public class GameScene extends BaseScene implements IOnSceneTouchListener {

	// -------------------------------------------
	// VARIABLES
	// -------------------------------------------

	private HUD mGameHUD; // create new private field for HUD, and method to
							// initialize it.
	private Text mScoreText; // create new field for score text, and initialize
								// it inside createHUD method:
	private int mScore = 0;
	private PhysicsWorld mPhysicsWorld; // create new field for our PhysicsWorld
										// and create method to initialize it.

	private Text mGameOverText;
	private boolean mGameOverDisplayer = false; // and boolean for flag if this
												// message has been displayed
												// already (to prevent from
												// displaying again)

	// -------------------------------------------
	// INITIALIZING LEVEL COMPLETE WINDOW
	// -------------------------------------------
	private LevelCompleteWindow levelCompleteWindow;
	// -------------------------------------------
	// LOAD LEVEL FROM XML FILES
	// -------------------------------------------

	// Now we need to create something which will handle displaying our window,
	// we will create sprite, after touching it with our player,
	// level complete window will be displayed.
	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_LEVEL_COMPLETE = "levelComplete";

	private static final String TAG_ENTITY = "entity";
	private static final String TAG_ENTITY_ATTRIBUTE_X = "x";
	private static final String TAG_ENTITY_ATTRIBUTE_Y = "y";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE = "type";

	private static final String TAG_ENTITY_ATTRIBUTE_TYPE_PLATFORM1 = "platform1";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE_PLATFORM2 = "platform2";
	private static final String TAG_ENTITY_ATTRIBUTE_TYPE_PLATFORM3 = "platform3";

	private static final String TAG_ENTITY_ATTRIBUTE_COIN = "coin";

	private static final Object TAG_ENTITY_ATTRIBUTE_TYPE_PLAYER = "player";

	// -------------------------------------------
	// PLAYER
	// -------------------------------------------
	private Player mPlayer;

	// Okay now when we finally have our player on the scene, lets take care
	// about jump and run functions, as mentioned, our player will
	// conterminously run (automatically) in the right direction, after FIRST
	// touch of the screen, after that, every next touch will make player jump,
	// quite simple I guess. Lest create new boolean, flag to know if it was
	// first touch.
	private boolean mFirstTouch = false;

	// -------------------------------------------
	// LEVEL
	// -------------------------------------------

	// Now I will try to explain what this code does, in registerEntityLoader,

	// 1) we return "mother entity" for all entities loaded from xml file. In
	// another words, entity where all loaded entities are going to be attached
	// to. As you can conclude, we should return our game scene (like we do in
	// the code above)

	// 2) in second registerEntityLoader we actually parse our xml file, we
	// check what type variable equals, if it equals "platform1" we create and
	// return new sprite for our first platform type, we also create body and
	// set user data to "platform1" (we will later need those user data to
	// recognise physical objects inside contact listener) You can ask why we
	// also register physics connector in platform 2 and platform 3, we do it,
	// because as you should conclude from platforms description on the
	// beginning of this article, those two platforms are going to collapse in
	// certain circumstances, that`s why we register it (so sprite will update
	// its positions, following body) We also have code responsible for creating
	// our coin, as you can see, we overridden onManageUpdate method, we will
	// later use it to check if player collide with coin, to execute certain
	// code, such as setting coin invisible and adding score. We also registered
	// loop scale entity modifier to the coin to make it looks better (bit
	// animated)

	private void loadLevel(int levelID) {
		final SimpleLevelLoader levelLoader = new SimpleLevelLoader( mVertexBufferObjectManager);
		final FixtureDef FIXTURE_DEF = PhysicsFactory.createFixtureDef(0, 0.01f, 0.5f);

		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(
						LevelConstants.TAG_LEVEL) {

					@Override
					public IEntity onLoadEntity(String pEntityName,
							IEntity pParent, Attributes pAttributes,
							SimpleLevelEntityLoaderData pEntityLoaderData)
							throws IOException {

						// TODO Auto-generated method stub
						final int width = SAXUtils.getIntAttributeOrThrow(pAttributes,
								LevelConstants.TAG_LEVEL_ATTRIBUTE_WIDTH);
						final int height = SAXUtils.getIntAttributeOrThrow( pAttributes,
								LevelConstants.TAG_LEVEL_ATTRIBUTE_HEIGHT);

						// TODO later we will specify camera BOUNDS and create
						// invisible walls
						// on the beginning and on the end of the level.
						mCamera.setBounds(0, 0, width, height);
						mCamera.setBoundsEnabled(true);

						return GameScene.this;
					}
				});

		levelLoader.registerEntityLoader(new EntityLoader<SimpleLevelEntityLoaderData>(TAG_ENTITY) {
					public IEntity onLoadEntity(
							final String pEntityName,
							final IEntity pParent,
							final Attributes pAttributes,
							final SimpleLevelEntityLoaderData pSimpleLevelEntityLoaderData)
							throws IOException {

						final int x = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_X);
						final int y = SAXUtils.getIntAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_Y);
						final String type = SAXUtils.getAttributeOrThrow(pAttributes, TAG_ENTITY_ATTRIBUTE_TYPE);

						final Sprite levelObject;
						

						// ----------------------- platform 1 --------------------------------

						if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_PLATFORM1)) {

							levelObject = new Sprite(x, y, mResourceManger.mPlatform1Region,
									mVertexBufferObjectManager);
							PhysicsFactory.createBoxBody(mPhysicsWorld, levelObject, BodyType.StaticBody,
									FIXTURE_DEF).setUserData("platform1");
						}

						// ----------------------- platform 2 --------------------------------
						else if (type
								.equals(TAG_ENTITY_ATTRIBUTE_TYPE_PLATFORM2)) {

							levelObject = new Sprite(x, y, mResourceManger.mPlatform2Region,
									mVertexBufferObjectManager);
							final Body body = PhysicsFactory.createBoxBody( mPhysicsWorld, levelObject,
									BodyType.StaticBody, FIXTURE_DEF);
							body.setUserData("platform2");

							mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(
											levelObject, body, true, false));
						}

						// ----------------------- platform 3 --------------------------------
						else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_PLATFORM3)) {

							levelObject = new Sprite(x, y, mResourceManger.mPlatform3Region, mVertexBufferObjectManager);
							final Body body = PhysicsFactory.createBoxBody(mPhysicsWorld, levelObject, BodyType.StaticBody, FIXTURE_DEF);
							body.setUserData("platform3");

							mPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(levelObject, body, true, false));

						// ------------------------------ coin -------------------------------------
						} else if (type.equals(TAG_ENTITY_ATTRIBUTE_COIN)) {
							levelObject = new Sprite(x, y, mResourceManger.mCoinRegion, mVertexBufferObjectManager) {

								@Override
								protected void onManagedUpdate(float pSecondsElapsed) {
									super.onManagedUpdate(pSecondsElapsed);

									if (mPlayer.collidesWith(this)) {

										// This code check on every update, if
										// player collides with coin, and if
										// does, we are adding 10 points to the
										// score, using our previously created
										// method, setting coin to invisible,
										// and setting coin to ignore update (so
										// it will not update any more, since
										// its already collected)
										addToScore(10);
										this.setVisible(false);
										this.setIgnoreUpdate(true);
									}
								}
							};

							// nhấp nháy
							levelObject
									.registerEntityModifier(new LoopEntityModifier(
											new ScaleModifier(1, 1, 1.3f)));
							
						//  ----------------------------- player -------------------------------------
						} else if (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_PLAYER)) {
							mPlayer = new Player(x, y,
									mVertexBufferObjectManager, mCamera,
									mPhysicsWorld) {

								@Override
								public void onDie() {
									// TODO Auto-generated method stub
									if (!mGameOverDisplayer)
										displayGameOverText();
								}
							};

							levelObject = mPlayer;
						}
						
						// ---------------------------- level complete --------------------------------
						else if  (type.equals(TAG_ENTITY_ATTRIBUTE_TYPE_LEVEL_COMPLETE))
						{
							levelObject = new Sprite(x, y, mResourceManger.mCompleteStarsRegion, mVertexBufferObjectManager){

								@Override
								protected void onManagedUpdate( float pSecondsElapsed) {
									// TODO Auto-generated method stub
									super.onManagedUpdate(pSecondsElapsed);									
									
									if (mPlayer.collidesWith(this))
									{
										levelCompleteWindow.display(StarsCount.TWO, GameScene.this, mCamera);
										this.setVisible(false);
										this.setIgnoreUpdate(true);
									}
								}
								
							};
							levelObject.registerEntityModifier(new LoopEntityModifier(new ScaleModifier(1, 1, 1.3f)));
						}						
					
						else {
							throw new IllegalArgumentException();
						}

						levelObject.setCullingEnabled(true);

						return levelObject;
					}
				});

		levelLoader.loadLevelFromAsset(mActivity.getAssets(), "level/" + levelID + ".lvl");
		// levelLoader.loadLevelFromAsset(mActivity.getAssets(), "level/1.lvl");
	}

	// -------------------------------------------
	// AUTO PARALLAX BACKGROUND
	// -------------------------------------------
	
	
	// private AutoParallaxBackground autoParallaxBackground;
	// autoParallaxBackground
	
	
	
	// -------------------------------------------
	// COMMONS
	// -------------------------------------------

	private void createPhysics() {
		// We used FixedStepPhysicsWorld class, this class tries to achieve a
		// specific amount of steps per second (in our case 60) second parameter
		// is our gravity parameter, its up to you to modify it depends on your
		// needs, on the end, we register our physics world as a update handler
		// to our scene.

		mPhysicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -17),
				false);

		// register contact listener for our physics world, inside method
		// responsible for creating our physics world, like this:
		mPhysicsWorld.setContactListener(contactListener());
		registerUpdateHandler(mPhysicsWorld);
	}

	private void addToScore(int i) {
		mScore += i;
		mScoreText.setText("Score: " + mScore);
	}

	private void createGameOverText() {
		mGameOverText = new Text(0, 0, mResourceManger.mFont, "Game Over!",
				mVertexBufferObjectManager);
	}

	private void displayGameOverText() {
		mCamera.setChaseEntity(null);
		mGameOverText.setPosition(mCamera.getCenterX(), mCamera.getCenterY());
		attachChild(mGameOverText);
		mGameOverDisplayer = true;
	}

	private void createHUD() {
		mGameHUD = new HUD();

		// CREATE SCORE TEXT
		mScoreText = new Text(20, 420, mResourceManger.mFont,
				"Score: 0123456789 ", new TextOptions(HorizontalAlign.LEFT),
				mVertexBufferObjectManager);
		mScoreText.setText("Score: 0");
		mScoreText.setAnchorCenter(0, 0);

		mGameHUD.attachChild(mScoreText);
		mCamera.setHUD(mGameHUD);
	}

	private void createBackground() {
		setBackground(new Background(Color.BLACK));
		//mResourceManger.loadBackground();		
		// Sprite s = new Sprite(0,0, mResourceManger.mBackgroundRegion, mVertexBufferObjectManager);		
		// attachChild(s);
		
		//AutoParallaxBackground autobg = new AutoParallaxBackground(0f, 0f, 0f, 10f);
		//autobg.attachParallaxEntity(new ParallaxBackground.ParallaxEntity(-5f, new Sprite(0.0F, 480f - mResourceManger.mBackgroundRegion.getHeight(), mResourceManger.mBackgroundRegion, this.mVertexBufferObjectManager)));	// -5.0F  0: đứng yên)
		//setBackground(autobg);
	}
	
	@Override
	public void createScene() {
		// TODO Auto-generated method stub

		ResourcesManager.getInstance().unloadMenuTextures();

		levelCompleteWindow = new LevelCompleteWindow(
				mVertexBufferObjectManager);

		mEngine.registerUpdateHandler(new TimerHandler(1.0f,
				new ITimerCallback() {

					@Override
					public void onTimePassed(TimerHandler pTimerHandler) {
						// TODO Auto-generated method stub
						mEngine.unregisterUpdateHandler(pTimerHandler);
						createBackground();
						createHUD();
						createPhysics();
						loadLevel(1);
						createGameOverText();

					}
				}));

		// add this line to register this scene touch listener: public boolean
		// onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		setOnSceneTouchListener(this);
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub

		SceneManager.getInstance().setScene(SceneType.SCENE_MENU);
	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub

		mCamera.setHUD(null);
		mCamera.setCenter(400, 240);

		// add code responsible for removing chase entity. Without that, after
		// moving back to the menu scene, we would see black screens, because
		// camera would still follow our player position.

		mCamera.setChaseEntity(null);
	}

	@Override
	public boolean onSceneTouchEvent(Scene pScene, TouchEvent pSceneTouchEvent) {
		// TODO Auto-generated method stub

		// first time touch -> set player running
		if (pSceneTouchEvent.isActionDown()) {
			if (!mFirstTouch) // = false
			{
				mPlayer.setRunning();
				mFirstTouch = true;
			} else // second touch -> set player jump
			{
				mPlayer.jump();
				Log.d("----", "jump");
			}
		}

		return false;
	}

	private ContactListener contactListener() {
		ContactListener contactListener = new ContactListener() {

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {
				// TODO Auto-generated method stub

			}

			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
				// TODO Auto-generated method stub

			}

			@Override
			public void endContact(Contact contact) {
				// TODO Auto-generated method stub

				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();

				if (x1.getBody().getUserData() != null
						&& x2.getBody().getUserData() != null)
					if (x2.getBody().getUserData().equals("player"))
						mPlayer.decreaseFootContact();
				
				Log.d(" ---- END CONTACT ----", " ... ");
			}

			@Override
			public void beginContact(Contact contact) {
				// TODO Auto-generated method stub
				final Fixture x1 = contact.getFixtureA();
				final Fixture x2 = contact.getFixtureB();

				if (x1.getBody().getUserData() != null
						&& x2.getBody().getUserData() != null)
					if (x2.getBody().getUserData().equals("player"))
					{
						mPlayer.decreaseFootContact();
						Log.d(" ---- BEGIN CONTACT ----", " player ");						
					}

				// Platform - falling immediately.
				// What it does? It simply changes our platform3 body type to
				// Dynamic, which means it will be also affected by gravity,
				// which means will fall down.
				if (x1.getBody().getUserData().equals("platform3")
						&& x2.getBody().getUserData().equals("player"))
				{
					x1.getBody().setType(BodyType.DynamicBody);
					Log.d(" ---- BEGIN CONTACT ----", " player collish with platform3 ");
				}

				// Platform - falling with delay.
				// Lets make similar feature for our next platform (platform2)
				// this time, lets use little delay before starting falling
				// down. To do it, we will use simple timer handler.
				if (x1.getBody().getUserData().equals("platform2")
						&& x2.getBody().getUserData().equals("player")) 
				{
				
					Log.d(" ---- BEGIN CONTACT ----", " player collish with platform2 ");
					
					mEngine.registerUpdateHandler(new TimerHandler(0.2f,
							new ITimerCallback() {

								@Override
								public void onTimePassed(
										TimerHandler pTimerHandler) {
									// TODO Auto-generated method stub
									pTimerHandler.reset();
									mEngine.unregisterUpdateHandler(pTimerHandler);
									x1.getBody().setType(BodyType.DynamicBody);

								}
							}));
				}

			}
		};

		return contactListener;
	}
}
