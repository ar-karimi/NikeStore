package com.ark.nikestore.feature.order

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ark.nikestore.common.BaseSingleObserver
import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.data.OrderHistoryItem
import com.ark.nikestore.data.repo.OrderRepository

class OrderHistoryViewModel(orderRepository: OrderRepository): BaseViewModel() {

    private val orderHistoryItemsLiveData = MutableLiveData<List<OrderHistoryItem>>()
    init {
        progressBarLiveData.value = true
        orderRepository.list()
            .doFinally { progressBarLiveData.value = false }
            .subscribe(object : BaseSingleObserver<List<OrderHistoryItem>>(compositeDisposable){
                override fun onSuccess(t: List<OrderHistoryItem>) {
                    orderHistoryItemsLiveData.value = t
                }
            })
    }

    fun getOrderHistoryItemsLiveData(): LiveData<List<OrderHistoryItem>> = orderHistoryItemsLiveData
}