package com.example.andersenhw.ui.home.cardView.animations.interpolators

import android.animation.TypeEvaluator
import android.util.Log
import timber.log.Timber
import kotlin.math.sin
import kotlin.math.sqrt

class ShakeFadeEvaluator(
    private val initialAmplitude: Float,
    private val periodCount: Int
) : TypeEvaluator<Float> {

    private companion object {
        const val PI = 3.141592653589793f
    }

    override fun evaluate(fraction: Float, startValue: Float?, endValue: Float?): Float {
        Timber.tag("Evaluator").i("fraction: %f".format(fraction))
        Timber.tag("Evaluator").i("start: %f".format(startValue))
        Timber.tag("Evaluator").i("end: %f".format(endValue))
        return sin(sqrt(fraction) * 2f * PI * periodCount.toFloat()) * initialAmplitude
    }
}