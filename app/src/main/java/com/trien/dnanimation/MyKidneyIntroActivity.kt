package com.trien.dnanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.animation.AccelerateInterpolator
import android.view.animation.DecelerateInterpolator
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_my_kidney_intro.*
import kotlinx.android.synthetic.main.my_kidney_slide1.*
import kotlinx.android.synthetic.main.my_kidney_slide1_icon_group.*
import kotlinx.android.synthetic.main.my_kidney_slide2.*
import kotlinx.android.synthetic.main.my_kidney_slide2_icon_group.*
import kotlinx.android.synthetic.main.my_kidney_slide3.*
import kotlinx.android.synthetic.main.my_kidney_slide3_icon_group.*

class MyKidneyIntroActivity : AppCompatActivity(), ViewPager.OnPageChangeListener {


    private lateinit var layouts: IntArray

    // boolean variables for determining if users enter the slide 2 and 3 for the first time
    internal var isSlide2FirstTime = true
    internal var isSlide3FirstTime = true

    // font types
    internal var adelleSansNormal: Typeface? = null
    internal var adelleSansBold: Typeface? = null
    internal var adelleSansExtraBold: Typeface? = null
    internal var adelleSansLight: Typeface? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_kidney_intro)

        // set up views
        setUpViews()

        // animate views
        animateSlide1()
    }

    /*
     * set up views
     */
    private fun setUpViews() {

        // set up get Started button
        getStartedBtn!!.typeface = adelleSansLight
        getStartedBtn!!.setOnClickListener {
            // once users click getStartedBtn, run MainActivity activity
            Log.d("trienBtn", "starbtu")
            val intent = Intent(applicationContext, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // layouts of all sliders
        // add few more layouts if we want
        layouts = intArrayOf(R.layout.my_kidney_slide1, R.layout.my_kidney_slide2, R.layout.my_kidney_slide3)

        // set up viewpager
        val myViewPagerAdapter = MyViewPagerAdapter(this, layouts!!)
        viewPager!!.adapter = myViewPagerAdapter
        viewPager!!.addOnPageChangeListener(this)
        viewPager!!.setPageTransformer(true, PageTransformer())

        // initialize font types
        adelleSansNormal = ResourcesCompat.getFont(this, R.font.adelle_sans)
        adelleSansBold = ResourcesCompat.getFont(this, R.font.adelle_sans_bold)
        adelleSansExtraBold = ResourcesCompat.getFont(this, R.font.adelle_sans_extra_bold)
        adelleSansLight = ResourcesCompat.getFont(this, R.font.adelle_sans_light)

        /*
         * set Alpha and visibility where applicable for animated views
         */

        circleRedBg!!.visibility = View.INVISIBLE

        icShieldAnchor!!.visibility = View.INVISIBLE
        icEyeGreenAnchor!!.visibility = View.INVISIBLE
        icKidneySlide1Anchor!!.visibility = View.INVISIBLE
        icCrossYellowAnchor!!.visibility = View.INVISIBLE
        icSpeechBubbleAnchor!!.visibility = View.INVISIBLE

        personSmallImg1!!.visibility = View.INVISIBLE
        personSmallImg2!!.visibility = View.INVISIBLE
        personSmallImg3!!.visibility = View.INVISIBLE
        personSmallImg4!!.visibility = View.INVISIBLE
        personSmallImg5!!.visibility = View.INVISIBLE
        personSmallImg6!!.visibility = View.INVISIBLE
        personBigImg!!.visibility = View.INVISIBLE

        icSlash1!!.visibility = View.INVISIBLE
        icSlash2!!.visibility = View.INVISIBLE
        icSlash3!!.visibility = View.INVISIBLE
        icKidneySlide2!!.visibility = View.INVISIBLE

        icKidneySlide3!!.visibility = View.INVISIBLE
        icConnector1!!.visibility = View.INVISIBLE
        icConnector2!!.visibility = View.INVISIBLE
        icConnector3!!.visibility = View.INVISIBLE
        icConnector4!!.visibility = View.INVISIBLE

        bottomLayout!!.visibility = View.INVISIBLE

        icCircleWhiteAnchor1!!.visibility = View.INVISIBLE
        icCircleWhiteAnchor2!!.visibility = View.INVISIBLE
        icCircleWhiteAnchor3!!.visibility = View.INVISIBLE
    }

    /*
     * animate slide 1
     */
    private fun animateSlide1() {

        // call method getViewTreeObserver and addOnGlobalLayoutListener to get some dimensions of
        // views after layouts (otherwise it will return 0 when calling getWidth/getHeight)
        circleFrame!!.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                // call removeOnGlobalLayoutListener so the method getViewTreeObserver is only executed once
                circleFrame!!.viewTreeObserver.removeOnGlobalLayoutListener(this)

                // call getOverlay().add() method on rootView to add some child views that initially
                // belongs to an inner layout/container. This is to help move or scale views outside
                // the inner container
                fadedViewGroupSlide1.overlay.add(icShieldAnchor!!)
                fadedViewGroupSlide1.overlay.add(icEyeGreenAnchor!!)
                fadedViewGroupSlide1.overlay.add(icKidneySlide1Anchor!!)
                fadedViewGroupSlide1.overlay.add(icCrossYellowAnchor!!)
                fadedViewGroupSlide1.overlay.add(icSpeechBubbleAnchor!!)

                // set up and set Alpha value to 0 so introText1 and introText2 are completely opaque
                // when loading Slide 1
                introText1.alpha = 0f
                introText2.alpha = 0f

                // set font types for text views
                introHeader1.typeface = adelleSansExtraBold
                introHeader2.typeface = adelleSansExtraBold
                introBody1.typeface = adelleSansNormal
                introBody2.typeface = adelleSansNormal


                /*
                 * animate the red circle
                 */
                val redCircleAnim = AnimatorSet()
                redCircleAnim
                        .play(ObjectAnimator.ofFloat<View>(circleRedBg, View.SCALE_X, 0f, 1f))
                        .with(ObjectAnimator.ofFloat<View>(circleRedBg, View.SCALE_Y, 0f, 1f))
                        .after(scaleToZero(circleRedBg))
                redCircleAnim.duration = SCALE_TIME.toLong()

                /*
                 * animate the iphone image
                 */
                val iphoneAnim = ObjectAnimator.ofFloat(iphoneImg, View.Y, circleFrame!!.height * 0.17f)
                iphoneAnim.duration = MOVE_TIME.toLong()
                iphoneAnim.startDelay = DELAY_TIME_REGULAR.toLong()

                /*
                 * animate icShield icon
                 */
                val icShieldX = icShieldAnchor!!.x

                val icShieldAnim = AnimatorSet()
                icShieldAnim
                        .play(ObjectAnimator.ofFloat<View>(
                                icShieldAnchor, View.X, rootView!!.width / 2f, icShieldX - OFFSET_X, icShieldX))
                        .with(ObjectAnimator.ofFloat<View>(icShieldAnchor, View.SCALE_X, 0f, 1.5f, 1f))
                        .with(ObjectAnimator.ofFloat<View>(icShieldAnchor, View.SCALE_Y, 0f, 1.5f, 1f))
                        .after(scaleToZero(icShieldAnchor))
                icShieldAnim.duration = SCALE_TIME.toLong()
                icShieldAnim.interpolator = DecelerateInterpolator()
                icShieldAnim.startDelay = DELAY_TIME_REGULAR.toLong()

                /*
                 * animate icEyeGreen icon
                 */
                val icEyeGreenX = icEyeGreenAnchor!!.x
                val icEyeGreenY = icEyeGreenAnchor!!.y

                val icEyeGreenAnim = AnimatorSet()
                icEyeGreenAnim
                        .play(ObjectAnimator.ofFloat<View>(
                                icEyeGreenAnchor, View.X, rootView!!.width / 2f, icEyeGreenX - OFFSET_X, icEyeGreenX))
                        .with(ObjectAnimator.ofFloat(icEyeGreenAnchor, View.Y, findViewById<View>(R.id.guidelineHA8).y, icEyeGreenY - OFFSET_Y, icEyeGreenY))
                        .with(ObjectAnimator.ofFloat<View>(icEyeGreenAnchor, View.SCALE_X, 0f, 1.5f, 1f))
                        .with(ObjectAnimator.ofFloat<View>(icEyeGreenAnchor, View.SCALE_Y, 0f, 1.5f, 1f))
                        .after(scaleToZero(icEyeGreenAnchor))
                icEyeGreenAnim.interpolator = DecelerateInterpolator()
                icEyeGreenAnim.duration = SCALE_TIME.toLong()

                /*
                 * animate icTwoKidney icon
                 */
                val icTwoKidneyY = icKidneySlide1Anchor!!.y

                val icTwoKidneyAnim = AnimatorSet()
                icTwoKidneyAnim
                        .play(ObjectAnimator.ofFloat<View>(icKidneySlide1Anchor, View.Y, findViewById<View>(R.id.guidelineHA8).y, 0f, icTwoKidneyY))
                        .with(ObjectAnimator.ofFloat<View>(icKidneySlide1Anchor, View.SCALE_X, 0f, 1.5f, 1f))
                        .with(ObjectAnimator.ofFloat<View>(icKidneySlide1Anchor, View.SCALE_Y, 0f, 1.5f, 1f))
                        .after(scaleToZero(icKidneySlide1Anchor))
                icTwoKidneyAnim.interpolator = DecelerateInterpolator()
                icTwoKidneyAnim.duration = SCALE_TIME.toLong()

                /*
                 * animate icCrossYellow icon
                 */
                val icCrossYellowX = icCrossYellowAnchor!!.x
                val icCrossYellowY = icCrossYellowAnchor!!.y

                val icCrossYellowAnim = AnimatorSet()
                icCrossYellowAnim
                        .play(ObjectAnimator.ofFloat<View>(
                                icCrossYellowAnchor, View.X, rootView!!.width / 2f, icCrossYellowX + OFFSET_X, icCrossYellowX))
                        .with(ObjectAnimator.ofFloat(
                                icCrossYellowAnchor, View.Y, findViewById<View>(R.id.guidelineHA8).y, icCrossYellowY - OFFSET_Y, icCrossYellowY))
                        .with(ObjectAnimator.ofFloat<View>(icCrossYellowAnchor, View.SCALE_X, 0f, 1.5f, 1f))
                        .with(ObjectAnimator.ofFloat<View>(icCrossYellowAnchor, View.SCALE_Y, 0f, 1.5f, 1f))
                        .after(scaleToZero(icCrossYellowAnchor))
                icCrossYellowAnim.interpolator = DecelerateInterpolator()
                icCrossYellowAnim.duration = SCALE_TIME.toLong()

                /*
                 * animate icSpeechBubble icon
                 */
                val icSpeechBubbleX = icSpeechBubbleAnchor!!.x

                val icSpeechBubbleAnim = AnimatorSet()
                icSpeechBubbleAnim
                        .play(ObjectAnimator.ofFloat<View>(
                                icSpeechBubbleAnchor, View.X, rootView!!.width / 2f, icSpeechBubbleX + OFFSET_X, icSpeechBubbleX))
                        .with(ObjectAnimator.ofFloat<View>(icSpeechBubbleAnchor, View.SCALE_X, 0f, 1.5f, 1f))
                        .with(ObjectAnimator.ofFloat<View>(icSpeechBubbleAnchor, View.SCALE_Y, 0f, 1.5f, 1f))
                        .after(scaleToZero(icSpeechBubbleAnchor))
                icSpeechBubbleAnim.duration = SCALE_TIME.toLong()
                icSpeechBubbleAnim.interpolator = DecelerateInterpolator()


                /*
                 * animate bottomLayout (3 dots and Get Started button)
                 */

                // slide bottomLayout from bottom up
                val bottomLayoutSlideUp = ObjectAnimator.ofFloat<View>(bottomLayout, View.Y,
                        rootView!!.height.toFloat(), findViewById<View>(R.id.guidelineH3).y)
                bottomLayoutSlideUp.setDuration((MOVE_TIME * 2).toLong())
                bottomLayoutSlideUp.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        super.onAnimationStart(animation)
                        bottomLayout!!.visibility = View.VISIBLE
                    }
                })

                // combine animations for bottomLayout
                // (animate white dot and bottomLayout slide-up
                val bottomLayoutAnim = AnimatorSet()
                bottomLayoutAnim
                        .play(zoomCommonView(icCircleWhiteAnchor1, SCALE_TYPE_CENTER, 0f, 1f))
                        .with(scaleToZero(icCircleWhiteAnchor2))
                        .with(scaleToZero(icCircleWhiteAnchor3))
                        .after(bottomLayoutSlideUp)

                /*
                 * combine animation sets for introGroup1 and bottomLayout
                 */
                val introText1Anim = ObjectAnimator.ofFloat<View>(introText1, View.ALPHA, 1f)
                introText1Anim.setDuration((FADE_TIME_LONG * 2).toLong())
                introText1Anim.setInterpolator(AccelerateInterpolator())
                introText1Anim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationStart(animation: Animator) {
                        super.onAnimationStart(animation)
                        bottomLayoutAnim.start()
                    }
                })

                /*
                 * start animating the whole lot
                 */
                val animatorSet = AnimatorSet()
                animatorSet.playSequentially(redCircleAnim, iphoneAnim, icShieldAnim, icEyeGreenAnim,
                        icTwoKidneyAnim, icCrossYellowAnim, icSpeechBubbleAnim, introText1Anim)
                animatorSet.start()
            }
        })
    }

    /*
     * define behaviors of views when scrolling between pages
     */
    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {

        // set up fadeOutAlpha and fadeInAlpha values when scrolling.
        var fadeOutAlpha = 1 - positionOffset * 2 //
        var fadeInAlpha = ((positionOffset - 0.5) * 2).toFloat()

        // avoid negative values
        if (fadeOutAlpha < 0) {
            fadeOutAlpha = 0f
        }

        // avoid negative values
        if (fadeInAlpha < 0) {
            fadeInAlpha = 0f
        }

        /*
         * if position = 0 (slide 1 selected)
         */
        if (position == 0) {

            // when users scroll to the left, fade out slide 1 views
            fadedViewGroupSlide1.alpha = fadeOutAlpha
            iphoneImg!!.alpha = fadeOutAlpha
            icCircleWhiteAnchor1!!.scaleX = fadeOutAlpha
            icCircleWhiteAnchor1!!.scaleY = fadeOutAlpha

            // when users scroll to the left, fade in slide 2 views
            fadedViewGroupSlide2.alpha = fadeInAlpha
            icCircleWhiteAnchor2!!.scaleX = fadeInAlpha
            icCircleWhiteAnchor2!!.scaleY = fadeInAlpha
        }

        /*
         * if position = 1 (slide 2 selected)
         */
        if (position == 1) {

            // when users scroll to the left, fade out slide 2 views and vice versa
            fadedViewGroupSlide2.alpha = fadeOutAlpha
            icCircleWhiteAnchor2!!.scaleX = fadeOutAlpha
            icCircleWhiteAnchor2!!.scaleY = fadeOutAlpha

            // when users scroll to the left, fade in slide 3 views and vice versa
            //slide3IconGroup.setAlpha(fadeInAlpha);
            fadedViewGroupSlide3.alpha = fadeInAlpha
            icCircleWhiteAnchor3!!.scaleX = fadeInAlpha
            icCircleWhiteAnchor3!!.scaleY = fadeInAlpha
        }
    }

    /*
     * define behaviors of views when a page is selected
     */
    override fun onPageSelected(position: Int) {

        // if slide 2 selected, and this is the first time user navigate to, animate slide 2 views
        if (position == 1 && isSlide2FirstTime) {

            animateSlide2()
        }

        // if slide 3 selected, and this is the first time user navigate to, animate slide 3 views
        if (position == 2 && isSlide3FirstTime) {

            animateSlide3()
        }
    }

    /*
     * do something when page scroll state changed
     */
    override fun onPageScrollStateChanged(state: Int) {

    }

    /*
     * method to animate Slide 2
     */
    private fun animateSlide2() {

        // make isSlide2FirstTime false so next time when users navigate to this slide, it won't run animations
        isSlide2FirstTime = false

        // call getOverlay().add() method on rootView to add some child views that initially
        // belongs to an inner layout/container. This is to help move or scale views outside
        // the inner container
        fadedViewGroupSlide2.overlay.add(personSmallImg6!!)
        fadedViewGroupSlide2.overlay.add(personSmallImg3!!)
        fadedViewGroupSlide2.overlay.add(personSmallImg5!!)
        fadedViewGroupSlide2.overlay.add(personSmallImg2!!)
        fadedViewGroupSlide2.overlay.add(personSmallImg4!!)
        fadedViewGroupSlide2.overlay.add(personSmallImg1!!)
        fadedViewGroupSlide2.overlay.add(personBigImg!!)
        fadedViewGroupSlide2.overlay.add(icSlash1!!)
        fadedViewGroupSlide2.overlay.add(icSlash2!!)
        fadedViewGroupSlide2.overlay.add(icSlash3!!)
        fadedViewGroupSlide2.overlay.add(icKidneySlide2!!)
        fadedViewGroupSlide2.overlay.add(circleFrameSlide2!!)


        /*
         * animate persons icons
         */
        val personsAnim = AnimatorSet()
        personsAnim.playSequentially(
                zoomCommonView(personBigImg, SCALE_TYPE_CENTER, 0f, 1f),
                zoomCommonView(personSmallImg1, SCALE_TYPE_CENTER, 0f, 1f),
                zoomCommonView(personSmallImg4, SCALE_TYPE_CENTER, 0f, 1f),
                zoomCommonView(personSmallImg2, SCALE_TYPE_CENTER, 0f, 1f),
                zoomCommonView(personSmallImg5, SCALE_TYPE_CENTER, 0f, 1f),
                zoomCommonView(personSmallImg3, SCALE_TYPE_CENTER, 0f, 1f),
                zoomCommonView(personSmallImg6, SCALE_TYPE_CENTER, 0f, 1f))

        /*
         * animate the 3 slash and the kidney
         */
        val objectAnimator = ObjectAnimator.ofFloat<View>(icSlash1, View.ALPHA, 0f, 1f)
        val slashAndKidneyAnim = AnimatorSet()
        slashAndKidneyAnim
                .play(objectAnimator)
                .with(ObjectAnimator.ofFloat<View>(icSlash2, View.ALPHA, 0f, 1f))
                .with(ObjectAnimator.ofFloat<View>(icSlash3, View.ALPHA, 0f, 1f))
                .with(ObjectAnimator.ofFloat<View>(icKidneySlide2, View.ALPHA, 0f, 1f))
        slashAndKidneyAnim.duration = FADE_TIME.toLong()
        slashAndKidneyAnim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                icSlash1!!.visibility = View.VISIBLE
                icSlash2!!.visibility = View.VISIBLE
                icSlash3!!.visibility = View.VISIBLE
                icKidneySlide2!!.visibility = View.VISIBLE
            }
        })

        /*
         * animate the text
         */
        val introText2Anim = ObjectAnimator.ofFloat<View>(introText2, View.ALPHA, 1f)
        introText2Anim.setDuration((FADE_TIME_LONG * 2).toLong())
        introText2Anim.setInterpolator(AccelerateInterpolator())

        /*
         * start animating the whole lot
         */
        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(personsAnim, slashAndKidneyAnim, introText2Anim)
        animatorSet.start()
    }

    /*
     * method to animate Slide 3
     */
    private fun animateSlide3() {

        // make isSlide3FirstTime false so next time when users navigate to this slide, it won't run animations
        isSlide3FirstTime = false

        // call getOverlay().add() method on rootView to add some child views that initially
        // belongs to an inner layout/container. This is to help move or scale views outside
        // the inner container
        fadedViewGroupSlide3.overlay.add(icConnector1!!)
        fadedViewGroupSlide3.overlay.add(icConnector2!!)
        fadedViewGroupSlide3.overlay.add(icConnector3!!)
        fadedViewGroupSlide3.overlay.add(icConnector4!!)
        fadedViewGroupSlide3.overlay.add(icKidneySlide3!!)

        // rotate icConnector icons properly when creating slide 3 views
        icConnector1!!.rotation = 100f
        icConnector2!!.rotation = 260f
        icConnector3!!.rotation = 160f
        icConnector4!!.rotation = 10f

        /*
         * animate the text
         */
        val introText2Anim = ObjectAnimator.ofFloat<View>(introText3, View.ALPHA, 1f)
        introText2Anim.setDuration((FADE_TIME_LONG * 2).toLong())
        introText2Anim.setInterpolator(AccelerateInterpolator())
        introText2Anim.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                getStartedBtn!!.setBackgroundResource(R.drawable.rectangle_rounded_white)
                getStartedBtn!!.setTextColor(resources.getColor(R.color.white))
            }
        })

        /*
         * animate kidney icon and the whole lot
         */
        val kidneyAnim = AnimatorSet()
        kidneyAnim.playSequentially(
                zoomCommonView(icKidneySlide3, SCALE_TYPE_CENTER, 0f, 1f).setDuration((SCALE_TIME * 2).toLong()),
                zoomCommonView(icConnector1, SCALE_TYPE_BOTTOM_CENTER, 0f, 1.5f, 1f),
                zoomCommonView(icConnector2, SCALE_TYPE_BOTTOM_CENTER, 0f, 1.5f, 1f),
                zoomCommonView(icConnector3, SCALE_TYPE_BOTTOM_CENTER, 0f, 1.5f, 1f),
                zoomCommonView(icConnector4, SCALE_TYPE_BOTTOM_CENTER, 0f, 1.5f, 1f),
                introText2Anim)
        kidneyAnim.start()
    }

    /*
     * method to zoom common views: scale view to zero first, then scale to wanted values
     */
    private fun zoomCommonView(view: View, scaleType: String, vararg values: Float): AnimatorSet {

        val animatorSet: AnimatorSet

        // set pivot X and Y for the scaled view. Pivot values must be calculated basing on
        // the view height/width where 0,0 means the pivot points are top/left of the view;
        // full height and full width means bottom/right of the views...

        if (scaleType == SCALE_TYPE_CENTER) {

            view.pivotX = (view.width / 2).toFloat()
            view.pivotY = (view.height / 2).toFloat()
        }
        if (scaleType == SCALE_TYPE_TOP_RIGHT) {

            view.pivotX = view.width.toFloat()
            view.pivotY = 0f
        }
        if (scaleType == SCALE_TYPE_TOP_LEFT) {

            view.pivotX = 0f
            view.pivotY = 0f
        }
        if (scaleType == SCALE_TYPE_BOTTOM_LEFT) {

            view.pivotX = 0f
            view.pivotY = view.height.toFloat()
        }

        if (scaleType == SCALE_TYPE_BOTTOM_CENTER) {

            view.pivotY = view.height.toFloat()
        }

        /*
         * scale view
         */
        animatorSet = AnimatorSet()
        animatorSet
                .play(ObjectAnimator.ofFloat(view, View.SCALE_X, *values))
                .with(ObjectAnimator.ofFloat(view, View.SCALE_Y, *values))
                .after(scaleToZero(view))
        animatorSet.duration = SCALE_TIME.toLong()

        return animatorSet
    }

    /*
     * a helper method to scale a view to zero
     */
    private fun scaleToZero(view: View?): AnimatorSet {

        val animatorSet = AnimatorSet()
        animatorSet
                .play(ObjectAnimator.ofFloat<View>(view, View.SCALE_X, 0f))
                .with(ObjectAnimator.ofFloat<View>(view, View.SCALE_Y, 0f))
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                view!!.visibility = View.VISIBLE
            }
        })
        animatorSet.duration = SCALE_TO_ZERO_TIME.toLong()
        return animatorSet
    }

    /*
     * customise PageTransformer class to define behaviors/animations/transitions of views/slides
     */
    inner class PageTransformer : ViewPager.PageTransformer {

        override fun transformPage(view: View, position: Float) {

            // if position < -1 (this page is way off-screen to the left)
            if (position < -1) { // [-Infinity,-1)

                // and if first time navigating to slide 3, set up slide 3 text views
                // we set up them here because slide 3 layout wont be inflated until users navigate to
                if (isSlide3FirstTime) {
                    introText3.alpha = 0f

                    introHeader3.typeface = adelleSansExtraBold
                    introBody3.typeface = adelleSansNormal
                }

            }
        }
    }

    companion object {

        // constants: anim time
        private val SCALE_TIME = 100
        private val FADE_TIME = 250
        private val MOVE_TIME = 100
        private val FADE_TIME_LONG = 500
        private val DELAY_TIME_REGULAR = 50
        private val SCALE_TO_ZERO_TIME = 1
        private val OFFSET_X = 80
        private val OFFSET_Y = 80

        // constants: scale type
        private val SCALE_TYPE_CENTER = "center"
        private val SCALE_TYPE_TOP_RIGHT = "top_right"
        private val SCALE_TYPE_TOP_LEFT = "top_left"
        private val SCALE_TYPE_BOTTOM_LEFT = "bottom_left"
        private val SCALE_TYPE_BOTTOM_CENTER = "bottom_center"
    }
}
