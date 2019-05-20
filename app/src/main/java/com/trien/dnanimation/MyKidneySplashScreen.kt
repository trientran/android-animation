package com.trien.dnanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Handler
import androidx.core.content.res.ResourcesCompat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_my_kidney_splash_screen.*

class MyKidneySplashScreen : AppCompatActivity() {

    // Splash screen timeout
    internal var splashTimeOut: Long = 500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_my_kidney_splash_screen)

        // set up views
        setUpViews()
        //animate views
        animateViews()
    }

    /*
     * set up views
     */
    private fun setUpViews() {

        // set font type for splashHeader
        splashHeader.typeface = ResourcesCompat.getFont(this, R.font.adelle_sans_bold)

        // set Alpha and visibility where applicable for animated views
        splashHeader.alpha = 0f
        bottomLogoImg.alpha = 0f
        person1Img.alpha = 0f
        person2Img.alpha = 0f
        twoPersonsImg.alpha = 0f
        icHeart.visibility = View.INVISIBLE
        icTwoHalves.alpha = 0f
    }

    /*
     * animate views
     */
    private fun animateViews() {

        person1Img.viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                person1Img.viewTreeObserver.removeOnGlobalLayoutListener(this)

                /*
                 * animate header text and bottom logo
                 */
                val topAndBottomAnim = AnimatorSet()
                topAndBottomAnim
                        .play(ObjectAnimator.ofFloat<View>(splashHeader, View.ALPHA, 1f))
                        .with(ObjectAnimator.ofFloat<View>(bottomLogoImg, View.ALPHA, 1f))
                topAndBottomAnim.duration = FADE_TIME.toLong()

                /*
                 * animate person 1
                 */
                val person1Anim = ObjectAnimator.ofFloat<View>(person1Img, View.ALPHA, 1f)
                person1Anim.setDuration(FADE_TIME.toLong())

                /*
                 * animate person 2
                 */
                val person2ImgX = person2Img.x
                val person2Anim = AnimatorSet()
                person2Anim
                        .play(ObjectAnimator.ofFloat(
                                person2Img, View.X, person2ImgX - person1Img.width / 2, person2ImgX))
                        .with(ObjectAnimator.ofFloat<View>(person2Img, View.ALPHA, 1f))
                person2Anim.duration = FADE_TIME.toLong()
                person2Anim.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        ObjectAnimator.ofFloat<View>(twoPersonsImg, View.ALPHA, 1f).setDuration((FADE_TIME / 2).toLong()).start()
                    }
                })

                /*
                 * animate heart and kidney (2 halves) icons
                 */
                val icHeartX = icHeart.x
                val icHeartY = icHeart.y
                val organAnim = AnimatorSet()
                organAnim
                        .play(ObjectAnimator.ofFloat<View>(icTwoHalves, View.ALPHA, 1f))
                        .with(ObjectAnimator.ofFloat<View>(
                                icHeart, View.X, rootView.width / 2f, icHeartX + OFFSET_X, icHeartX))
                        .with(ObjectAnimator.ofFloat<View>(
                                icHeart, View.Y, rootView.height / 2f, icHeartY - OFFSET_Y, icHeartY))
                        .with(ObjectAnimator.ofFloat<View>(icHeart, View.SCALE_X, 0f, 2f, 1f))
                        .with(ObjectAnimator.ofFloat<View>(icHeart, View.SCALE_Y, 0f, 2f, 1f))
                        .after(scaleToZero(icHeart))
                organAnim.duration = FADE_TIME.toLong()


                /*
                 * start animating the whole lot
                 */
                val animatorSet = AnimatorSet()
                animatorSet.playSequentially(topAndBottomAnim, person1Anim, person2Anim, organAnim)
                animatorSet.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        // navigate to main activity after ending all animations
                        navigateToMainActivity(splashTimeOut)
                    }
                })

                animatorSet.start()
            }
        })
    }

    /*
     * method set a timer before switching to other activity
     */
    private fun navigateToMainActivity(splashTimeOut: Long) {
        // create a timer to switch to other activity
        Handler().postDelayed({
            // This method will be executed once the timer is over
            // Start MyKidneyIntroActivity activity
            val i = Intent(this@MyKidneySplashScreen, MyKidneyIntroActivity::class.java)
            startActivity(i)

            // close this activity
            finish()
        }, splashTimeOut)
    }

    /*
     * a helper method to scale a view to zero
     */
    private fun scaleToZero(view: View): AnimatorSet {

        val animatorSet = AnimatorSet()
        animatorSet
                .play(ObjectAnimator.ofFloat<View>(view, View.SCALE_X, 0f))
                .with(ObjectAnimator.ofFloat<View>(view, View.SCALE_Y, 0f))
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                super.onAnimationEnd(animation)
                view.visibility = View.VISIBLE
            }
        })
        animatorSet.duration = SCALE_TO_ZERO_TIME.toLong()
        return animatorSet
    }

    companion object {

        // constants
        private val SCALE_TIME = 100
        private val FADE_TIME = 1000
        private val SCALE_TO_ZERO_TIME = 1
        private val OFFSET_X = 80
        private val OFFSET_Y = 30
    }
}
