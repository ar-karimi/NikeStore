package com.ark.nikestore.feature.checkout

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ark.nikestore.common.BaseSingleObserver
import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.data.Checkout
import com.ark.nikestore.data.repo.OrderRepository

class CheckOutViewModel(orderId: Int, orderRepository: OrderRepository): BaseViewModel() {

    private val checkoutLiveData = MutableLiveData<Checkout>()
    init {
        progressBarLiveData.value = true
        orderRepository.checkout(orderId)
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : BaseSingleObserver<Checkout>(compositeDisposable){
                override fun onSuccess(t: Checkout) {
                    checkoutLiveData.value = t
                }
            })
    }

    fun getCheckoutLiveData(): LiveData<Checkout> = checkoutLiveData
}