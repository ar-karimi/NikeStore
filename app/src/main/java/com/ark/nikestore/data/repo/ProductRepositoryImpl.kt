package com.ark.nikestore.data.repo

import com.ark.nikestore.data.Product
import com.ark.nikestore.data.repo.source.ProductDataSource
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductRepositoryImpl(val remoteDataSource: ProductDataSource
, val localDataSource: ProductDataSource):ProductRepository {

    override fun getProducts(sort: Int): Single<List<Product>> = remoteDataSource.getProducts(sort)
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

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