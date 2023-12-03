package com.ark.nikestore.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ark.nikestore.common.BaseSingleObserver
import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.data.Banner
import com.ark.nikestore.data.Product
import com.ark.nikestore.data.SORT_LATEST
import com.ark.nikestore.data.SORT_POPULAR
import com.ark.nikestore.data.repo.BannerRepository
import com.ark.nikestore.data.repo.ProductRepository

class MainViewModel(productRepository: ProductRepository, bannerRepository: BannerRepository) : BaseViewModel() {

    private val bannersLiveData = MutableLiveData<List<Banner>>()
    private val latestProductsLiveData = MutableLiveData<List<Product>>()
    private val popularProductsLiveData = MutableLiveData<List<Product>>()

    init {
        progressBarLiveData.value = true

        bannerRepository.getBanners()
            //.doFinally { progressBarLiveData.value = false }
            .subscribe(object : BaseSingleObserver<List<Banner>>(compositeDisposable){
                override fun onSuccess(t: List<Banner>) {
                    bannersLiveData.value = t
                }
            })

        productRepository.getProducts(SORT_LATEST)
            //.doFinally { progressBarLiveData.value = false }
            .subscribe(object : BaseSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    latestProductsLiveData.value = t
                }
            })

        productRepository.getProducts(SORT_POPULAR)
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : BaseSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    popularProductsLiveData.value = t
                }
            })
    }

    fun getBanners(): LiveData<List<Banner>> = bannersLiveData
    fun getLatestProductsLiveData(): LiveData<List<Product>> = latestProductsLiveData
    fun getPopularProductsLiveData(): LiveData<List<Product>> = popularProductsLiveData
}