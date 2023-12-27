package com.ark.nikestore

import android.app.Application
import android.content.SharedPreferences
import android.os.Bundle
import com.ark.nikestore.data.repo.BannerRepository
import com.ark.nikestore.data.repo.BannerRepositoryImpl
import com.ark.nikestore.data.repo.CartRepository
import com.ark.nikestore.data.repo.CartRepositoryImpl
import com.ark.nikestore.data.repo.CommentRepository
import com.ark.nikestore.data.repo.CommentRepositoryImpl
import com.ark.nikestore.data.repo.OrderRepository
import com.ark.nikestore.data.repo.OrderRepositoryImpl
import com.ark.nikestore.data.repo.ProductRepository
import com.ark.nikestore.data.repo.ProductRepositoryImpl
import com.ark.nikestore.data.repo.UserRepository
import com.ark.nikestore.data.repo.UserRepositoryImpl
import com.ark.nikestore.data.repo.source.BannerRemoteDataSource
import com.ark.nikestore.data.repo.source.CartRemoteDataSource
import com.ark.nikestore.data.repo.source.CommentRemoteDataSource
import com.ark.nikestore.data.repo.source.OrderRemoteDataSource
import com.ark.nikestore.data.repo.source.ProductLocalDataSource
import com.ark.nikestore.data.repo.source.ProductRemoteDataSource
import com.ark.nikestore.data.repo.source.UserLocalDataSource
import com.ark.nikestore.data.repo.source.UserRemoteDataSource
import com.ark.nikestore.feature.auth.AuthViewModel
import com.ark.nikestore.feature.cart.CartViewModel
import com.ark.nikestore.feature.checkout.CheckOutViewModel
import com.ark.nikestore.feature.home.HomeViewModel
import com.ark.nikestore.feature.list.ProductListViewModel
import com.ark.nikestore.feature.main.MainViewModel
import com.ark.nikestore.feature.product.ProductDetailViewModel
import com.ark.nikestore.feature.product.comment.CommentListViewModel
import com.ark.nikestore.feature.profile.ProfileViewModel
import com.ark.nikestore.feature.shipping.ShippingViewModel
import com.ark.nikestore.services.FrescoImageLoadingService
import com.ark.nikestore.services.ImageLoadingService
import com.ark.nikestore.services.httpClient.BaseAuthenticator
import com.ark.nikestore.services.httpClient.createApiServiceInstance
import com.facebook.drawee.backends.pipeline.Fresco
import org.koin.android.ext.android.get
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
            single { BaseAuthenticator() }
            single { createApiServiceInstance(get()) }
            single<ImageLoadingService> { FrescoImageLoadingService() }
            single<SharedPreferences> { this@App.getSharedPreferences("app_sharedPref", MODE_PRIVATE) }
            single<UserRepository> { UserRepositoryImpl(UserRemoteDataSource(get()), UserLocalDataSource(get())) }
            single { UserLocalDataSource(get()) }
            single<OrderRepository> { OrderRepositoryImpl(OrderRemoteDataSource(get())) }
            factory<ProductRepository> { ProductRepositoryImpl(ProductRemoteDataSource(get()), ProductLocalDataSource()) }
            factory<BannerRepository> { BannerRepositoryImpl(BannerRemoteDataSource(get())) }
            factory<CommentRepository> { CommentRepositoryImpl(CommentRemoteDataSource(get())) }
            factory<CartRepository>{ CartRepositoryImpl(CartRemoteDataSource(get())) }
            viewModel { HomeViewModel(get(), get()) }
            viewModel {(bundle: Bundle) -> ProductDetailViewModel(bundle, get(), get()) }
            viewModel {(productId: Int) -> CommentListViewModel(productId, get()) }
            viewModel {(sort : Int) -> ProductListViewModel(sort, get()) }
            viewModel { AuthViewModel(get()) }
            viewModel { CartViewModel(get()) }
            viewModel { MainViewModel(get()) }
            viewModel { ShippingViewModel(get()) }
            viewModel {(orderId: Int) -> CheckOutViewModel(orderId, get()) }
            viewModel { ProfileViewModel(get()) }
        }

        startKoin {
            androidContext(this@App)
            modules(myModules)
        }

        //Initialize TokenContainer from SharedPref
        val userRepository: UserRepository = get()
        userRepository.loadToken()

    }
}