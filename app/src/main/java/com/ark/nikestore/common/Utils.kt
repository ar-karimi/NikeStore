package com.ark.nikestore.common

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.SpannableString
import android.text.style.RelativeSizeSpan
import android.view.MotionEvent
import android.view.View
import androidx.browser.customtabs.CustomTabsIntent
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import timber.log.Timber

fun Context.dpToPx(dp: Int): Int {
    return (dp * resources.displayMetrics.density).toInt()
}

fun Context.pxToDp(px: Int): Int {
    return (px / resources.displayMetrics.density).toInt()
}

fun formatPrice(price: Number, unitRelativeSizeFactor: Float = 0.7f): SpannableString {
    val priceWithCommas = "%,d".format(price)
    val currencyLabel="تومان"
    val spannableString = SpannableString("$priceWithCommas $currencyLabel")
    spannableString.setSpan(
        RelativeSizeSpan(unitRelativeSizeFactor),
        spannableString.indexOf(currencyLabel),
        spannableString.length,
        SpannableString.SPAN_EXCLUSIVE_EXCLUSIVE
    )
    return spannableString
}

@SuppressLint("ClickableViewAccessibility")
fun View.implementSpringAnimationTrait() {
    val scaleXAnim = SpringAnimation(this, DynamicAnimation.SCALE_X, 0.90f)
    val scaleYAnim = SpringAnimation(this, DynamicAnimation.SCALE_Y, 0.90f)

    setOnTouchListener { v, event ->
        Timber.i(event.action.toString())
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                scaleXAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                scaleXAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                scaleXAnim.start()

                scaleYAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                scaleYAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                scaleYAnim.start()

            }

            MotionEvent.ACTION_UP,
            MotionEvent.ACTION_CANCEL -> {
                scaleXAnim.cancel()
                scaleYAnim.cancel()
                val reverseScaleXAnim = SpringAnimation(this, DynamicAnimation.SCALE_X, 1f)
                reverseScaleXAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                reverseScaleXAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                reverseScaleXAnim.start()

                val reverseScaleYAnim = SpringAnimation(this, DynamicAnimation.SCALE_Y, 1f)
                reverseScaleYAnim.spring.stiffness = SpringForce.STIFFNESS_LOW
                reverseScaleYAnim.spring.dampingRatio = SpringForce.DAMPING_RATIO_LOW_BOUNCY
                reverseScaleYAnim.start()

            }
        }
        false
    }
}

fun openUrlInCustomTab(context: Context, url: String) {
    try {
        val uri = Uri.parse(url)
        val intentBuilder = CustomTabsIntent.Builder()
        intentBuilder.setStartAnimations(context, android.R.anim.fade_in, android.R.anim.fade_out)
        intentBuilder.setExitAnimations(context, android.R.anim.fade_in, android.R.anim.fade_out)
        val customTabsIntent = intentBuilder.build()
        customTabsIntent.intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        customTabsIntent.launchUrl(context, uri)

    } catch (e: Exception) {}

}