package com.ark.nikestore.di.repositories

import com.ark.nikestore.data.repo.BannerRepository
import com.ark.nikestore.data.repo.BannerRepositoryImpl
import com.ark.nikestore.data.repo.source.BannerDataSource
import com.ark.nikestore.data.repo.source.BannerRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BannerRepositoryModule {

    @Binds
    abstract fun provideBannerRepository(bannerRepositoryImpl: BannerRepositoryImpl): BannerRepository

    @Binds
    abstract fun provideBannerRemoteDataSource(bannerRemoteDataSource: BannerRemoteDataSource): BannerDataSource
}