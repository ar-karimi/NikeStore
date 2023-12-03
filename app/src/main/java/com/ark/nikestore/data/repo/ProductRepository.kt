package com.ark.nikestore.data.repo

import com.ark.nikestore.data.Product
import io.reactivex.Completable
import io.reactivex.Single

interface ProductRepository {

    fun getProducts(sort: Int): Single<List<Product>>

    fun getFavoriteProducts(): Single<List<Product>>

    fun addToFavorites(): Completable

    fun deleteFromFavorites(): Completable
}