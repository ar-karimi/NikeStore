package com.ark.nikestore

import android.app.Application
import com.ark.nikestore.data.repo.ProductRepository
import com.ark.nikestore.data.repo.ProductRepositoryImpl
import com.ark.nikestore.data.repo.source.ProductLocalDataSource
import com.ark.nikestore.data.repo.source.ProductRemoteDataSource
import com.ark.nikestore.feature.main.MainViewModel
import com.ark.nikestore.services.httpClient.createApiServiceInstance
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant()

        val myModules = module {
            single { createApiServiceInstance() }
            factory<ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()), ProductLocalDataSource()) }
            viewModel { MainViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

    }
}