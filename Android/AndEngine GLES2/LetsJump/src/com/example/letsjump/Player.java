package com.example.letsjump;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.extension.physics.box2d.PhysicsConnector;
import org.andengine.extension.physics.box2d.PhysicsFactory;
import org.andengine.extension.physics.box2d.PhysicsWorld;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

//We created code responsible for loading player`s graphic, now lets take
// care about player`s class. Create new class called Player extended by
// AnimatedSprite. You will be forced by eclipse to add constructor. We will
// modify constructor little bit.

public abstract class Player extends AnimatedSprite {

	// -------------------------------------------
	// VARIABLES
	// -------------------------------------------

	private Body mBody;
	private boolean mCanRun = false; // create new boolean to flag if player can
										// jump (as I said, if player will
										// firstly
										// touch screen, we will make player
										// run,
										// and after that every next touch will
										// make
										// player jump) here`s method
										// responsible
										// for creating physics body for player:

	private int mFootContacts = 100;// Currently, please is able to jump infinity
									// times without firstly touching ground,

	// -------------------------------------------
	//
	// -------------------------------------------

	public void increseFootContacts() {
		mFootContacts++;
	}

	public void decreaseFootContact() {
		mFootContacts--;
	}

	public void jump() {
		// modify jump function to check if this value is higher than 0. If it
		// is, we will let player to jump. We will increase this value every
		// time player will touch platform, and decrease every time he will end
		// contact with platform.

		if (mFootContacts < 1)
			return;

		// nhảy lên
		mBody.setLinearVelocity(new Vector2(mBody.getLinearVelocity().x, 12));
	}

	// -------------------------------------------
	// CONTRUCTOR
	// -------------------------------------------

	public Player(float pX, float pY,
			VertexBufferObjectManager pVertexBufferObjectManager,
			Camera camera, PhysicsWorld pPhysicsWorld) {

		// TODO Auto-generated constructor stub
		super(pX, pY, ResourcesManager.getInstance().mPlayerRegion,
				pVertexBufferObjectManager);
		createPhysics(camera, pPhysicsWorld);

		// execute this method inside constructor. Lets also add code setting
		// camera`s chase entity, so camera will follow player`s position.
		camera.setChaseEntity(this);
	}

	// -------------------------------------------
	// ABSTRACT FUNCTION
	// -------------------------------------------

	public abstract void onDie(); // create new abstract method onDie() we will
									// execute it when certain events will
									// occur.

	// -------------------------------------------
	// FUNCTION
	// -------------------------------------------

	// What we just did there? We initialized players body, set user data so we
	// can latter recognise this body in certain events (such as contact between
	// bodies detection), we also set fixed body rotation, so body will not
	// rotate. Latter we registered physics connector (so player`s sprite, will
	// automatically update its position, following body updates) inside we
	// override onUpdate() method, and used camera onUpdate method, it helps to
	// reduce camera "jitering" effect while moving. On the end, we created code
	// checking player`s Y coordinate, if player`s Y is less than 0, onDie will
	// be executed. We also check if canRun boolean returns true, and if yes we
	// set linear velocity on the X axis to stimulate run.

	private void createPhysics(final Camera camera, PhysicsWorld pPhysicsWorld) {
		mBody = PhysicsFactory.createBoxBody(pPhysicsWorld, this,
				BodyType.DynamicBody, PhysicsFactory.createFixtureDef(0, 0, 0));

		mBody.setUserData("player");
		mBody.setFixedRotation(true);

		pPhysicsWorld.registerPhysicsConnector(new PhysicsConnector(this,
				mBody, true, false) // không đóng ngoăc o day, neu ko se ko the
									// goi ham
									// override trong menu Source -> override
				{
					@Override
					public void onUpdate(float pSecondsElapsed) {
						// TODO Auto-generated method stub
						super.onUpdate(pSecondsElapsed);

						camera.onUpdate(0.1f);

						if (getY() <= 0)
							onDie();

						if (mCanRun) {
							
							// setLinearVelocity -> di chuyển theo đường thẳng
							// x: tốc độ
							mBody.setLinearVelocity(new Vector2(5, mBody
									.getLinearVelocity().y));
						}
					}

				});
	}

	public void setRunning() {

		// We defined duration for each player`s tile/frame (in milliseconds)
		// used for animation, we also set player to animate (continuously by
		// setting last parameter to true) animating player`s tiled region from
		// first to last tile (means from 0 to 2, since first value starts from
		// 0 not 1) On the end, we set linear velocity to the body on the X axis
		// to set player running.

		mCanRun = true;
		final long[] PLAYER_ANIMATE = new long[] { 100, 100, 100 };
		animate(PLAYER_ANIMATE, 0, 2, true); // 0 ,1 ,2 sprite (3 hình) theo
												// hàng ngang
	}

}
