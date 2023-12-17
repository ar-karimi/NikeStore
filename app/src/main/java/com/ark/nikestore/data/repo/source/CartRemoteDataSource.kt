package com.ark.nikestore.data.repo.source

import com.ark.nikestore.data.AddToCartResponse
import com.ark.nikestore.data.CartItemCount
import com.ark.nikestore.data.CartResponse
import com.ark.nikestore.data.MessageResponse
import com.ark.nikestore.services.httpClient.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single

class CartRemoteDataSource(val apiService: ApiService): CartDataSource {
    override fun addToCart(productId: Int): Single<AddToCartResponse> = apiService.addToCart(
        JsonObject().apply {
            addProperty("product_id", productId)
        }
    )

    override fun get(): Single<CartResponse> {
        TODO("Not yet implemented")
    }

    override fun remove(cartItemId: Int): Single<MessageResponse> {
        TODO("Not yet implemented")
    }

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> {
        TODO("Not yet implemented")
    }

    override fun getCardItemsCount(): Single<CartItemCount> {
        TODO("Not yet implemented")
    }
}