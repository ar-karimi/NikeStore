package com.ark.nikestore.services

import com.ark.nikestore.view.BaseImageView
import com.facebook.drawee.view.SimpleDraweeView
import java.lang.IllegalStateException

class FrescoImageLoadingService: ImageLoadingService {
    override fun load(imageView: BaseImageView, imageUrl: String) {
        if (imageView is SimpleDraweeView)
            imageView.setImageURI(imageUrl)
        else
            throw IllegalStateException("imageView must be instance of SimpleDraweeView")
    }
}