package com.ark.nikestore.data.repo.source

import com.ark.nikestore.data.Checkout
import com.ark.nikestore.data.OrderHistoryItem
import com.ark.nikestore.data.SubmitOrderResult
import com.ark.nikestore.services.httpClient.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single
import javax.inject.Inject

class OrderRemoteDataSource @Inject constructor(private val apiService: ApiService): OrderDataSource {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> = apiService.submitOrder(JsonObject().apply {
        addProperty("first_name", firstName)
        addProperty("last_name", lastName)
        addProperty("postal_code", postalCode)
        addProperty("mobile", phoneNumber)
        addProperty("address", address)
        addProperty("payment_method", paymentMethod)
    })

    override fun checkout(orderId: Int): Single<Checkout> = apiService.checkout(orderId)
    override fun list(): Single<List<OrderHistoryItem>> = apiService.orders()
}