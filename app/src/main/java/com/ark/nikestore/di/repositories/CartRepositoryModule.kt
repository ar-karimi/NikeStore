package com.ark.nikestore.di.repositories

import com.ark.nikestore.data.repo.CartRepository
import com.ark.nikestore.data.repo.CartRepositoryImpl
import com.ark.nikestore.data.repo.source.CartDataSource
import com.ark.nikestore.data.repo.source.CartRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class CartRepositoryModule {

    @Binds
    abstract fun provideCartRepository(cartRepositoryImpl: CartRepositoryImpl): CartRepository

    @Binds
    abstract fun provideCartRemoteDataSource(cartRemoteDataSource: CartRemoteDataSource): CartDataSource
}