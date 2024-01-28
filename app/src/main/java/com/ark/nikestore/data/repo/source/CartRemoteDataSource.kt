package com.ark.nikestore.data.repo.source

import com.ark.nikestore.data.AddToCartResponse
import com.ark.nikestore.data.CartItemCount
import com.ark.nikestore.data.CartResponse
import com.ark.nikestore.data.MessageResponse
import com.ark.nikestore.services.httpClient.ApiService
import com.google.gson.JsonObject
import io.reactivex.Single
import javax.inject.Inject

class CartRemoteDataSource @Inject constructor(private val apiService: ApiService) :
    CartDataSource {
    override fun addToCart(productId: Int): Single<AddToCartResponse> = apiService.addToCart(
        JsonObject().apply {
            addProperty("product_id", productId)
        }
    )

    override fun get(): Single<CartResponse> = apiService.getCart()

    override fun remove(cartItemId: Int): Single<MessageResponse> = apiService.removeFromCart(
        JsonObject().apply {
            addProperty("cart_item_id", cartItemId)
        }
    )

    override fun changeCount(cartItemId: Int, count: Int): Single<AddToCartResponse> =
        apiService.changeCount(
            JsonObject().apply {
                addProperty("cart_item_id", cartItemId)
                addProperty("count", count)
            }
        )

    override fun getCardItemsCount(): Single<CartItemCount> = apiService.getCartItemsCount()
}