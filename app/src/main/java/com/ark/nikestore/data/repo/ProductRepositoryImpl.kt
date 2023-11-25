package com.ark.nikestore.data.repo

import com.ark.nikestore.data.Product
import com.ark.nikestore.data.repo.source.ProductDataSource
import com.ark.nikestore.data.repo.source.ProductLocalDataSource
import com.ark.nikestore.data.repo.source.ProductRemoteDataSource
import io.reactivex.Completable
import io.reactivex.Single

class ProductRepositoryImpl(val remoteDataSource: ProductDataSource
, val localDataSource: ProductDataSource):ProductRepository {

    override fun getProducts(): Single<List<Product>> = remoteDataSource.getProducts()

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