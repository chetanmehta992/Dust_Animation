package com.mehta.demo.Dust;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

public class PopAnimator extends ValueAnimator {

	private static float PARTICLE_MOVE_FACTOR ;
	private boolean mIsFollowedUp = false;
	private final Paint mPaint;
	private final Rect mBound;
	private final Particle[][] mParticles;
	private final Bitmap mBitmap;
	private final View mContainer;
	float[][] startX;
	float[][] startY;
	float randSpeed = 1;
	float radius = 2.5f;


	public PopAnimator (View container, Bitmap bitmap, Rect bound) {
		mPaint = new Paint();
		mBound = new Rect(bound);
		int bitMapWidth = bitmap.getWidth();
		int bitMapHeight = bitmap.getHeight();
		int noOfParticlesX = 47;
		int noOfParticlesY = 47;
		PARTICLE_MOVE_FACTOR = (bitMapWidth*radius/noOfParticlesX + bitMapHeight*radius/noOfParticlesY);
		mParticles = new Particle[noOfParticlesY][noOfParticlesX];
		float defaultRadius = ((bitMapWidth / (2f * noOfParticlesX)) + (bitMapHeight / (2f * noOfParticlesY))) / 2f;
		for(int i=0;i<noOfParticlesY;i++) {
			for(int j=0;j<noOfParticlesX;j++) {
				mParticles[i][j]
					= generateParticle( bitmap.getPixel((j*bitMapWidth/noOfParticlesX)
						, (i*bitMapHeight/noOfParticlesY))
						,mBound.left+(int)(j* defaultRadius *2)
						, mBound.top+(int)(i* defaultRadius *2));
			}
		}
		mBitmap = bitmap;
		mContainer = container;
		setFloatValues(0f,1f);
	}

	public PopAnimator (View container, Bitmap bitmap, Rect bound, Particle[][] particles) {
		mPaint = new Paint();
		mBound = new Rect(bound);
		mParticles = particles;
		startX = new float[mParticles.length][mParticles[0].length];
		startY = new float[mParticles.length][mParticles[0].length];
		mBitmap = bitmap;
		mContainer = container;
		mIsFollowedUp = true;
		setFloatValues(0f,1f);
	}

	public Particle[][] getParticleValues () {
		return mParticles;
	}

	private Particle generateParticle (int color, int initialX, int initialY) {
		Particle particle = new Particle();
		particle.color = color;
		particle.initialX = initialX;
		particle.initialY = initialY;
		particle.x = initialX;
		particle.y = initialY;
		particle.randSpeed = randSpeed;
		particle.radius = radius;
		return particle;
	}

	public void draw (Canvas canvas) {

		if(!isStarted()) {
			return;
		}

		for (int i = 0; i < mParticles.length; i++) {
			for (int j = 0; j < mParticles[i].length; j++) {
				if (mIsFollowedUp) {
					startX[i][j] = mParticles[i][j].x;
					startY[i][j] = mParticles[i][j].y;
					mParticles[i][j].followUp( mBound, mBitmap, PARTICLE_MOVE_FACTOR, startX[i][j], startY[i][j]);
				}
				else {
					   mParticles[i][j].advance(mBound, mBitmap, PARTICLE_MOVE_FACTOR);

				}
				mPaint.setColor(mParticles[i][j].color);
				mPaint.setAlpha((int) (Color.alpha(mParticles[i][j].color) * mParticles[i][j].alpha));
				canvas.drawCircle(mParticles[i][j].x, mParticles[i][j].y, mParticles[i][j].radius, mPaint);
			}
		}
		mContainer.invalidate();
	}

	@Override
	public void start() {
		super.start();
		mContainer.invalidate(mBound);
	}

}
