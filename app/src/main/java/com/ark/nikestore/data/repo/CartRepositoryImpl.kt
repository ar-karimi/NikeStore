package com.ark.nikestore.data.repo

import com.ark.nikestore.data.AddToCartResponse
import com.ark.nikestore.data.CartItemCount
import com.ark.nikestore.data.CartResponse
import com.ark.nikestore.data.MessageResponse
import com.ark.nikestore.data.repo.source.CartDataSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class CartRepositoryImpl(private val remoteDataSource: CartDataSource) : CartRepository {
    override fun addToCart(productId: Int): Single<AddToCartResponse> = remoteDataSource.addToCart(productId)
            .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    override fun get(): Single<CartResponse> = remoteDataSource.get()
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    override fun remove(cartItemId: Int): Single<MessageResponse> = remoteDataSource.remove(cartItemId)
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> = remoteDataSource.changeCount(cartItemId, count)
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    override fun getCardItemsCount(): Single<CartItemCount> = remoteDataSource.getCardItemsCount()
}