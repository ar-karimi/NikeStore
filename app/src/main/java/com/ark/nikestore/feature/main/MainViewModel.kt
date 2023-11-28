package com.ark.nikestore.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ark.nikestore.common.BaseSingleObserver
import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.data.Banner
import com.ark.nikestore.data.Product
import com.ark.nikestore.data.repo.BannerRepository
import com.ark.nikestore.data.repo.ProductRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MainViewModel(productRepository: ProductRepository, bannerRepository: BannerRepository) : BaseViewModel() {

    private val productsLiveData = MutableLiveData<List<Product>>()
    private val bannersLiveData = MutableLiveData<List<Banner>>()

    init {
        progressBarLiveData.value = true
        productRepository.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : BaseSingleObserver<List<Product>>(compositeDisposable){
                override fun onSuccess(t: List<Product>) {
                    productsLiveData.value = t
                }
            })

        //progressBarLiveData.value = true
        bannerRepository.getBanners()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            //.doFinally { progressBarLiveData.value = false }
            .subscribe(object : BaseSingleObserver<List<Banner>>(compositeDisposable){
                override fun onSuccess(t: List<Banner>) {
                    bannersLiveData.value = t
                }
            })
    }

    fun getProductsLiveData(): LiveData<List<Product>> = productsLiveData
    fun getBanners(): LiveData<List<Banner>> = bannersLiveData
}