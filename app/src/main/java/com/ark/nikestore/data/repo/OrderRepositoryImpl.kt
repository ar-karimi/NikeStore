package com.ark.nikestore.data.repo

import com.ark.nikestore.data.Checkout
import com.ark.nikestore.data.OrderHistoryItem
import com.ark.nikestore.data.SubmitOrderResult
import com.ark.nikestore.data.repo.source.OrderDataSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(private val orderRemoteDataSource: OrderDataSource) :
    OrderRepository {
    override fun submit(
        firstName: String,
        lastName: String,
        postalCode: String,
        phoneNumber: String,
        address: String,
        paymentMethod: String
    ): Single<SubmitOrderResult> =
        orderRemoteDataSource.submit(
            firstName,
            lastName,
            postalCode,
            phoneNumber,
            address,
            paymentMethod
        )
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    override fun checkout(orderId: Int): Single<Checkout> = orderRemoteDataSource.checkout(orderId)
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    override fun list(): Single<List<OrderHistoryItem>> = orderRemoteDataSource.list()
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}