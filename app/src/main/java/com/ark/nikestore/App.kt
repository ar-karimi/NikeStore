package com.ark.nikestore

import android.app.Application
import android.os.Bundle
import com.ark.nikestore.data.repo.BannerRepository
import com.ark.nikestore.data.repo.BannerRepositoryImpl
import com.ark.nikestore.data.repo.CartRepository
import com.ark.nikestore.data.repo.CartRepositoryImpl
import com.ark.nikestore.data.repo.CommentRepository
import com.ark.nikestore.data.repo.CommentRepositoryImpl
import com.ark.nikestore.data.repo.ProductRepository
import com.ark.nikestore.data.repo.ProductRepositoryImpl
import com.ark.nikestore.data.repo.source.BannerRemoteDataSource
import com.ark.nikestore.data.repo.source.CartRemoteDataSource
import com.ark.nikestore.data.repo.source.CommentRemoteDataSource
import com.ark.nikestore.data.repo.source.ProductLocalDataSource
import com.ark.nikestore.data.repo.source.ProductRemoteDataSource
import com.ark.nikestore.feature.list.ProductListViewModel
import com.ark.nikestore.feature.home.HomeViewModel
import com.ark.nikestore.feature.product.ProductDetailViewModel
import com.ark.nikestore.feature.product.comment.CommentListViewModel
import com.ark.nikestore.services.FrescoImageLoadingService
import com.ark.nikestore.services.ImageLoadingService
import com.ark.nikestore.services.httpClient.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        Fresco.initialize(this)

        val myModules = module {
            single { createApiServiceInstance() }
            single<ImageLoadingService> { FrescoImageLoadingService() }
            factory<ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()), ProductLocalDataSource()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            factory<CartRepository>{ CartRepositoryImpl(CartRemoteDataSource(get())) }
            viewModel { HomeViewModel(get(), get()) }
            viewModel {(bundle: Bundle) -> ProductDetailViewModel(bundle, get(), get()) }
            viewModel {(productId: Int) -> CommentListViewModel(productId, get()) }
            viewModel {(sort : Int) -> ProductListViewModel(sort, get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

    }
}