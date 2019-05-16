package com.trien.dnanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Handler;
import androidx.core.content.res.ResourcesCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.TextView;

public class MyKidneySplashScreen extends AppCompatActivity {

    // constants
    private static final int SCALE_TIME = 100;
    private static final int FADE_TIME = 1000;
    private static final int SCALE_TO_ZERO_TIME = 1;
    private static final int OFFSET_X = 80;
    private static final int OFFSET_Y = 30;

    // Splash screen timeout
    long splashTimeOut = 500;

    // root view
    ViewGroup rootView;

    // all other views
    ImageView person1Img;
    ImageView person2Img;
    ImageView twoPersonsImg;
    ImageView bottomLogoImg;
    ImageView icHeart;
    ImageView icTwoHalves;
    TextView splashHeader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_kidney_splash_screen);

        // set up views
        setUpViews();
        //animate views
        animateViews();


    }

    /*
     * set up views
     */
    private void setUpViews() {

        rootView = findViewById(R.id.rootView);
        person1Img = findViewById(R.id.person1Img);
        person2Img = findViewById(R.id.person2Img);
        twoPersonsImg = findViewById(R.id.twoPersonsImg);
        bottomLogoImg = findViewById(R.id.bottomLogoImg);
        icHeart = findViewById(R.id.icHeart);
        icTwoHalves = findViewById(R.id.icTwoHalves);
        splashHeader = findViewById(R.id.splashHeader);

        // set font type for splashHeader
        splashHeader.setTypeface(ResourcesCompat.getFont(this, R.font.adelle_sans_bold));

        // set Alpha and visibility where applicable for animated views
        splashHeader.setAlpha(0f);
        bottomLogoImg.setAlpha(0f);
        person1Img.setAlpha(0f);
        person2Img.setAlpha(0f);
        twoPersonsImg.setAlpha(0f);
        icHeart.setVisibility(View.INVISIBLE);
        icTwoHalves.setAlpha(0f);
    }

    /*
     * animate views
     */
    private void animateViews() {

        person1Img.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                person1Img.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                /*
                 * animate header text and bottom logo
                 */
                AnimatorSet topAndBottomAnim = new AnimatorSet();
                topAndBottomAnim
                        .play(ObjectAnimator.ofFloat(splashHeader, View.ALPHA, 1))
                        .with(ObjectAnimator.ofFloat(bottomLogoImg, View.ALPHA, 1));
                topAndBottomAnim.setDuration(FADE_TIME);

                /*
                 * animate person 1
                 */
                ObjectAnimator person1Anim = ObjectAnimator.ofFloat(person1Img, View.ALPHA, 1);
                person1Anim.setDuration(FADE_TIME);

                /*
                 * animate person 2
                 */
                float person2ImgX = person2Img.getX();
                AnimatorSet person2Anim = new AnimatorSet();
                person2Anim
                        .play(ObjectAnimator.ofFloat(
                                person2Img
                                , View.X
                                , person2ImgX - person1Img.getWidth()/2
                                , person2ImgX))
                        .with(ObjectAnimator.ofFloat(person2Img, View.ALPHA, 1));
                person2Anim.setDuration(FADE_TIME);
                person2Anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        ObjectAnimator.ofFloat(twoPersonsImg, View.ALPHA, 1).setDuration(FADE_TIME / 2).start();
                    }
                });

                /*
                 * animate heart and kidney (2 halves) icons
                 */
                float icHeartX = icHeart.getX();
                float icHeartY = icHeart.getY();
                AnimatorSet organAnim = new AnimatorSet();
                organAnim
                        .play(ObjectAnimator.ofFloat(icTwoHalves, View.ALPHA, 1))
                        .with(ObjectAnimator.ofFloat(
                                icHeart
                                , View.X
                                , rootView.getWidth() / 2
                                , icHeartX + OFFSET_X
                                , icHeartX))
                        .with(ObjectAnimator.ofFloat(
                                icHeart
                                , View.Y
                                , rootView.getHeight() / 2
                                , icHeartY - OFFSET_Y
                                , icHeartY))
                        .with(ObjectAnimator.ofFloat(icHeart, View.SCALE_X, 0, 2, 1))
                        .with(ObjectAnimator.ofFloat(icHeart, View.SCALE_Y, 0, 2, 1))
                        .after(scaleToZero(icHeart));
                organAnim.setDuration(FADE_TIME);


                /*
                 * start animating the whole lot
                 */
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playSequentially(topAndBottomAnim, person1Anim, person2Anim, organAnim);
                animatorSet.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        // navigate to main activity after ending all animations
                        navigateToMainActivity(splashTimeOut);
                    }
                });

                animatorSet.start();
            }
        });
    }

    /*
     * method set a timer before switching to other activity
     */
    private void navigateToMainActivity(long splashTimeOut) {
        // create a timer to switch to other activity
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start MyKidneyIntroActivity activity
                Intent i = new Intent(MyKidneySplashScreen.this, MyKidneyIntroActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, splashTimeOut);
    }

    /*
     * a helper method to scale a view to zero
     */
    private AnimatorSet scaleToZero(final View view) {

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet
                .play(ObjectAnimator.ofFloat(view, View.SCALE_X, 0))
                .with(ObjectAnimator.ofFloat(view, View.SCALE_Y, 0));
        animatorSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.VISIBLE);
            }
        });
        animatorSet.setDuration(SCALE_TO_ZERO_TIME);
        return animatorSet;
    }
}
