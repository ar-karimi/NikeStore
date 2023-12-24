package com.ark.nikestore.services.httpClient

import com.ark.nikestore.BuildConfig
import com.ark.nikestore.data.AddToCartResponse
import com.ark.nikestore.data.Banner
import com.ark.nikestore.data.CartItemCount
import com.ark.nikestore.data.CartResponse
import com.ark.nikestore.data.Comment
import com.ark.nikestore.data.MessageResponse
import com.ark.nikestore.data.Product
import com.ark.nikestore.data.TokenContainer
import com.ark.nikestore.data.TokenResponse
import com.google.gson.JsonObject
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

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
}

fun createApiServiceInstance(baseAuthenticator: BaseAuthenticator): ApiService {

    val okHttpClientBuilder = OkHttpClient.Builder()
        .authenticator(baseAuthenticator)
        .addInterceptor {
            val oldRequest = it.request()
            val newRequestBuilder = oldRequest.newBuilder()
            TokenContainer.token?.let {
                newRequestBuilder.addHeader("Authorization", "Bearer ${TokenContainer.token}")
            }
            newRequestBuilder.addHeader("Accept", "application/json")
            newRequestBuilder.method(oldRequest.method, oldRequest.body)
            return@addInterceptor it.proceed(newRequestBuilder.build())
        }

    if (!BuildConfig.BUILD_TYPE.equals("release"))
        okHttpClientBuilder.addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)) //to Log Requests (to show also headers must add after headers Interceptor)

    val retrofit = Retrofit.Builder()
        .baseUrl("http://expertdevelopers.ir/api/v1/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClientBuilder.build())
        .build()

    return retrofit.create(ApiService::class.java)
}