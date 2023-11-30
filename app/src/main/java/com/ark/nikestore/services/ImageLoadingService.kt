package com.ark.nikestore.services

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import com.ark.nikestore.view.customViews.BaseImageView

interface ImageLoadingService {
    fun loadImage(imageView: BaseImageView, imageUrl: String)
    fun loadImage(imageView: BaseImageView, imageUrl: String, @DrawableRes placeHolder: Int, @DrawableRes errorDrawable: Int)
    fun loadImage(imageView: BaseImageView, imageUrl: String, placeHolder: Drawable, errorDrawable: Drawable)
    fun loadImage(imageView: BaseImageView, @DrawableRes drawableResId: Int)
}