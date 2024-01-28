package com.ark.nikestore.feature.shipping

import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.data.SubmitOrderResult
import com.ark.nikestore.data.repo.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Single
import javax.inject.Inject

const val PAYMENT_METHOD_COD = "cash_on_delivery"
const val PAYMENT_METHOD_ONLINE = "online"
@HiltViewModel
class ShippingViewModel @Inject constructor(private val orderRepository: OrderRepository) : BaseViewModel() {

    fun submitOrder(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> {
        progressBarLiveData.value = true
        return orderRepository.submit(firstName, lastName, postalCode, phoneNumber, address, paymentMethod)
            .doFinally { progressBarLiveData.value = false }
    }

}