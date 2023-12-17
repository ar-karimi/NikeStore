package com.ark.nikestore.feature.product

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ark.nikestore.common.BaseSingleObserver
import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.common.EXTRA_KEY_DATA
import com.ark.nikestore.data.Comment
import com.ark.nikestore.data.Product
import com.ark.nikestore.data.repo.CartRepository
import com.ark.nikestore.data.repo.CommentRepository
import io.reactivex.Completable

class ProductDetailViewModel(bundle: Bundle, commentRepository: CommentRepository,
                             val cartRepository: CartRepository):BaseViewModel() {

    private val productLiveData = MutableLiveData<Product>()
    private val commentsLiveData = MutableLiveData<List<Comment>>()

    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)

        progressBarLiveData.value = true
        commentRepository.getAll(productLiveData.value!!.id)
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : BaseSingleObserver<List<Comment>>(compositeDisposable){
                override fun onSuccess(t: List<Comment>) {
                    commentsLiveData.value = t
                }
            })
    }

    fun getProductLiveData(): LiveData<Product> = productLiveData
    fun getCommentsLiveData(): LiveData<List<Comment>> = commentsLiveData

    fun onAddToCartBtnClick():Completable = cartRepository.addToCart(productLiveData.value!!.id).ignoreElement()
}