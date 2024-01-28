package com.ark.nikestore.di.repositories

import com.ark.nikestore.data.repo.OrderRepository
import com.ark.nikestore.data.repo.OrderRepositoryImpl
import com.ark.nikestore.data.repo.source.OrderDataSource
import com.ark.nikestore.data.repo.source.OrderRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class OrderRepositoryModule {

    @Binds
    @Singleton
    abstract fun provideOrderRepository(orderRepositoryImpl: OrderRepositoryImpl): OrderRepository

    @Binds
    @Singleton
    abstract fun provideOrderRemoteDataSource(orderRemoteDataSource: OrderRemoteDataSource): OrderDataSource
}