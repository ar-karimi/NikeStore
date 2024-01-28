package com.ark.nikestore.feature.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ark.nikestore.common.BaseCompletableObserver
import com.ark.nikestore.common.BaseSingleObserver
import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.data.Comment
import com.ark.nikestore.data.Product
import com.ark.nikestore.data.repo.CartRepository
import com.ark.nikestore.data.repo.CommentRepository
import com.ark.nikestore.data.repo.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(private val commentRepository: CommentRepository,
    private val cartRepository: CartRepository, private val productRepository: ProductRepository
) : BaseViewModel() {

    private val productLiveData = MutableLiveData<Product>()
    private val commentsLiveData = MutableLiveData<List<Comment>>()

    fun initProduct(product: Product){
        productLiveData.value = product

        progressBarLiveData.value = true
        commentRepository.getAll(productLiveData.value!!.id)
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : BaseSingleObserver<List<Comment>>(compositeDisposable) {
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }
            })
    }

    fun changeFavoriteProduct() {

        productLiveData.value?.let { product ->
            if (product.isFavorite)
                productRepository.deleteFromFavorites(product)
                    .subscribeOn(Schedulers.io())
                    .subscribe(object : BaseCompletableObserver(compositeDisposable) {
                        override fun onComplete() {
                            product.isFavorite = false
                            productLiveData.postValue(product)
                        }
                    })
            else
                productRepository.addToFavorites(product)
                    .subscribeOn(Schedulers.io())
                    .subscribe(object : BaseCompletableObserver(compositeDisposable) {
                        override fun onComplete() {
                            product.isFavorite = true
                            productLiveData.postValue(product)
                        }
                    })
        }
    }

    fun getProductLiveData(): LiveData<Product> = productLiveData
    fun getCommentsLiveData(): LiveData<List<Comment>> = commentsLiveData

    fun onAddToCartBtnClick(): Completable =
        cartRepository.addToCart(productLiveData.value!!.id).ignoreElement()
}