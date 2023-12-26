package com.ark.nikestore.data.repo

import com.ark.nikestore.data.Checkout
import com.ark.nikestore.data.SubmitOrderResult
import io.reactivex.Single

interface OrderRepository {

    fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult>

    fun checkout(orderId: Int): Single<Checkout>
}