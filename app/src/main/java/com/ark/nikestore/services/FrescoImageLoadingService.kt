package com.ark.nikestore.services

import android.graphics.drawable.Drawable
import android.net.Uri
import com.ark.nikestore.view.customViews.CustomImageView
import com.facebook.common.util.UriUtil
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.drawee.interfaces.DraweeController
import com.facebook.drawee.view.SimpleDraweeView
import com.facebook.imagepipeline.request.ImageRequestBuilder

class FrescoImageLoadingService: ImageLoadingService {

    override fun loadImage(imageView: CustomImageView, imageUrl: String) {
        if (imageView is SimpleDraweeView) {
            val request = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageUrl))
                .setProgressiveRenderingEnabled(true)
                .build()
            val controller: DraweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(imageView.controller)
                .build()
            imageView.controller = controller

            //or just imageView.setImageURI(imageUrl)
        } else
            throw IllegalArgumentException("Fresco only works with SimpleDraweeView")
    }
    override fun loadImage(imageView: CustomImageView, imageUrl: String, placeHolder: Int, errorDrawable: Int) {
        if (imageView is SimpleDraweeView) {
            val imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageUrl))
                .setProgressiveRenderingEnabled(true)
                .build()
            val controller: DraweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(imageView.controller)
                .setTapToRetryEnabled(false)
                .build()
            imageView.hierarchy.setPlaceholderImage(placeHolder)
            imageView.hierarchy.setFailureImage(errorDrawable)
            imageView.controller = controller
        } else
            throw IllegalArgumentException("Fresco only works with SimpleDraweeView")
    }
    override fun loadImage(imageView: CustomImageView, imageUrl: String, placeHolder: Drawable, errorDrawable: Drawable) {
        if (imageView is SimpleDraweeView) {
            val imageRequest = ImageRequestBuilder.newBuilderWithSource(Uri.parse(imageUrl))
                .setProgressiveRenderingEnabled(true)
                .build()
            val controller: DraweeController = Fresco.newDraweeControllerBuilder()
                .setImageRequest(imageRequest)
                .setOldController(imageView.controller)
                .setTapToRetryEnabled(false)
                .build()
            imageView.hierarchy.setPlaceholderImage(placeHolder)
            imageView.hierarchy.setFailureImage(errorDrawable)
            imageView.controller = controller
        } else
            throw IllegalArgumentException("Fresco only works with SimpleDraweeView")
    }

    override fun loadImage(imageView: CustomImageView, drawableResId: Int) {
        val uri = Uri.Builder()
            .scheme(UriUtil.LOCAL_RESOURCE_SCHEME) // "res"
            .path(drawableResId.toString())
            .build()
        imageView.setImageURI(uri)
    }
}