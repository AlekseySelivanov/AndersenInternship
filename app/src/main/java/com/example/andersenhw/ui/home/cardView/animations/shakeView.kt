package com.example.andersenhw.ui.home.cardView.animations

import android.animation.ObjectAnimator
import android.view.View
import com.example.andersenhw.ui.home.cardView.animations.interpolators.ShakeFadeEvaluator

const val DEFAULT_SHAKE_AMPLITUDE = 16f
const val DEFAULT_SHAKE_COUNT = 2
const val DEFAULT_SHAKE_DURATION = 800L

fun View.shake(
    shakeAmplitude: Float = DEFAULT_SHAKE_AMPLITUDE,
    shakeCount: Int = DEFAULT_SHAKE_COUNT,
    animDuration: Long = DEFAULT_SHAKE_DURATION
) {
    ObjectAnimator.ofFloat(this, View.TRANSLATION_X, 0f, 0f)
        .apply {
            setEvaluator(ShakeFadeEvaluator(shakeAmplitude, shakeCount))
            duration = animDuration
            start()
        }
}

