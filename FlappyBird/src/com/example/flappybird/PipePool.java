package com.example.flappybird;

import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;
import org.andengine.util.adt.pool.GenericPool;
import org.andengine.util.math.MathUtils;

// we need a pool of pipes (of different heights) so that we can recycle
// them as the game progresses. AndEngine provides GenericPool for easily
// creating pool of objects.

public class PipePool extends GenericPool<Pipe> {

	private TiledTextureRegion mPipeTextureRegion;
	private VertexBufferObjectManager mVertexBufferObjectManager;
	private float mGroundY;
	private int mPipeIndex;

	public PipePool(TiledTextureRegion pPipeTextureRegion,
			VertexBufferObjectManager pVertexBufferObjectManager, float pGroundY) {
		super();
		this.mPipeTextureRegion = pPipeTextureRegion;
		this.mVertexBufferObjectManager = pVertexBufferObjectManager;
		this.mGroundY = pGroundY;
	}
	
	
	public int getPipeIndex()
	{
		return mPipeIndex;
	}
	
	
	/*************** override *****************/

	@Override
	protected Pipe onAllocatePoolItem() {
		// TODO Auto-generated method stub

		Pipe p = new Pipe(mPipeTextureRegion, mVertexBufferObjectManager,
				mGroundY, 50 * MathUtils.random(-2, +2));
		return p;
	}

	@Override
	protected void onHandleRecycleItem(Pipe pItem) {
		// TODO Auto-generated method stub
		super.onHandleRecycleItem(pItem);
	}

	@Override
	protected void onHandleObtainItem(Pipe pItem) {
		// TODO Auto-generated method stub
		//
		pItem.reset();
	}

	@Override
	public synchronized Pipe obtainPoolItem() {
		// TODO Auto-generated method stub
		//

		mPipeIndex++;
		return super.obtainPoolItem();
	}
}
