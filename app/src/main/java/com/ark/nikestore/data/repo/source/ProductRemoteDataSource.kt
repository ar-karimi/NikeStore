package com.ark.nikestore.data.repo.source

import com.ark.nikestore.data.Product
import com.ark.nikestore.services.httpClient.ApiService
import io.reactivex.Completable
import io.reactivex.Single

class ProductRemoteDataSource(val apiService: ApiService):ProductDataSource {

    override fun getProducts(sort: Int): Single<List<Product>> = apiService.getProducts(sort.toString())

    override fun getFavoriteProducts(): Single<List<Product>> {
        TODO("Not yet implemented")
    }

    override fun addToFavorites(): Completable {
        TODO("Not yet implemented")
    }

    override fun deleteFromFavorites(): Completable {
        TODO("Not yet implemented")
    }
}