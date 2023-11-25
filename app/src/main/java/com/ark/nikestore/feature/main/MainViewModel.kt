package com.ark.nikestore.feature.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.data.Product
import com.ark.nikestore.data.repo.ProductRepository
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainViewModel(productRepository: ProductRepository): BaseViewModel() {

    private val productsLiveData = MutableLiveData<List<Product>>()

    init {
        productRepository.getProducts()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<List<Product>>{
                override fun onSubscribe(d: Disposable) {

                    compositeDisposable.add(d)
                }

                override fun onError(e: Throwable) {
                    Timber.e(e)
                }

                override fun onSuccess(t: List<Product>) {

                    productsLiveData.value = t
                }

            })
    }

    fun getProductsLiveData ():LiveData<List<Product>> = productsLiveData
}