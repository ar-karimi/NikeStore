package com.ark.nikestore.services.httpClient

import com.ark.nikestore.data.AddToCartResponse
import com.ark.nikestore.data.Banner
import com.ark.nikestore.data.CartItemCount
import com.ark.nikestore.data.CartResponse
import com.ark.nikestore.data.Checkout
import com.ark.nikestore.data.Comment
import com.ark.nikestore.data.MessageResponse
import com.ark.nikestore.data.OrderHistoryItem
import com.ark.nikestore.data.Product
import com.ark.nikestore.data.SubmitOrderResult
import com.ark.nikestore.data.TokenResponse
import com.google.gson.JsonObject
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService {

    @GET("product/list")
    fun getProducts(@Query("sort") sort: String): Single<List<Product>>

    @GET("banner/slider")
    fun getBanners(): Single<List<Banner>>

    @GET("comment/list")
    fun getComments(@Query("product_id") productId: Int): Single<List<Comment>>

    @POST("cart/add")
    fun addToCart(@Body jsonObject: JsonObject): Single<AddToCartResponse>

    @POST("cart/remove")
    fun removeFromCart(@Body jsonObject: JsonObject): Single<MessageResponse>

    @GET("cart/list")
    fun getCart(): Single<CartResponse>

    @POST("cart/changeCount")
    fun changeCount(@Body jsonObject: JsonObject): Single<AddToCartResponse>

    @GET("cart/count")
    fun getCartItemsCount(): Single<CartItemCount>

    @POST("auth/token")
    fun login(@Body jsonObject: JsonObject): Single<TokenResponse>

    @POST("user/register")
    fun signUp(@Body jsonObject: JsonObject): Single<MessageResponse>

    @POST("auth/token")
    fun refreshToken(@Body jsonObject: JsonObject): Call<TokenResponse>

    @POST("order/submit")
    fun submitOrder(@Body jsonObject: JsonObject): Single<SubmitOrderResult>

    @GET("order/checkout")
    fun checkout(@Query("order_id") orderId: Int): Single<Checkout>

    @GET("order/list")
    fun orders(): Single<List<OrderHistoryItem>>

    companion object {
        const val BASE_URL = "http://expertdevelopers.ir/api/v1/"
    }
}