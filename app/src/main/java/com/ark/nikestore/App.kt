package com.ark.nikestore

import android.app.Application
import android.os.Bundle
import com.ark.nikestore.data.repo.BannerRepository
import com.ark.nikestore.data.repo.BannerRepositoryImpl
import com.ark.nikestore.data.repo.ProductRepository
import com.ark.nikestore.data.repo.ProductRepositoryImpl
import com.ark.nikestore.data.repo.source.BannerRemoteDataSource
import com.ark.nikestore.data.repo.source.ProductLocalDataSource
import com.ark.nikestore.data.repo.source.ProductRemoteDataSource
import com.ark.nikestore.feature.main.MainViewModel
import com.ark.nikestore.feature.product.ProductDetailViewModel
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
            viewModel { MainViewModel(get(), get()) }
            viewModel {(bundle: Bundle) -> ProductDetailViewModel(bundle) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

    }
}