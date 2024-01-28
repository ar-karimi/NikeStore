package com.ark.nikestore.services

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import com.ark.nikestore.view.customViews.CustomImageView

interface ImageLoadingService {
    fun loadImage(imageView: CustomImageView, imageUrl: String)
    fun loadImage(imageView: CustomImageView, imageUrl: String, @DrawableRes placeHolder: Int, @DrawableRes errorDrawable: Int)
    fun loadImage(imageView: CustomImageView, imageUrl: String, placeHolder: Drawable, errorDrawable: Drawable)
    fun loadImage(imageView: CustomImageView, @DrawableRes drawableResId: Int)
}