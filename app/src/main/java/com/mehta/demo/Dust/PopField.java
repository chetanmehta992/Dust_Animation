package com.mehta.demo.Dust;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import java.util.ArrayList;
import java.util.List;

@SuppressLint("ViewConstructor")
public class PopField extends View {

	private final List<PopAnimator> mPopViews = new ArrayList<>();

	public PopField(Activity context) {
		super(context);
		ViewGroup rootView = (ViewGroup) context.findViewById(Window.ID_ANDROID_CONTENT);
		rootView.addView(this, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		for (PopAnimator popView : mPopViews) {
			popView.draw(canvas);
		}
	}

	private void popView(final Bitmap bitmap, final View view, final Rect bound) {

		final PopAnimator pop = new PopAnimator(this, bitmap, bound);
		final View currentView = this;
		pop.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				PopAnimator popNext = new PopAnimator(currentView, bitmap, bound, pop.getParticleValues());
				popNext.addListener(new AnimatorListenerAdapter() {
					@Override public void onAnimationEnd(Animator animation) {
						view.animate().setDuration(150).scaleX(1f).scaleY(1f).alpha(1f).start();
					}
				});
				mPopViews.add(popNext);
				popNext.start();
				popNext.setDuration(1250);
			}
		});
		mPopViews.add(pop);
		pop.start();
		pop.setDuration(1250);
	}

	public void popView (final View view) {
			Rect r = new Rect();
			view.getGlobalVisibleRect(r);
			int[] location = new int[2];
			getLocationOnScreen(location);
			r.offset(-location[0], -location[1]);
			view.animate().setDuration(0).scaleX(0f).scaleY(0f).alpha(0f).start();
			popView(PopUtils.createBitmapFromView(view), view, r);
	}

}
