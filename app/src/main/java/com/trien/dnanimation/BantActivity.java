package com.trien.dnanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class BantActivity extends AppCompatActivity {

    Button zoomBtn;

    ImageView upperBubble;
    ImageView upperAnchor;

    ImageView upperWhiteDot1;
    ImageView upperWhiteDot2;
    ImageView upperWhiteDot3;
    ImageView upperWhiteDot4;

    ImageView lowerBubble;
    ImageView lowerAnchor;

    ImageView lowerWhiteDot1;
    ImageView lowerWhiteDot2;
    ImageView lowerWhiteDot3;
    ImageView lowerWhiteDot4;

    ConstraintLayout fragment1;
    ConstraintLayout fragment2;

    private static final int ZOOM_TIME = 500;
    private static final int FADE_TIME = 200;
    private static final int FADE_TIME_LONG = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bant);

        fragment1 = findViewById(R.id.fragment1);
        fragment2 = findViewById(R.id.fragment2);

        // Retrieve and cache the system's default "short" animation time.
        // ZOOM_TIME = getResources().getInteger(android.R.integer.config_shortAnimTime);

        zoomBtn = findViewById(R.id.zoomBtn);

        // we use R.id.upperAnchor and R.id.lowerAnchor as a fixed sized frame that we want our speech bubbles to scale to
        upperAnchor = findViewById(R.id.upperAnchor);
        lowerAnchor = findViewById(R.id.lowerAnchor);


        upperBubble = findViewById(R.id.upperBubble);
        lowerBubble = findViewById(R.id.lowerBubble);


        upperWhiteDot1 = findViewById(R.id.upperWhiteDot1);
        upperWhiteDot2 = findViewById(R.id.upperWhiteDot2);
        upperWhiteDot3 = findViewById(R.id.upperWhiteDot3);
        upperWhiteDot4 = findViewById(R.id.upperWhiteDot4);
        lowerWhiteDot1 = findViewById(R.id.lowerWhiteDot1);
        lowerWhiteDot2 = findViewById(R.id.lowerWhiteDot2);
        lowerWhiteDot3 = findViewById(R.id.lowerWhiteDot3);
        lowerWhiteDot4 = findViewById(R.id.lowerWhiteDot4);

        upperBubble.setVisibility(View.INVISIBLE);
        lowerBubble.setVisibility(View.INVISIBLE);
        upperAnchor.setVisibility(View.INVISIBLE);
        lowerAnchor.setVisibility(View.INVISIBLE);

        upperWhiteDot1.setAlpha(0f);
        upperWhiteDot2.setAlpha(0f);
        upperWhiteDot3.setAlpha(0f);
        upperWhiteDot4.setAlpha(0f);
        lowerWhiteDot1.setAlpha(0f);
        lowerWhiteDot2.setAlpha(0f);
        lowerWhiteDot3.setAlpha(0f);
        lowerWhiteDot4.setAlpha(0f);

        // Set fragment2 (content view) to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        fragment2.setAlpha(0f);

        zoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animateBubbles();
                transitionLayout();
            }
        });
    }

    private void transitionLayout() {



        /*// Set fragment1 (progress bar) visible and 100% opacity
        fragment1.setVisibility(View.VISIBLE);
        fragment1.setAlpha(1f);*/

        // Animate fragment1 to 0% opacity. After the animation ends, repeat it for a second time to
        // mimic an app loading data from internet
        // set its visibility to GONE as an optimization step to get rid of the whole progress bar
        fragment1.animate()
                .alpha(1f)
                .setDuration(18000)
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
                                        zoomBtn.setVisibility(View.GONE);
                                    }
                                });


                        fragment2.animate()
                                .alpha(1f)
                                .setDuration(2000)
                                .setListener(null);

                    }
                });


    }

    private void animateBubbles() {


        int anchorWidth ;
        int anchorHeight;
        int width0;
        int height0;

        final float scaleX1;
        final float scaleX2;
        final float scaleX3;

        final float scaleY1;
        final float scaleY2;

        anchorWidth = lowerAnchor.getWidth();
        anchorHeight = lowerAnchor.getHeight();

        width0 = lowerBubble.getWidth();
        height0 = lowerBubble.getHeight();

        scaleX1 = anchorWidth/20*17/width0;
        scaleY1 = anchorHeight/16*20/height0;

        scaleX2 = anchorWidth/20*15/width0;
        scaleY2 = anchorHeight/height0;

        scaleX3 = anchorWidth/width0;

        animateLowerBubble(scaleX1, scaleX2, scaleX3, scaleY1, scaleY2);

    }

    private void animateLowerBubble(final float scaleX1, final float scaleX2, final float scaleX3, final float scaleY1, final float scaleY2) {

        lowerBubble.setVisibility(View.VISIBLE);

        // set pivot X and Y for the scaled speech bubbles. pivot values must be calculated basing on
        // the view height/width where 0,0 means the pivot points are top/left of the view;
        // full height and full width means bottom/right of the views...
        lowerBubble.setPivotX(0f);
        lowerBubble.setPivotY(lowerBubble.getHeight());


        // 1st time zoom. values param to input is a multiplication of the original scale.
        // value = 1 means no scaling, 2 means scale to double size...
        AnimatorSet zoom1 = new AnimatorSet();
        zoom1
                .play(ObjectAnimator.ofFloat(lowerBubble, View.SCALE_X, 0, scaleX1, scaleX2))
                .with(ObjectAnimator.ofFloat(lowerBubble, View.SCALE_Y, 0, scaleY1, scaleY2));
        zoom1.setDuration(ZOOM_TIME *2);

        // 2nd time zoom
        AnimatorSet zoom2 = new AnimatorSet();
        zoom2
                .play(ObjectAnimator.ofFloat(lowerBubble, View.SCALE_X, scaleX3 ));
        zoom2.setDuration(ZOOM_TIME);


        /*
         * set up animation for white dots
         */
        ObjectAnimator fadeAnim1 = ObjectAnimator.ofFloat(lowerWhiteDot1, View.ALPHA, 1f);
        fadeAnim1.setDuration(FADE_TIME);

        ObjectAnimator fadeAnim2 = ObjectAnimator.ofFloat(lowerWhiteDot2, View.ALPHA, 1f);
        fadeAnim2.setDuration(FADE_TIME);
        fadeAnim2.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ObjectAnimator.ofFloat(lowerWhiteDot1, View.ALPHA, 0.8f).setDuration(FADE_TIME).start();
            }
        });

        ObjectAnimator fadeAnim3 = ObjectAnimator.ofFloat(lowerWhiteDot3, View.ALPHA, 1f);
        fadeAnim3.setDuration(FADE_TIME);
        fadeAnim3.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ObjectAnimator.ofFloat(lowerWhiteDot1, View.ALPHA, 0.5f).setDuration(FADE_TIME).start();
                ObjectAnimator.ofFloat(lowerWhiteDot2, View.ALPHA, 0.8f).setDuration(FADE_TIME).start();
            }
        });

        ObjectAnimator fadeAnim4 = ObjectAnimator.ofFloat(lowerWhiteDot4, View.ALPHA, 1f);
        fadeAnim4.setDuration(FADE_TIME);
        fadeAnim4.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ObjectAnimator.ofFloat(lowerWhiteDot1, View.ALPHA, 0.2f).setDuration(FADE_TIME).start();
                ObjectAnimator.ofFloat(lowerWhiteDot2, View.ALPHA, 0.5f).setDuration(FADE_TIME).start();
                ObjectAnimator.ofFloat(lowerWhiteDot3, View.ALPHA, 0.8f).setDuration(FADE_TIME).start();
            }
        });

        ObjectAnimator fadeAnim5 = ObjectAnimator.ofFloat(lowerWhiteDot3, View.ALPHA, 1f);
        fadeAnim5.setDuration(FADE_TIME);

        ObjectAnimator fadeAnim6 = ObjectAnimator.ofFloat(lowerWhiteDot2, View.ALPHA, 1f);
        fadeAnim6.setDuration(FADE_TIME);
        fadeAnim6.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                animateUpperBubble(scaleX1, scaleX2, scaleX3, scaleY1, scaleY2);
            }
        });

        ObjectAnimator fadeAnim7 = ObjectAnimator.ofFloat(lowerWhiteDot1, View.ALPHA, 1f);
        fadeAnim7.setDuration(FADE_TIME);

        AnimatorSet whiteDotAnimSet = new AnimatorSet();
        whiteDotAnimSet.playSequentially(fadeAnim1, fadeAnim2, fadeAnim3, fadeAnim4, fadeAnim5, fadeAnim6, fadeAnim7);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(zoom1, zoom2, whiteDotAnimSet);
        animatorSet.start();
    }

    /*
        * set up animation for upperBubble
        */
    private void animateUpperBubble(final float scaleX1, final float scaleX2, final float scaleX3, final float scaleY1, final float scaleY2) {

        upperBubble.setVisibility(View.VISIBLE);

        // set pivot X and Y for the scaled speech bubbles. pivot values must be calculated basing on
        // the view height/width where 0,0 means the pivot points are top/left of the view;
        // full height and full width means bottom/right of the views...
        upperBubble.setPivotX(0f);
        upperBubble.setPivotY(upperBubble.getHeight());


        // 1st time zoom. values param to input is a multiplication of the original scale.
        // value = 1 means no scaling, 2 means scale to double size...
        AnimatorSet zoom1 = new AnimatorSet();
        zoom1
                .play(ObjectAnimator.ofFloat(upperBubble, View.SCALE_X, 0, scaleX1, scaleX2))
                .with(ObjectAnimator.ofFloat(upperBubble, View.SCALE_Y, 0, scaleY1, scaleY2));
        zoom1.setDuration(ZOOM_TIME *2);

        // 2nd time zoom
        AnimatorSet zoom2 = new AnimatorSet();
        zoom2
                .play(ObjectAnimator.ofFloat(upperBubble, View.SCALE_X, scaleX3 ));
        zoom2.setDuration(ZOOM_TIME);


        /*
         * set up animation for white dots
         */
        ObjectAnimator fadeAnim1 = ObjectAnimator.ofFloat(upperWhiteDot1, View.ALPHA, 1f);
        fadeAnim1.setDuration(FADE_TIME);

        ObjectAnimator fadeAnim2 = ObjectAnimator.ofFloat(upperWhiteDot2, View.ALPHA, 1f);
        fadeAnim2.setDuration(FADE_TIME);
        fadeAnim2.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ObjectAnimator.ofFloat(upperWhiteDot1, View.ALPHA, 0.8f).setDuration(FADE_TIME).start();
            }
        });


        ObjectAnimator fadeAnim3 = ObjectAnimator.ofFloat(upperWhiteDot3, View.ALPHA, 1f);
        fadeAnim3.setDuration(FADE_TIME);
        fadeAnim3.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ObjectAnimator.ofFloat(upperWhiteDot1, View.ALPHA, 0.5f).setDuration(FADE_TIME).start();
                ObjectAnimator.ofFloat(upperWhiteDot2, View.ALPHA, 0.8f).setDuration(FADE_TIME).start();
            }
        });

        ObjectAnimator fadeAnim4 = ObjectAnimator.ofFloat(upperWhiteDot4, View.ALPHA, 1f);
        fadeAnim4.setDuration(FADE_TIME);
        fadeAnim4.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ObjectAnimator.ofFloat(upperWhiteDot1, View.ALPHA, 0.2f).setDuration(FADE_TIME).start();
                ObjectAnimator.ofFloat(upperWhiteDot2, View.ALPHA, 0.5f).setDuration(FADE_TIME).start();
                ObjectAnimator.ofFloat(upperWhiteDot3, View.ALPHA, 0.8f).setDuration(FADE_TIME).start();

                Log.d("trienLog", String.valueOf(upperWhiteDot1.getAlpha()));
            }
        });

        ObjectAnimator fadeAnim5 = ObjectAnimator.ofFloat(upperWhiteDot3, View.ALPHA, 1f);
        fadeAnim5.setDuration(FADE_TIME);

        ObjectAnimator fadeAnim6 = ObjectAnimator.ofFloat(upperWhiteDot2, View.ALPHA, 1f);
        fadeAnim6.setDuration(FADE_TIME);

        ObjectAnimator fadeAnim7 = ObjectAnimator.ofFloat(upperWhiteDot1, View.ALPHA, 1f);
        fadeAnim7.setDuration(FADE_TIME);

        ObjectAnimator fadeAnim8 = ObjectAnimator.ofFloat(fragment1, View.ALPHA, 0f);
        fadeAnim8.setDuration(FADE_TIME_LONG);
        fadeAnim8.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                lowerBubble.setVisibility(View.INVISIBLE);
                upperBubble.setVisibility(View.INVISIBLE);
                upperWhiteDot1.setAlpha(0f);
                upperWhiteDot2.setAlpha(0f);
                upperWhiteDot3.setAlpha(0f);
                upperWhiteDot4.setAlpha(0f);
                lowerWhiteDot1.setAlpha(0f);
                lowerWhiteDot2.setAlpha(0f);
                lowerWhiteDot3.setAlpha(0f);
                lowerWhiteDot4.setAlpha(0f);

                //animateLowerBubble(scaleX1, scaleX2, scaleX3, scaleY1, scaleY2);

            }
        });

        ObjectAnimator fadeAnim9 = ObjectAnimator.ofFloat(fragment1, View.ALPHA, 1f);
        fadeAnim9.setDuration(FADE_TIME);
        fadeAnim9.addListener(new AnimatorListenerAdapter() {

            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);

                animateLowerBubble(scaleX1, scaleX2, scaleX3, scaleY1, scaleY2);

            }
        });


        AnimatorSet whiteDotAnimSet = new AnimatorSet();
        whiteDotAnimSet.playSequentially(fadeAnim1, fadeAnim2, fadeAnim3, fadeAnim4, fadeAnim5, fadeAnim6, fadeAnim7, fadeAnim8, fadeAnim9);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(zoom1, zoom2, whiteDotAnimSet);
        animatorSet.start();


        /*
         * set up animation for lowerBubble
         */
    }
}
