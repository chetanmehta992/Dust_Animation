package com.mehta.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import com.mehta.demo.Dust.PopField;


public class MainActivity extends AppCompatActivity {
    public static ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = findViewById(R.id.logo);
        PopField popField = new PopField(this);
        Animation zoomout = AnimationUtils.loadAnimation(this, R.anim.zoom_out);
        zoomout.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                popField.popView(imageView);
            }

            @Override
            public void onAnimationRepeat(Animation animation) { }
        });
        imageView.setAnimation(zoomout);
    }
}