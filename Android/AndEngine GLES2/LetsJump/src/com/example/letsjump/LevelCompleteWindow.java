package com.example.letsjump;

import org.andengine.engine.camera.Camera;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.Sprite;
import org.andengine.entity.sprite.TiledSprite;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

public class LevelCompleteWindow extends Sprite{
	private TiledSprite star1;
	private TiledSprite star2;
	private TiledSprite star3;
	
	public enum StarsCount{
		ONE, TWO, THREE
	}
	
	
	public LevelCompleteWindow(VertexBufferObjectManager vbom)
	{
		super(0, 0, 650,  400, ResourcesManager.getInstance().mCompleteWindowRegion, vbom );
		attachStars(vbom);		
	}


	private void attachStars(VertexBufferObjectManager vbom) {
		// TODO Auto-generated method stub
		
		star1 = new TiledSprite(150, 150, ResourcesManager.getInstance().mCompleteStarsRegion, vbom);
		star2 = new TiledSprite(325, 150, ResourcesManager.getInstance().mCompleteStarsRegion, vbom);
		star3 = new TiledSprite(500, 150, ResourcesManager.getInstance().mCompleteStarsRegion, vbom);
		
		attachChild(star1);
		attachChild(star2);
		attachChild(star3);
	}	
	
	/*
	 * Change star tile index, depends on stars count 
	 * @param StarsCount
	 * @param Scene
	 * @param Camera
	 */
	public void display(StarsCount count, Scene scene, Camera camera)	
	{
		switch (count)
		{
		case ONE:
			star1.setCurrentTileIndex(0);
			star2.setCurrentTileIndex(1);
			star3.setCurrentTileIndex(1);
			break;
			
		case TWO:
			star1.setCurrentTileIndex(0);
			star2.setCurrentTileIndex(0);
			star3.setCurrentTileIndex(1);
			break;
			
		case THREE:
			star1.setCurrentTileIndex(0);
			star2.setCurrentTileIndex(0);
			star3.setCurrentTileIndex(0);
			break;
		}
		
		// Hide HUD
		camera.getHUD().setVisible(false);
		
		// Disable camera chase entity
		camera.setChaseEntity(null);
		
		// Attach our level complete panel in the middle of camera;
		setPosition(camera.getCenterX(), camera.getCenterY());
		scene.attachChild(this);
	}
	
}
