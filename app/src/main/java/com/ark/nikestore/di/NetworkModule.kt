package com.ark.nikestore.di

import com.ark.nikestore.data.TokenContainer
import com.ark.nikestore.services.httpClient.ApiService
import com.ark.nikestore.services.httpClient.BaseAuthenticator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton
import com.ark.nikestore.BuildConfig

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    @Provides
    @Singleton
    fun provideBaseAuthenticator() = BaseAuthenticator()

    @Provides
    @Singleton
    fun provideClient(
        loggingInterceptor: HttpLoggingInterceptor,
        baseAuthenticator: BaseAuthenticator
    ): OkHttpClient {
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
            okHttpClientBuilder.addInterceptor(loggingInterceptor) //to Log Requests (to show also headers must add after headers Interceptor)

        return okHttpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://expertdevelopers.ir/api/v1/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)
}