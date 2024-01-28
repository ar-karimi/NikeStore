package com.ark.nikestore.di

import com.ark.nikestore.services.ImageLoadingService
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface ImageLoadingServiceEntryPoint {
    fun provideImageLoadingService(): ImageLoadingService
}