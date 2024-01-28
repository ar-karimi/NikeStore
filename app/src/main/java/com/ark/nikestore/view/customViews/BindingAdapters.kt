package com.ark.nikestore.view.customViews

import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.ark.nikestore.common.formatPrice
import com.ark.nikestore.di.ImageLoadingServiceEntryPoint
import dagger.hilt.android.EntryPointAccessors

object BindingAdapters {

    @BindingAdapter(value = ["image_url", "place_holder", "error_drawable"], requireAll = false)
    @JvmStatic
    fun loadImage(imageView: CustomImageView, imageUrl: String, placeHolder: Drawable?, errorDrawable: Drawable?) {

        //Hilt can't support @Inject fields in Kotlin objects and must use this way:
        // (We also need an interface to declare with @EntryPoint to use here)
        val appContext = imageView.context?.applicationContext ?: throw IllegalStateException()
        val hiltEntryPoint = EntryPointAccessors.fromApplication(appContext, ImageLoadingServiceEntryPoint::class.java)
        val imageLoadingService = hiltEntryPoint.provideImageLoadingService()

        if (placeHolder != null && errorDrawable != null)
            imageLoadingService.loadImage(imageView, imageUrl, placeHolder, errorDrawable)
        else
            imageLoadingService.loadImage(imageView, imageUrl)
    }

    @BindingAdapter(value = ["price_in_toman"])
    @JvmStatic
    fun priceInToman(textView: TextView, price: Int) {

        textView.text = formatPrice(price)
    }

    @BindingAdapter("strike_through")
    @JvmStatic
    fun strikeThrough(textView: TextView, strikeThrough: Boolean) {
        if (strikeThrough) {
            textView.paintFlags = textView.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
        } else {
            textView.paintFlags = textView.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv()
        }
    }
}