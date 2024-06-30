package com.example.utils.extension

import android.animation.Animator
import com.airbnb.lottie.LottieAnimationView

fun LottieAnimationView.onJsonAnimationEnd(action: () -> Unit) {
    addAnimatorListener(object : Animator.AnimatorListener {
        override fun onAnimationEnd(animation: Animator) {
            action()
        }

        override fun onAnimationStart(animation: Animator) {}
        override fun onAnimationCancel(animation: Animator) {}
        override fun onAnimationRepeat(animation: Animator) {}
    })
}