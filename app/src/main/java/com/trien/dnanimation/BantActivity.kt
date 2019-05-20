package com.trien.dnanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_bant.*

class BantActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bant)

        upperBubble.visibility = View.INVISIBLE
        lowerBubble.visibility = View.INVISIBLE
        upperAnchor.visibility = View.INVISIBLE
        lowerAnchor.visibility = View.INVISIBLE

        upperWhiteDot1.alpha = 0f
        upperWhiteDot2.alpha = 0f
        upperWhiteDot3.alpha = 0f
        upperWhiteDot4.alpha = 0f
        lowerWhiteDot1.alpha = 0f
        lowerWhiteDot2.alpha = 0f
        lowerWhiteDot3.alpha = 0f
        lowerWhiteDot4.alpha = 0f

        // Set fragment2 (content view) to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        fragment2.alpha = 0f

        zoomBtn.setOnClickListener {
            animateBubbles()
            transitionLayout()
        }
    }

    private fun transitionLayout() {


        /*// Set fragment1 (progress bar) visible and 100% opacity
        fragment1.setVisibility(View.VISIBLE);
        fragment1.setAlpha(1f);*/

        // Animate fragment1 to 0% opacity. After the animation ends, repeat it for a second time to
        // mimic an app loading data from internet
        // set its visibility to GONE as an optimization step to get rid of the whole progress bar
        fragment1.animate()
                .alpha(1f)
                .setDuration(18000)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)

                        fragment1.animate()
                                .alpha(0f)
                                .setDuration(2000)
                                .setListener(object : AnimatorListenerAdapter() {
                                    override fun onAnimationEnd(animation: Animator) {
                                        super.onAnimationEnd(animation)
                                        // set progressBar visibility to Gone
                                        fragment1.visibility = View.GONE
                                        zoomBtn.visibility = View.GONE
                                    }
                                })


                        fragment2.animate()
                                .alpha(1f)
                                .setDuration(2000)
                                .setListener(null)

                    }
                })


    }

    private fun animateBubbles() {


        val anchorWidth: Int
        val anchorHeight: Int
        val width0: Int
        val height0: Int

        val scaleX1: Float
        val scaleX2: Float
        val scaleX3: Float

        val scaleY1: Float
        val scaleY2: Float

        anchorWidth = lowerAnchor.width
        anchorHeight = lowerAnchor.height

        width0 = lowerBubble.width
        height0 = lowerBubble.height

        scaleX1 = (anchorWidth / 20 * 17 / width0).toFloat()
        scaleY1 = (anchorHeight / 16 * 20 / height0).toFloat()

        scaleX2 = (anchorWidth / 20 * 15 / width0).toFloat()
        scaleY2 = (anchorHeight / height0).toFloat()

        scaleX3 = (anchorWidth / width0).toFloat()

        animateLowerBubble(scaleX1, scaleX2, scaleX3, scaleY1, scaleY2)

    }

    private fun animateLowerBubble(scaleX1: Float, scaleX2: Float, scaleX3: Float, scaleY1: Float, scaleY2: Float) {

        lowerBubble.visibility = View.VISIBLE

        // set pivot X and Y for the scaled speech bubbles. pivot values must be calculated basing on
        // the view height/width where 0,0 means the pivot points are top/left of the view;
        // full height and full width means bottom/right of the views...
        lowerBubble.pivotX = 0f
        lowerBubble.pivotY = lowerBubble.height.toFloat()


        // 1st time zoom. values param to input is a multiplication of the original scale.
        // value = 1 means no scaling, 2 means scale to double size...
        // if a literal number specified, it needs to be in 0f, 1f, 2f... format
        val zoom1 = AnimatorSet()
        zoom1
                .play(ObjectAnimator.ofFloat(lowerBubble, View.SCALE_X, 0f, scaleX1, scaleX2))
                .with(ObjectAnimator.ofFloat<View>(lowerBubble, View.SCALE_Y, 0f, scaleY1, scaleY2))
        zoom1.duration = (ZOOM_TIME * 2).toLong()

        // 2nd time zoom
        val zoom2 = AnimatorSet()
        zoom2
                .play(ObjectAnimator.ofFloat(lowerBubble, View.SCALE_X, scaleX3))
        zoom2.duration = ZOOM_TIME.toLong()


        /*
         * set up animation for white dots
         */
        val fadeAnim1 = ObjectAnimator.ofFloat(lowerWhiteDot1, View.ALPHA, 1f)
        fadeAnim1.duration = FADE_TIME.toLong()

        val fadeAnim2 = ObjectAnimator.ofFloat(lowerWhiteDot2, View.ALPHA, 1f)
        fadeAnim2.duration = FADE_TIME.toLong()
        fadeAnim2.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                ObjectAnimator.ofFloat(lowerWhiteDot1, View.ALPHA, 0.8f).setDuration(FADE_TIME.toLong()).start()
            }
        })

        val fadeAnim3 = ObjectAnimator.ofFloat(lowerWhiteDot3, View.ALPHA, 1f)
        fadeAnim3.duration = FADE_TIME.toLong()
        fadeAnim3.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                ObjectAnimator.ofFloat(lowerWhiteDot1, View.ALPHA, 0.5f).setDuration(FADE_TIME.toLong()).start()
                ObjectAnimator.ofFloat(lowerWhiteDot2, View.ALPHA, 0.8f).setDuration(FADE_TIME.toLong()).start()
            }
        })

        val fadeAnim4 = ObjectAnimator.ofFloat(lowerWhiteDot4, View.ALPHA, 1f)
        fadeAnim4.duration = FADE_TIME.toLong()
        fadeAnim4.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                ObjectAnimator.ofFloat(lowerWhiteDot1, View.ALPHA, 0.2f).setDuration(FADE_TIME.toLong()).start()
                ObjectAnimator.ofFloat(lowerWhiteDot2, View.ALPHA, 0.5f).setDuration(FADE_TIME.toLong()).start()
                ObjectAnimator.ofFloat(lowerWhiteDot3, View.ALPHA, 0.8f).setDuration(FADE_TIME.toLong()).start()
            }
        })

        val fadeAnim5 = ObjectAnimator.ofFloat(lowerWhiteDot3, View.ALPHA, 1f)
        fadeAnim5.duration = FADE_TIME.toLong()

        val fadeAnim6 = ObjectAnimator.ofFloat(lowerWhiteDot2, View.ALPHA, 1f)
        fadeAnim6.duration = FADE_TIME.toLong()
        fadeAnim6.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                animateUpperBubble(scaleX1, scaleX2, scaleX3, scaleY1, scaleY2)
            }
        })

        val fadeAnim7 = ObjectAnimator.ofFloat(lowerWhiteDot1, View.ALPHA, 1f)
        fadeAnim7.duration = FADE_TIME.toLong()

        val whiteDotAnimSet = AnimatorSet()
        whiteDotAnimSet.playSequentially(fadeAnim1, fadeAnim2, fadeAnim3, fadeAnim4, fadeAnim5, fadeAnim6, fadeAnim7)

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(zoom1, zoom2, whiteDotAnimSet)
        animatorSet.start()
    }

    /*
        * set up animation for upperBubble
        */
    private fun animateUpperBubble(scaleX1: Float, scaleX2: Float, scaleX3: Float, scaleY1: Float, scaleY2: Float) {

        upperBubble.visibility = View.VISIBLE

        // set pivot X and Y for the scaled speech bubbles. pivot values must be calculated basing on
        // the view height/width where 0,0 means the pivot points are top/left of the view;
        // full height and full width means bottom/right of the views...
        upperBubble.pivotX = 0f
        upperBubble.pivotY = upperBubble.height.toFloat()


        // 1st time zoom. values param to input is a multiplication of the original scale.
        // value = 1 means no scaling, 2 means scale to double size...
        val zoom1 = AnimatorSet()
        zoom1
                .play(ObjectAnimator.ofFloat<View>(upperBubble, View.SCALE_X, 0f, scaleX1, scaleX2))
                .with(ObjectAnimator.ofFloat<View>(upperBubble, View.SCALE_Y, 0f, scaleY1, scaleY2))
        zoom1.duration = (ZOOM_TIME * 2).toLong()

        // 2nd time zoom
        val zoom2 = AnimatorSet()
        zoom2
                .play(ObjectAnimator.ofFloat(upperBubble, View.SCALE_X, scaleX3))
        zoom2.duration = ZOOM_TIME.toLong()


        /*
         * set up animation for white dots
         */
        val fadeAnim1 = ObjectAnimator.ofFloat(upperWhiteDot1, View.ALPHA, 1f)
        fadeAnim1.duration = FADE_TIME.toLong()

        val fadeAnim2 = ObjectAnimator.ofFloat(upperWhiteDot2, View.ALPHA, 1f)
        fadeAnim2.duration = FADE_TIME.toLong()
        fadeAnim2.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                ObjectAnimator.ofFloat(upperWhiteDot1, View.ALPHA, 0.8f).setDuration(FADE_TIME.toLong()).start()
            }
        })


        val fadeAnim3 = ObjectAnimator.ofFloat(upperWhiteDot3, View.ALPHA, 1f)
        fadeAnim3.duration = FADE_TIME.toLong()
        fadeAnim3.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                ObjectAnimator.ofFloat(upperWhiteDot1, View.ALPHA, 0.5f).setDuration(FADE_TIME.toLong()).start()
                ObjectAnimator.ofFloat(upperWhiteDot2, View.ALPHA, 0.8f).setDuration(FADE_TIME.toLong()).start()
            }
        })

        val fadeAnim4 = ObjectAnimator.ofFloat(upperWhiteDot4, View.ALPHA, 1f)
        fadeAnim4.duration = FADE_TIME.toLong()
        fadeAnim4.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationStart(animation: Animator) {
                super.onAnimationStart(animation)
                ObjectAnimator.ofFloat(upperWhiteDot1, View.ALPHA, 0.2f).setDuration(FADE_TIME.toLong()).start()
                ObjectAnimator.ofFloat(upperWhiteDot2, View.ALPHA, 0.5f).setDuration(FADE_TIME.toLong()).start()
                ObjectAnimator.ofFloat(upperWhiteDot3, View.ALPHA, 0.8f).setDuration(FADE_TIME.toLong()).start()

                Log.d("trienLog", upperWhiteDot1.alpha.toString())
            }
        })

        val fadeAnim5 = ObjectAnimator.ofFloat(upperWhiteDot3, View.ALPHA, 1f)
        fadeAnim5.duration = FADE_TIME.toLong()

        val fadeAnim6 = ObjectAnimator.ofFloat(upperWhiteDot2, View.ALPHA, 1f)
        fadeAnim6.duration = FADE_TIME.toLong()

        val fadeAnim7 = ObjectAnimator.ofFloat(upperWhiteDot1, View.ALPHA, 1f)
        fadeAnim7.duration = FADE_TIME.toLong()

        val fadeAnim8 = ObjectAnimator.ofFloat(fragment1, View.ALPHA, 0f)
        fadeAnim8.duration = FADE_TIME_LONG.toLong()
        fadeAnim8.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)

                lowerBubble.visibility = View.INVISIBLE
                upperBubble.visibility = View.INVISIBLE
                upperWhiteDot1.alpha = 0f
                upperWhiteDot2.alpha = 0f
                upperWhiteDot3.alpha = 0f
                upperWhiteDot4.alpha = 0f
                lowerWhiteDot1.alpha = 0f
                lowerWhiteDot2.alpha = 0f
                lowerWhiteDot3.alpha = 0f
                lowerWhiteDot4.alpha = 0f

                //animateLowerBubble(scaleX1, scaleX2, scaleX3, scaleY1, scaleY2);

            }
        })

        val fadeAnim9 = ObjectAnimator.ofFloat(fragment1, View.ALPHA, 1f)
        fadeAnim9.duration = FADE_TIME.toLong()
        fadeAnim9.addListener(object : AnimatorListenerAdapter() {

            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)

                animateLowerBubble(scaleX1, scaleX2, scaleX3, scaleY1, scaleY2)

            }
        })


        val whiteDotAnimSet = AnimatorSet()
        whiteDotAnimSet.playSequentially(fadeAnim1, fadeAnim2, fadeAnim3, fadeAnim4, fadeAnim5, fadeAnim6, fadeAnim7, fadeAnim8, fadeAnim9)

        val animatorSet = AnimatorSet()
        animatorSet.playSequentially(zoom1, zoom2, whiteDotAnimSet)
        animatorSet.start()


        /*
         * set up animation for lowerBubble
         */
    }

    companion object {

        private val ZOOM_TIME = 500
        private val FADE_TIME = 200
        private val FADE_TIME_LONG = 1000
    }
}
