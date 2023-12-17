package com.ark.nikestore.data.repo.source

import com.ark.nikestore.data.AddToCartResponse
import com.ark.nikestore.data.CartItemCount
import com.ark.nikestore.data.CartResponse
import com.ark.nikestore.data.MessageResponse
import io.reactivex.Single

interface CartDataSource {

    fun addToCart(productId: Int): Single<AddToCartResponse>
    fun get(): Single<CartResponse>
    fun remove(cartItemId: Int):Single<MessageResponse>
    fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse>
    fun getCardItemsCount(): Single<CartItemCount>
}