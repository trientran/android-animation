package com.trien.dnanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.graphics.Typeface;
import androidx.core.content.res.ResourcesCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MyKidneyIntroActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    // constants: anim time
    private static final int SCALE_TIME = 100;
    private static final int FADE_TIME = 250;
    private static final int MOVE_TIME = 100;
    private static final int FADE_TIME_LONG = 500;
    private static final int DELAY_TIME_REGULAR = 50;
    private static final int SCALE_TO_ZERO_TIME = 1;
    private static final int OFFSET_X = 80;
    private static final int OFFSET_Y = 80;

    // constants: scale type
    private static final String SCALE_TYPE_CENTER = "center";
    private static final String SCALE_TYPE_TOP_RIGHT = "top_right";
    private static final String SCALE_TYPE_TOP_LEFT = "top_left";
    private static final String SCALE_TYPE_BOTTOM_LEFT = "bottom_left";
    private static final String SCALE_TYPE_BOTTOM_CENTER = "bottom_center";

    // ViewPager and MyViewPagerAdapter object
    private ViewPager viewPager;
    private int[] layouts;
    private MyViewPagerAdapter myViewPagerAdapter;

    /*
     * all other views
     */

    // containers for animated icons for each slide
    ViewGroup slide3IconGroup;
    ViewGroup slide2IconGroup;
    ViewGroup slide1IconGroup;

    ViewGroup fadedViewGroupSlide1;
    ViewGroup fadedViewGroupSlide2;
    ViewGroup fadedViewGroupSlide3;

    // root view
    private ViewGroup rootView;

    // background views for the circle
    private ImageView circleRedBg;
    private ImageView circleFrame;
    private ImageView circleFrameSlide2;

    // slide 1 views
    private ImageView iphoneImg;
    private ImageView icSlash1;
    private ImageView icSlash2;
    private ImageView icSlash3;
    private ImageView personSmallImg1;
    private ImageView personSmallImg2;
    private ImageView personSmallImg3;
    private ImageView personSmallImg4;
    private ImageView personSmallImg5;
    private ImageView personSmallImg6;
    private ImageView personBigImg;
    private ImageView icKidneySlide2;

    // slide 2 views
    private ImageView icShieldAnchor;
    private ImageView icSpeechBubbleAnchor;
    private ImageView icKidneySlide1Anchor;
    private ImageView icEyeGreenAnchor;
    private ImageView icCrossYellowAnchor;

    // slide 3 views
    private ImageView icKidneySlide3;
    private View icConnector1;
    private View icConnector2;
    private View icConnector3;
    private View icConnector4;

    // 3 dots
    private ImageView icCircleWhite1;
    private ImageView icCircleWhite2;
    private ImageView icCircleWhite3;

    // get started button
    private Button getStartedBtn;

    // bottom layout
    private View bottomLayout;

    // slide 1,2,3 introduction texts (header text and body text)
    View introText1;
    View introText2;
    View introText3;

    // header texts
    TextView introHeader1;
    TextView introHeader2;
    TextView introHeader3;

    // body texts
    TextView introBody1;
    TextView introBody2;
    TextView introBody3;

    // boolean variables for determining if users enter the slide 2 and 3 for the first time
    boolean isSlide2FirstTime = true;
    boolean isSlide3FirstTime = true;

    // font types
    Typeface adelleSansNormal;
    Typeface adelleSansBold;
    Typeface adelleSansExtraBold;
    Typeface adelleSansLight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_kidney_intro);

        // set up views
        setUpViews();

        // animate views
        animateSlide1();
    }

    /*
     * set up views
     */
    private void setUpViews() {

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        rootView = (ViewGroup) findViewById(R.id.rootView);

        circleRedBg = (ImageView) findViewById(R.id.circleRedBg);
        circleFrame = (ImageView) findViewById(R.id.circleFrame);
        circleFrameSlide2 = (ImageView) findViewById(R.id.circleFrameSlide2);

        iphoneImg = (ImageView) findViewById(R.id.iphoneImg);
        icShieldAnchor = (ImageView) findViewById(R.id.icShieldAnchor);
        icSpeechBubbleAnchor = (ImageView) findViewById(R.id.icSpeechBubbleAnchor);
        icKidneySlide1Anchor = (ImageView) findViewById(R.id.icKidneySlide1Anchor);
        icEyeGreenAnchor = (ImageView) findViewById(R.id.icEyeGreenAnchor);
        icCrossYellowAnchor = (ImageView) findViewById(R.id.icCrossYellowAnchor);

        icSlash1 = (ImageView) findViewById(R.id.icSlash1);
        icSlash2 = (ImageView) findViewById(R.id.icSlash2);
        icSlash3 = (ImageView) findViewById(R.id.icSlash3);
        personSmallImg1 = (ImageView) findViewById(R.id.personSmallImg1);
        personSmallImg2 = (ImageView) findViewById(R.id.personSmallImg2);
        personSmallImg3 = (ImageView) findViewById(R.id.personSmallImg3);
        personSmallImg4 = (ImageView) findViewById(R.id.personSmallImg4);
        personSmallImg5 = (ImageView) findViewById(R.id.personSmallImg5);
        personSmallImg6 = (ImageView) findViewById(R.id.personSmallImg6);
        personBigImg = (ImageView) findViewById(R.id.personBigImg);
        icKidneySlide2 = (ImageView) findViewById(R.id.icKidneySlide2);

        icKidneySlide3 = (ImageView) findViewById(R.id.icKidneySlide3);
        icConnector1 = findViewById(R.id.icConnector1);
        icConnector2 = findViewById(R.id.icConnector2);
        icConnector3 = findViewById(R.id.icConnector3);
        icConnector4 = findViewById(R.id.icConnector4);

        slide3IconGroup = findViewById(R.id.slide3IconGroup);
        slide2IconGroup = findViewById(R.id.slide2IconGroup);
        slide1IconGroup = findViewById(R.id.slide1IconGroup);
        fadedViewGroupSlide1 = findViewById(R.id.fadedViewGroupSlide1);
        fadedViewGroupSlide2 = findViewById(R.id.fadedViewGroupSlide2);
        fadedViewGroupSlide3 = findViewById(R.id.fadedViewGroupSlide3);

        icCircleWhite1 = (ImageView) findViewById(R.id.icCircleWhiteAnchor1);
        icCircleWhite2 = (ImageView) findViewById(R.id.icCircleWhiteAnchor2);
        icCircleWhite3 = (ImageView) findViewById(R.id.icCircleWhiteAnchor3);

        // set up get Started button
        getStartedBtn = (Button) findViewById(R.id.getStartedBtn);
        getStartedBtn.setTypeface(adelleSansLight);
        getStartedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // once users click getStartedBtn, run MainActivity activity
                Log.d("trienBtn", "starbtu");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        bottomLayout = findViewById(R.id.bottomLayout);

        // layouts of all sliders
        // add few more layouts if we want
        layouts = new int[]{
                R.layout.my_kidney_slide1,
                R.layout.my_kidney_slide2,
                R.layout.my_kidney_slide3};

        // set up viewpager
        myViewPagerAdapter = new MyViewPagerAdapter(this, layouts);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(this);
        viewPager.setPageTransformer(true, new PageTransformer());

        // initialize font types
        adelleSansNormal = ResourcesCompat.getFont(this, R.font.adelle_sans);
        adelleSansBold = ResourcesCompat.getFont(this, R.font.adelle_sans_bold);
        adelleSansExtraBold = ResourcesCompat.getFont(this, R.font.adelle_sans_extra_bold);
        adelleSansLight = ResourcesCompat.getFont(this, R.font.adelle_sans_light);

        /*
         * set Alpha and visibility where applicable for animated views
         */

        circleRedBg.setVisibility(View.INVISIBLE);

        icShieldAnchor.setVisibility(View.INVISIBLE);
        icEyeGreenAnchor.setVisibility(View.INVISIBLE);
        icKidneySlide1Anchor.setVisibility(View.INVISIBLE);
        icCrossYellowAnchor.setVisibility(View.INVISIBLE);
        icSpeechBubbleAnchor.setVisibility(View.INVISIBLE);

        personSmallImg1.setVisibility(View.INVISIBLE);
        personSmallImg2.setVisibility(View.INVISIBLE);
        personSmallImg3.setVisibility(View.INVISIBLE);
        personSmallImg4.setVisibility(View.INVISIBLE);
        personSmallImg5.setVisibility(View.INVISIBLE);
        personSmallImg6.setVisibility(View.INVISIBLE);
        personBigImg.setVisibility(View.INVISIBLE);

        icSlash1.setVisibility(View.INVISIBLE);
        icSlash2.setVisibility(View.INVISIBLE);
        icSlash3.setVisibility(View.INVISIBLE);
        icKidneySlide2.setVisibility(View.INVISIBLE);

        icKidneySlide3.setVisibility(View.INVISIBLE);
        icConnector1.setVisibility(View.INVISIBLE);
        icConnector2.setVisibility(View.INVISIBLE);
        icConnector3.setVisibility(View.INVISIBLE);
        icConnector4.setVisibility(View.INVISIBLE);

        bottomLayout.setVisibility(View.INVISIBLE);

        icCircleWhite1.setVisibility(View.INVISIBLE);
        icCircleWhite2.setVisibility(View.INVISIBLE);
        icCircleWhite3.setVisibility(View.INVISIBLE);
    }

    /*
     * animate slide 1
     */
    private void animateSlide1() {

        // call method getViewTreeObserver and addOnGlobalLayoutListener to get some dimensions of
        // views after layouts (otherwise it will return 0 when calling getWidth/getHeight)
        circleFrame.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                // call removeOnGlobalLayoutListener so the method getViewTreeObserver is only executed once
                circleFrame.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                // call getOverlay().add() method on rootView to add some child views that initially
                // belongs to an inner layout/container. This is to help move or scale views outside
                // the inner container
                fadedViewGroupSlide1.getOverlay().add(icShieldAnchor);
                fadedViewGroupSlide1.getOverlay().add(icEyeGreenAnchor);
                fadedViewGroupSlide1.getOverlay().add(icKidneySlide1Anchor);
                fadedViewGroupSlide1.getOverlay().add(icCrossYellowAnchor);
                fadedViewGroupSlide1.getOverlay().add(icSpeechBubbleAnchor);

                // set up and set Alpha value to 0 so introText1 and introText2 are completely opaque
                // when loading Slide 1
                introText1 = findViewById(R.id.introText1);
                introText1.setAlpha(0);
                introText2 = findViewById(R.id.introText2);
                introText2.setAlpha(0);

                introHeader1 = findViewById(R.id.introHeader1);
                introHeader2 = findViewById(R.id.introHeader2);
                introBody1 = findViewById(R.id.introBody1);
                introBody2 = findViewById(R.id.introBody2);

                // set font types for text views
                introHeader1.setTypeface(adelleSansExtraBold);
                introHeader2.setTypeface(adelleSansExtraBold);
                introBody1.setTypeface(adelleSansNormal);
                introBody2.setTypeface(adelleSansNormal);


                /*
                 * animate the red circle
                 */
                AnimatorSet redCircleAnim = new AnimatorSet();
                redCircleAnim
                        .play(ObjectAnimator.ofFloat(circleRedBg, View.SCALE_X, 0, 1))
                        .with(ObjectAnimator.ofFloat(circleRedBg, View.SCALE_Y, 0, 1))
                        .after(scaleToZero(circleRedBg));
                redCircleAnim.setDuration(SCALE_TIME);

                /*
                 * animate the iphone image
                 */
                ObjectAnimator iphoneAnim = ObjectAnimator.ofFloat(iphoneImg, View.Y, circleFrame.getHeight() * 0.17f);
                iphoneAnim.setDuration(MOVE_TIME);
                iphoneAnim.setStartDelay(DELAY_TIME_REGULAR);

                /*
                 * animate icShield icon
                 */
                float icShieldX = icShieldAnchor.getX();

                AnimatorSet icShieldAnim = new AnimatorSet();
                icShieldAnim
                        .play(ObjectAnimator.ofFloat(
                                icShieldAnchor
                                , View.X
                                , rootView.getWidth() / 2
                                , icShieldX - OFFSET_X
                                , icShieldX))
                        .with(ObjectAnimator.ofFloat(icShieldAnchor, View.SCALE_X, 0, 1.5f, 1))
                        .with(ObjectAnimator.ofFloat(icShieldAnchor, View.SCALE_Y, 0, 1.5f, 1))
                        .after(scaleToZero(icShieldAnchor));
                icShieldAnim.setDuration(SCALE_TIME);
                icShieldAnim.setInterpolator(new DecelerateInterpolator());
                icShieldAnim.setStartDelay(DELAY_TIME_REGULAR);

                /*
                 * animate icEyeGreen icon
                 */
                float icEyeGreenX = icEyeGreenAnchor.getX();
                float icEyeGreenY = icEyeGreenAnchor.getY();

                AnimatorSet icEyeGreenAnim = new AnimatorSet();
                icEyeGreenAnim
                        .play(ObjectAnimator.ofFloat(
                                icEyeGreenAnchor
                                , View.X
                                , rootView.getWidth() / 2
                                , icEyeGreenX - OFFSET_X
                                , icEyeGreenX))
                        .with(ObjectAnimator.ofFloat(icEyeGreenAnchor
                                , View.Y
                                , findViewById(R.id.guidelineHA8).getY()
                                , icEyeGreenY - OFFSET_Y
                                , icEyeGreenY))
                        .with(ObjectAnimator.ofFloat(icEyeGreenAnchor, View.SCALE_X, 0, 1.5f, 1))
                        .with(ObjectAnimator.ofFloat(icEyeGreenAnchor, View.SCALE_Y, 0, 1.5f, 1))
                        .after(scaleToZero(icEyeGreenAnchor));
                icEyeGreenAnim.setInterpolator(new DecelerateInterpolator());
                icEyeGreenAnim.setDuration(SCALE_TIME);

                /*
                 * animate icTwoKidney icon
                 */
                float icTwoKidneyY = icKidneySlide1Anchor.getY();

                AnimatorSet icTwoKidneyAnim = new AnimatorSet();
                icTwoKidneyAnim
                        .play(ObjectAnimator.ofFloat(icKidneySlide1Anchor, View.Y, findViewById(R.id.guidelineHA8).getY(), 0, icTwoKidneyY))
                        .with(ObjectAnimator.ofFloat(icKidneySlide1Anchor, View.SCALE_X, 0, 1.5f, 1))
                        .with(ObjectAnimator.ofFloat(icKidneySlide1Anchor, View.SCALE_Y, 0, 1.5f, 1))
                        .after(scaleToZero(icKidneySlide1Anchor));
                icTwoKidneyAnim.setInterpolator(new DecelerateInterpolator());
                icTwoKidneyAnim.setDuration(SCALE_TIME);

                /*
                 * animate icCrossYellow icon
                 */
                float icCrossYellowX = icCrossYellowAnchor.getX();
                float icCrossYellowY = icCrossYellowAnchor.getY();

                AnimatorSet icCrossYellowAnim = new AnimatorSet();
                icCrossYellowAnim
                        .play(ObjectAnimator.ofFloat(
                                icCrossYellowAnchor
                                , View.X
                                , rootView.getWidth() / 2
                                , icCrossYellowX + OFFSET_X
                                , icCrossYellowX))
                        .with(ObjectAnimator.ofFloat(
                                icCrossYellowAnchor
                                , View.Y
                                , findViewById(R.id.guidelineHA8).getY()
                                , icCrossYellowY - OFFSET_Y
                                , icCrossYellowY))
                        .with(ObjectAnimator.ofFloat(icCrossYellowAnchor, View.SCALE_X, 0, 1.5f, 1))
                        .with(ObjectAnimator.ofFloat(icCrossYellowAnchor, View.SCALE_Y, 0, 1.5f, 1))
                        .after(scaleToZero(icCrossYellowAnchor));
                icCrossYellowAnim.setInterpolator(new DecelerateInterpolator());
                icCrossYellowAnim.setDuration(SCALE_TIME);

                /*
                 * animate icSpeechBubble icon
                 */
                float icSpeechBubbleX = icSpeechBubbleAnchor.getX();

                AnimatorSet icSpeechBubbleAnim = new AnimatorSet();
                icSpeechBubbleAnim
                        .play(ObjectAnimator.ofFloat(
                                icSpeechBubbleAnchor
                                , View.X
                                , rootView.getWidth() / 2
                                , icSpeechBubbleX + OFFSET_X
                                , icSpeechBubbleX))
                        .with(ObjectAnimator.ofFloat(icSpeechBubbleAnchor, View.SCALE_X, 0, 1.5f, 1))
                        .with(ObjectAnimator.ofFloat(icSpeechBubbleAnchor, View.SCALE_Y, 0, 1.5f, 1))
                        .after(scaleToZero(icSpeechBubbleAnchor));
                icSpeechBubbleAnim.setDuration(SCALE_TIME);
                icSpeechBubbleAnim.setInterpolator(new DecelerateInterpolator());


                /*
                 * animate bottomLayout (3 dots and Get Started button)
                 */

                // slide bottomLayout from bottom up
                ObjectAnimator bottomLayoutSlideUp = ObjectAnimator.ofFloat(bottomLayout, View.Y,
                        rootView.getHeight(), findViewById(R.id.guidelineH3).getY());
                bottomLayoutSlideUp.setDuration(MOVE_TIME * 2);
                bottomLayoutSlideUp.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        bottomLayout.setVisibility(View.VISIBLE);
                    }
                });

                // combine animations for bottomLayout
                // (animate white dot and bottomLayout slide-up
                final AnimatorSet bottomLayoutAnim = new AnimatorSet();
                bottomLayoutAnim
                        .play(zoomCommonView(icCircleWhite1, SCALE_TYPE_CENTER, 0, 1))
                        .with(scaleToZero(icCircleWhite2))
                        .with(scaleToZero(icCircleWhite3))
                        .after(bottomLayoutSlideUp);

                /*
                 * combine animation sets for introGroup1 and bottomLayout
                 */
                ObjectAnimator introText1Anim = ObjectAnimator.ofFloat(introText1, View.ALPHA, 1);
                introText1Anim.setDuration(FADE_TIME_LONG * 2);
                introText1Anim.setInterpolator(new AccelerateInterpolator());
                introText1Anim.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                        super.onAnimationStart(animation);
                        bottomLayoutAnim.start();
                    }
                });

                /*
                 * start animating the whole lot
                 */
                AnimatorSet animatorSet = new AnimatorSet();
                animatorSet.playSequentially(redCircleAnim, iphoneAnim, icShieldAnim, icEyeGreenAnim,
                        icTwoKidneyAnim, icCrossYellowAnim, icSpeechBubbleAnim, introText1Anim);
                animatorSet.start();
            }
        });
    }

    /*
     * define behaviors of views when scrolling between pages
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, final int positionOffsetPixels) {

        // set up fadeOutAlpha and fadeInAlpha values when scrolling.
        float fadeOutAlpha = 1 - positionOffset * 2; //
        float fadeInAlpha = (float) ((positionOffset - 0.5) * 2);

        // avoid negative values
        if (fadeOutAlpha < 0) {
            fadeOutAlpha = 0;
        }

        // avoid negative values
        if (fadeInAlpha < 0) {
            fadeInAlpha = 0;
        }

        /*
         * if position = 0 (slide 1 selected)
         */
        if (position == 0) {

            // when users scroll to the left, fade out slide 1 views
            fadedViewGroupSlide1.setAlpha(fadeOutAlpha);
            iphoneImg.setAlpha(fadeOutAlpha);
            icCircleWhite1.setScaleX(fadeOutAlpha);
            icCircleWhite1.setScaleY(fadeOutAlpha);

            // when users scroll to the left, fade in slide 2 views
            fadedViewGroupSlide2.setAlpha(fadeInAlpha);
            icCircleWhite2.setScaleX(fadeInAlpha);
            icCircleWhite2.setScaleY(fadeInAlpha);
        }

        /*
         * if position = 1 (slide 2 selected)
         */
        if (position == 1) {

            // when users scroll to the left, fade out slide 2 views and vice versa
            fadedViewGroupSlide2.setAlpha(fadeOutAlpha);
            icCircleWhite2.setScaleX(fadeOutAlpha);
            icCircleWhite2.setScaleY(fadeOutAlpha);

            // when users scroll to the left, fade in slide 3 views and vice versa
            //slide3IconGroup.setAlpha(fadeInAlpha);
            fadedViewGroupSlide3.setAlpha(fadeInAlpha);
            icCircleWhite3.setScaleX(fadeInAlpha);
            icCircleWhite3.setScaleY(fadeInAlpha);
        }
    }

    /*
     * define behaviors of views when a page is selected
     */
    @Override
    public void onPageSelected(int position) {

        // if slide 2 selected, and this is the first time user navigate to, animate slide 2 views
        if (position == 1 && isSlide2FirstTime) {

            animateSlide2();
        }

        // if slide 3 selected, and this is the first time user navigate to, animate slide 3 views
        if (position == 2 && isSlide3FirstTime) {

            animateSlide3();
        }
    }

    /*
     * do something when page scroll state changed
     */
    @Override
    public void onPageScrollStateChanged(int state) {

    }

    /*
     * method to animate Slide 2
     */
    private void animateSlide2() {

        // make isSlide2FirstTime false so next time when users navigate to this slide, it won't run animations
        isSlide2FirstTime = false;

        // call getOverlay().add() method on rootView to add some child views that initially
        // belongs to an inner layout/container. This is to help move or scale views outside
        // the inner container
        fadedViewGroupSlide2.getOverlay().add(personSmallImg6);
        fadedViewGroupSlide2.getOverlay().add(personSmallImg3);
        fadedViewGroupSlide2.getOverlay().add(personSmallImg5);
        fadedViewGroupSlide2.getOverlay().add(personSmallImg2);
        fadedViewGroupSlide2.getOverlay().add(personSmallImg4);
        fadedViewGroupSlide2.getOverlay().add(personSmallImg1);
        fadedViewGroupSlide2.getOverlay().add(personBigImg);
        fadedViewGroupSlide2.getOverlay().add(icSlash1);
        fadedViewGroupSlide2.getOverlay().add(icSlash2);
        fadedViewGroupSlide2.getOverlay().add(icSlash3);
        fadedViewGroupSlide2.getOverlay().add(icKidneySlide2);
        fadedViewGroupSlide2.getOverlay().add(circleFrameSlide2);


        /*
         * animate persons icons
         */
        AnimatorSet personsAnim = new AnimatorSet();
        personsAnim.playSequentially(
                zoomCommonView(personBigImg, SCALE_TYPE_CENTER, 0, 1),
                zoomCommonView(personSmallImg1, SCALE_TYPE_CENTER, 0, 1),
                zoomCommonView(personSmallImg4, SCALE_TYPE_CENTER, 0, 1),
                zoomCommonView(personSmallImg2, SCALE_TYPE_CENTER, 0, 1),
                zoomCommonView(personSmallImg5, SCALE_TYPE_CENTER, 0, 1),
                zoomCommonView(personSmallImg3, SCALE_TYPE_CENTER, 0, 1),
                zoomCommonView(personSmallImg6, SCALE_TYPE_CENTER, 0, 1));

        /*
         * animate the 3 slash and the kidney
         */
        final ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(icSlash1, View.ALPHA, 0, 1);
        final AnimatorSet slashAndKidneyAnim = new AnimatorSet();
        slashAndKidneyAnim
                .play(objectAnimator)
                .with(ObjectAnimator.ofFloat(icSlash2, View.ALPHA, 0, 1))
                .with(ObjectAnimator.ofFloat(icSlash3, View.ALPHA, 0, 1))
                .with(ObjectAnimator.ofFloat(icKidneySlide2, View.ALPHA, 0, 1));
        slashAndKidneyAnim.setDuration(FADE_TIME);
        slashAndKidneyAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                icSlash1.setVisibility(View.VISIBLE);
                icSlash2.setVisibility(View.VISIBLE);
                icSlash3.setVisibility(View.VISIBLE);
                icKidneySlide2.setVisibility(View.VISIBLE);
            }
        });

        /*
         * animate the text
         */
        ObjectAnimator introText2Anim = ObjectAnimator.ofFloat(introText2, View.ALPHA, 1);
        introText2Anim.setDuration(FADE_TIME_LONG * 2);
        introText2Anim.setInterpolator(new AccelerateInterpolator());

        /*
         * start animating the whole lot
         */
        final AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(personsAnim, slashAndKidneyAnim, introText2Anim);
        animatorSet.start();
    }

    /*
     * method to animate Slide 3
     */
    private void animateSlide3() {

        // make isSlide3FirstTime false so next time when users navigate to this slide, it won't run animations
        isSlide3FirstTime = false;

        // call getOverlay().add() method on rootView to add some child views that initially
        // belongs to an inner layout/container. This is to help move or scale views outside
        // the inner container
        fadedViewGroupSlide3.getOverlay().add(icConnector1);
        fadedViewGroupSlide3.getOverlay().add(icConnector2);
        fadedViewGroupSlide3.getOverlay().add(icConnector3);
        fadedViewGroupSlide3.getOverlay().add(icConnector4);
        fadedViewGroupSlide3.getOverlay().add(icKidneySlide3);

        // rotate icConnector icons properly when creating slide 3 views
        icConnector1.setRotation(100);
        icConnector2.setRotation(260);
        icConnector3.setRotation(160);
        icConnector4.setRotation(10);

        /*
         * animate the text
         */
        ObjectAnimator introText2Anim = ObjectAnimator.ofFloat(introText3, View.ALPHA, 1);
        introText2Anim.setDuration(FADE_TIME_LONG * 2);
        introText2Anim.setInterpolator(new AccelerateInterpolator());
        introText2Anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                getStartedBtn.setBackgroundResource(R.drawable.rectangle_rounded_white);
                getStartedBtn.setTextColor(getResources().getColor(R.color.white));
            }
        });

        /*
         * animate kidney icon and the whole lot
         */
        AnimatorSet kidneyAnim = new AnimatorSet();
        kidneyAnim.playSequentially(
                zoomCommonView(icKidneySlide3, SCALE_TYPE_CENTER, 0, 1).setDuration(SCALE_TIME * 2),
                zoomCommonView(icConnector1, SCALE_TYPE_BOTTOM_CENTER, 0, 1.5f, 1),
                zoomCommonView(icConnector2, SCALE_TYPE_BOTTOM_CENTER, 0, 1.5f, 1),
                zoomCommonView(icConnector3, SCALE_TYPE_BOTTOM_CENTER, 0, 1.5f, 1),
                zoomCommonView(icConnector4, SCALE_TYPE_BOTTOM_CENTER, 0, 1.5f, 1),
                introText2Anim);
        kidneyAnim.start();
    }

    /*
     * method to zoom common views: scale view to zero first, then scale to wanted values
     */
    private AnimatorSet zoomCommonView(View view, String scaleType, float... values) {

        AnimatorSet animatorSet;

        // set pivot X and Y for the scaled view. Pivot values must be calculated basing on
        // the view height/width where 0,0 means the pivot points are top/left of the view;
        // full height and full width means bottom/right of the views...

        if (scaleType.equals(SCALE_TYPE_CENTER)) {

            view.setPivotX(view.getWidth() / 2);
            view.setPivotY(view.getHeight() / 2);
        }
        if (scaleType.equals(SCALE_TYPE_TOP_RIGHT)) {

            view.setPivotX(view.getWidth());
            view.setPivotY(0f);
        }
        if (scaleType.equals(SCALE_TYPE_TOP_LEFT)) {

            view.setPivotX(0f);
            view.setPivotY(0f);
        }
        if (scaleType.equals(SCALE_TYPE_BOTTOM_LEFT)) {

            view.setPivotX(0f);
            view.setPivotY(view.getHeight());
        }

        if (scaleType.equals(SCALE_TYPE_BOTTOM_CENTER)) {

            view.setPivotY(view.getHeight());
        }

        /*
         * scale view
         */
        animatorSet = new AnimatorSet();
        animatorSet
                .play(ObjectAnimator.ofFloat(view, View.SCALE_X, values))
                .with(ObjectAnimator.ofFloat(view, View.SCALE_Y, values))
                .after(scaleToZero(view));
        animatorSet.setDuration(SCALE_TIME);

        return animatorSet;
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

    /*
     * customise PageTransformer class to define behaviors/animations/transitions of views/slides
     */
    public class PageTransformer implements ViewPager.PageTransformer {

        public void transformPage(View view, float position) {

            // if position < -1 (this page is way off-screen to the left)
            if (position < -1) { // [-Infinity,-1)

                // and if first time navigating to slide 3, set up slide 3 text views
                // we set up them here because slide 3 layout wont be inflated until users navigate to
                if (isSlide3FirstTime) {
                    introText3 = findViewById(R.id.introText3);
                    introText3.setAlpha(0);

                    introHeader3 = findViewById(R.id.introHeader3);
                    introBody3 = findViewById(R.id.introBody3);

                    introHeader3.setTypeface(adelleSansExtraBold);
                    introBody3.setTypeface(adelleSansNormal);
                }

            }
        }
    }
}
