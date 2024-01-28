package com.ark.nikestore.feature.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ark.nikestore.common.BaseCompletableObserver
import com.ark.nikestore.common.BaseSingleObserver
import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.data.Product
import com.ark.nikestore.data.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoriteProductsViewModel @Inject constructor(private val productRepository: ProductRepository) :
    BaseViewModel() {

    private val favoriteProductsLiveData = MutableLiveData<List<Product>>()

    fun getFavoriteProducts() {
        progressBarLiveData.value = true
        productRepository.getFavoriteProducts()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : BaseSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    favoriteProductsLiveData.value = t
                }
            })
    }

    fun removeFromFavorites(product: Product) {
        productRepository.deleteFromFavorites(product)
            .subscribeOn(Schedulers.io())
            .subscribe(object : BaseCompletableObserver(compositeDisposable) {
                override fun onComplete() {
                    Timber.i("removeFromFavorites Completed")
                }
            })
    }

    fun getFavoriteProductsLiveData(): LiveData<List<Product>> = favoriteProductsLiveData
}