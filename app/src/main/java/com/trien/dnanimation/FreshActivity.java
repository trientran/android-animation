
package com.trien.dnanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class FreshActivity extends AppCompatActivity {

    ImageView imageView1;
    ImageView imageView2;
    ImageView imageView3;
    ImageView imageView4;
    ImageView imageView5;
    ImageView imageView6;

    ConstraintLayout progressBar;
    ConstraintLayout fragment1;
    ConstraintLayout fragment2;

    private int mShortAnimationDuration = 200;
    float originalAlpha = 0f;
    float destinationAlpha = 1f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fresh);

        progressBar = findViewById(R.id.progressBar);
        fragment1 = findViewById(R.id.fragment1);
        fragment2 = findViewById(R.id.fragment2);
        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);
        imageView3 = findViewById(R.id.imageView3);
        imageView4 = findViewById(R.id.imageView4);
        imageView5 = findViewById(R.id.imageView5);
        imageView6 = findViewById(R.id.imageView6);

        repeatProgressBarInfinitely();

        transitionLayout();
    }

    // this method is just to mimic fade-in fade-out transition between fragments/layouts
    private void transitionLayout() {

        // Set fragment2 (content view) to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        fragment2.setAlpha(0f);

        // Animate fragment1 to 0% opacity. After the animation ends, repeat it for a second time to
        // mimic an app loading data from internet
        // set its visibility to GONE as an optimization step to get rid of the whole progress bar
        fragment1.animate()
                .alpha(1f)
                .setDuration(4000)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);


                        fragment1.animate()
                                .alpha(0f)
                                .setDuration(2000)
                                .setListener(new AnimatorListenerAdapter() {
                            @Override
                            public void onAnimationEnd(Animator animation) {
                                super.onAnimationEnd(animation);
                                // set progressBar visibility to Gone
                                fragment1.setVisibility(View.GONE);
                            }
                        });


                        fragment2.animate()
                                .alpha(1f)
                                .setDuration(2000)
                                .setListener(null);
                    }
                });


    }

    private void repeatProgressBarInfinitely() {
        progressBar.setAlpha(destinationAlpha);
        imageView1.setAlpha(originalAlpha);
        imageView2.setAlpha(originalAlpha);
        imageView3.setAlpha(originalAlpha);
        imageView4.setAlpha(originalAlpha);
        imageView5.setAlpha(originalAlpha);
        imageView6.setAlpha(originalAlpha);


        imageView1.setVisibility(View.INVISIBLE);
        imageView2.setVisibility(View.INVISIBLE);
        imageView3.setVisibility(View.INVISIBLE);
        imageView4.setVisibility(View.INVISIBLE);
        imageView5.setVisibility(View.INVISIBLE);
        imageView6.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        imageView1.animate()
                .alpha(destinationAlpha)
                .setDuration(mShortAnimationDuration)
                .setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        imageView1.setVisibility(View.VISIBLE);
                        imageView2.animate()
                                .alpha(destinationAlpha)
                                .setDuration(mShortAnimationDuration)
                                .setListener(new AnimatorListenerAdapter() {
                                    @Override
                                    public void onAnimationEnd(Animator animation) {
                                        imageView2.setVisibility(View.VISIBLE);
                                        imageView3.animate()
                                                .alpha(destinationAlpha)
                                                .setDuration(mShortAnimationDuration)
                                                .setListener(new AnimatorListenerAdapter() {
                                                    @Override
                                                    public void onAnimationEnd(Animator animation) {
                                                        imageView3.setVisibility(View.VISIBLE);
                                                        imageView4.animate()
                                                                .alpha(destinationAlpha)
                                                                .setDuration(mShortAnimationDuration)
                                                                .setListener(new AnimatorListenerAdapter() {
                                                                    @Override
                                                                    public void onAnimationEnd(Animator animation) {
                                                                        imageView4.setVisibility(View.VISIBLE);
                                                                        imageView5.animate()
                                                                                .alpha(destinationAlpha)
                                                                                .setDuration(mShortAnimationDuration)
                                                                                .setListener(new AnimatorListenerAdapter() {
                                                                                    @Override
                                                                                    public void onAnimationEnd(Animator animation) {
                                                                                        imageView5.setVisibility(View.VISIBLE);
                                                                                        imageView6.animate()
                                                                                                .alpha(destinationAlpha)
                                                                                                .setDuration(mShortAnimationDuration)
                                                                                                .setListener(new AnimatorListenerAdapter() {
                                                                                                    @Override
                                                                                                    public void onAnimationEnd(Animator animation) {
                                                                                                        imageView6.setVisibility(View.VISIBLE);
                                                                                                        progressBar.animate()
                                                                                                                .alpha(destinationAlpha)
                                                                                                                .setDuration(mShortAnimationDuration)
                                                                                                                .setListener(new AnimatorListenerAdapter() {
                                                                                                                    @Override
                                                                                                                    public void onAnimationEnd(Animator animation) {
                                                                                                                        progressBar.setVisibility(View.INVISIBLE);
                                                                                                                        repeatProgressBarInfinitely();
                                                                                                                    }
                                                                                                                });
                                                                                                    }
                                                                                                });
                                                                                    }
                                                                                });
                                                                    }
                                                                });
                                                    }
                                                });
                                    }
                                });
                    }
                });
    }
}
