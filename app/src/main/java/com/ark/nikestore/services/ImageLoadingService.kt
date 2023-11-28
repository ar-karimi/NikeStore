package com.ark.nikestore.services

import com.ark.nikestore.view.BaseImageView

interface ImageLoadingService {
    fun load(imageView: BaseImageView, imageUrl: String)
}