package com.trien.dnanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import kotlinx.android.synthetic.main.activity_fresh.*

class FreshActivity : AppCompatActivity() {

    private val mShortAnimationDuration = 200
    internal var originalAlpha = 0f
    internal var destinationAlpha = 1f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fresh)

        repeatProgressBarInfinitely()

        transitionLayout()
    }

    // this method is just to mimic fade-in fade-out transition between fragments/layouts
    private fun transitionLayout() {

        // Set fragment2 (content view) to 0% opacity but visible, so that it is visible
        // (but fully transparent) during the animation.
        fragment2.alpha = 0f

        // Animate fragment1 to 0% opacity. After the animation ends, repeat it for a second time to
        // mimic an app loading data from internet
        // set its visibility to GONE as an optimization step to get rid of the whole progress bar
        fragment1.animate()
                .alpha(1f)
                .setDuration(4000)
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
                                    }
                                })


                        fragment2.animate()
                                .alpha(1f)
                                .setDuration(2000)
                                .setListener(null)
                    }
                })


    }

    private fun repeatProgressBarInfinitely() {
        progressBar.alpha = destinationAlpha
        imageView1.alpha = originalAlpha
        imageView2.alpha = originalAlpha
        imageView3.alpha = originalAlpha
        imageView4.alpha = originalAlpha
        imageView5.alpha = originalAlpha
        imageView6.alpha = originalAlpha


        imageView1.visibility = View.INVISIBLE
        imageView2.visibility = View.INVISIBLE
        imageView3.visibility = View.INVISIBLE
        imageView4.visibility = View.INVISIBLE
        imageView5.visibility = View.INVISIBLE
        imageView6.visibility = View.INVISIBLE
        progressBar.visibility = View.VISIBLE

        imageView1.animate()
                .alpha(destinationAlpha)
                .setDuration(mShortAnimationDuration.toLong())
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        imageView1.visibility = View.VISIBLE
                        imageView2.animate()
                                .alpha(destinationAlpha)
                                .setDuration(mShortAnimationDuration.toLong())
                                .setListener(object : AnimatorListenerAdapter() {
                                    override fun onAnimationEnd(animation: Animator) {
                                        imageView2.visibility = View.VISIBLE
                                        imageView3.animate()
                                                .alpha(destinationAlpha)
                                                .setDuration(mShortAnimationDuration.toLong())
                                                .setListener(object : AnimatorListenerAdapter() {
                                                    override fun onAnimationEnd(animation: Animator) {
                                                        imageView3.visibility = View.VISIBLE
                                                        imageView4.animate()
                                                                .alpha(destinationAlpha)
                                                                .setDuration(mShortAnimationDuration.toLong())
                                                                .setListener(object : AnimatorListenerAdapter() {
                                                                    override fun onAnimationEnd(animation: Animator) {
                                                                        imageView4.visibility = View.VISIBLE
                                                                        imageView5.animate()
                                                                                .alpha(destinationAlpha)
                                                                                .setDuration(mShortAnimationDuration.toLong())
                                                                                .setListener(object : AnimatorListenerAdapter() {
                                                                                    override fun onAnimationEnd(animation: Animator) {
                                                                                        imageView5.visibility = View.VISIBLE
                                                                                        imageView6.animate()
                                                                                                .alpha(destinationAlpha)
                                                                                                .setDuration(mShortAnimationDuration.toLong())
                                                                                                .setListener(object : AnimatorListenerAdapter() {
                                                                                                    override fun onAnimationEnd(animation: Animator) {
                                                                                                        imageView6.visibility = View.VISIBLE
                                                                                                        progressBar.animate()
                                                                                                                .alpha(destinationAlpha)
                                                                                                                .setDuration(mShortAnimationDuration.toLong())
                                                                                                                .setListener(object : AnimatorListenerAdapter() {
                                                                                                                    override fun onAnimationEnd(animation: Animator) {
                                                                                                                        progressBar.visibility = View.INVISIBLE
                                                                                                                        repeatProgressBarInfinitely()
                                                                                                                    }
                                                                                                                })
                                                                                                    }
                                                                                                })
                                                                                    }
                                                                                })
                                                                    }
                                                                })
                                                    }
                                                })
                                    }
                                })
                    }
                })
    }
}
