package com.ark.nikestore.di.repositories.productRepository

import com.ark.nikestore.data.repo.ProductRepository
import com.ark.nikestore.data.repo.ProductRepositoryImpl
import com.ark.nikestore.data.repo.source.ProductDataSource
import com.ark.nikestore.data.repo.source.ProductRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class ProductRepositoryModule {

    @Binds
    abstract fun provideProductRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository

    @ProductRemoteDataSourceQualifier
    @Binds
    abstract fun provideProductRemoteDataSource(productRemoteDataSource: ProductRemoteDataSource): ProductDataSource

    //This is Implemented by Room and we write it in AppModule
    /*@ProductLocalDataSourceQualifier
    @Binds
    abstract fun provideProductLocalDataSource(productLocalDataSource: ProductLocalDataSource): ProductDataSource*/

}