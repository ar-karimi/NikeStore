package com.ark.nikestore.feature.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ark.nikestore.common.BaseCompletableObserver
import com.ark.nikestore.common.BaseSingleObserver
import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.data.Banner
import com.ark.nikestore.data.Product
import com.ark.nikestore.data.SORT_LATEST
import com.ark.nikestore.data.SORT_POPULAR
import com.ark.nikestore.data.repo.BannerRepository
import com.ark.nikestore.data.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    bannerRepository: BannerRepository
) : BaseViewModel() {

    private val bannersLiveData = MutableLiveData<List<Banner>>()
    private val latestProductsLiveData = MutableLiveData<List<Product>>()
    private val popularProductsLiveData = MutableLiveData<List<Product>>()

    init {
        progressBarLiveData.value = true

        bannerRepository.getBanners()
            //.doFinally { progressBarLiveData.value = false }
            .subscribe(object : BaseSingleObserver<List<Banner>>(compositeDisposable) {
                override fun onSuccess(t: List<Banner>) {
                    bannersLiveData.value = t
                }
            })
    }

    fun getProductsLists() {

        productRepository.getProducts(SORT_LATEST)
            //.doFinally { progressBarLiveData.value = false }
            .subscribe(object : BaseSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    latestProductsLiveData.value = t
                }
            })

        productRepository.getProducts(SORT_POPULAR)
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : BaseSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    popularProductsLiveData.value = t
                }
            })
    }

    fun changeFavoriteProduct(product: Product) {
        if (product.isFavorite)
            productRepository.deleteFromFavorites(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : BaseCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = false
                    }
                })
        else
            productRepository.addToFavorites(product)
                .subscribeOn(Schedulers.io())
                .subscribe(object : BaseCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        product.isFavorite = true
                    }
                })
    }

    fun getBanners(): LiveData<List<Banner>> = bannersLiveData
    fun getLatestProductsLiveData(): LiveData<List<Product>> = latestProductsLiveData
    fun getPopularProductsLiveData(): LiveData<List<Product>> = popularProductsLiveData
}