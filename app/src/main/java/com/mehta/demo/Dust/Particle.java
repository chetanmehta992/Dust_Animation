package com.mehta.demo.Dust;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Particle {
	int color;
	float radius;
	float randSpeed;
	float initialX;
	float initialY;
	float x;
	float y;
	float alpha = 1f;

	public void advance (Rect bound, Bitmap bitmap, float moveFactor) {
		x = x+randSpeed*((initialX - (bound.left+bitmap.getWidth()/2f))/(bitmap.getWidth()/2f))*(moveFactor);
		y = y+randSpeed*((initialY - (bound.top+bitmap.getHeight()/2f))/(bitmap.getHeight()/2f))*(moveFactor);
	}

	public void followUp (Rect bound, Bitmap bitmap, float moveFactor, float startX, float startY) {
		if(startX>initialX && x>initialX || startX<initialX && x<initialX)
			x = x-randSpeed*((initialX - (bound.left+bitmap.getWidth()/2f))/(bitmap.getWidth()/2f))*(moveFactor);
		if(startY>initialY && y>initialY || startY<initialY && y<initialY)
			y = y-randSpeed*((initialY - (bound.top+bitmap.getHeight()/2f))/(bitmap.getHeight()/2f))*(moveFactor);
	}
}