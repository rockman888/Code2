package com.example.jump2.scene;

import org.andengine.engine.camera.hud.HUD;
import org.andengine.entity.modifier.IEntityModifier;
import org.andengine.entity.scene.background.Background;

import org.andengine.entity.text.Text;
import org.andengine.entity.text.TextOptions;
import org.andengine.extension.physics.box2d.FixedStepPhysicsWorld;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.util.HorizontalAlign;
import org.andengine.util.color.Color;

import com.badlogic.gdx.math.Vector2;


import com.example.jump2.base.BaseScene;
import com.example.jump2.manager.SceneManager.SceneType;

/*
 * And finally here we are, in this article, we will create most important
 * part of our application, which is game-play scene
 */

public class GameScene extends BaseScene {

	private int score = 0;
	private HUD gameHUD; // which will be used for displaying game controller
							// and score text.
	private Text scoreText; // Create ScoreText;

	// physics - We will need physics in our game, so lets create it
	private PhysicsWorld physicsWorld;

	/*
	 * We used FixedStepPhysicsWorld class, this class tries to achieve a
	 * specific amount of steps per second (in our case 60) second parameter is
	 * our gravity parameter, its up to you to modify it depends on your needs,
	 * on the end, we register our physics world as a update handler to our
	 * scene.
	 */

	private void createPhysics() {
		physicsWorld = new FixedStepPhysicsWorld(60, new Vector2(0, -17), false);
		registerEntityModifier((IEntityModifier) physicsWorld);
	}

	private void addToScore(int i) {
		score += i;
		scoreText.setText("Score: " + score);
	}

	private void createHUD() {
		gameHUD = new HUD();

		// create score Text;
		scoreText = new Text(20, 420, resourcesManager.font,
				"Score: 0123456789", new TextOptions(HorizontalAlign.LEFT),
				vbom);
		// scoreText.setAnchorCenter(0,0);
		scoreText.setText("Score: 0");
		gameHUD.attachChild(scoreText);

		camera.setHUD(gameHUD);
	}

	/*
	 * In this example, we will use something simple as a background, we will
	 * use simple colour, you can also use your own image or create more
	 * advanced backgrounds such as parallax etc.
	 */
	private void createBackground() {
		setBackground(new Background(Color.BLUE));
	}

	@Override
	public void createScene() {
		// TODO Auto-generated method stub
		
		createBackground();
		createHUD();
		createPhysics();
	}

	@Override
	public void onBackKeyPressed() {
		// TODO Auto-generated method stub

	}

	@Override
	public SceneType getSceneType() {
		// TODO Auto-generated method stub
		return SceneType.SCENE_GAME;
	}

	@Override
	public void disposeScene() {
		// TODO Auto-generated method stub

	}

}
