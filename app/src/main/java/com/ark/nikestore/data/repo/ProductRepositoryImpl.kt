package com.ark.nikestore.data.repo

import com.ark.nikestore.data.Product
import com.ark.nikestore.data.repo.source.ProductDataSource
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class ProductRepositoryImpl(private val remoteDataSource: ProductDataSource
, private val localDataSource: ProductDataSource):ProductRepository {

    override fun getProducts(sort: Int): Single<List<Product>> = localDataSource.getFavoriteProducts()
        .subscribeOn(Schedulers.io())
        .flatMap { favoriteProducts ->
            val favoriteProductsIds = favoriteProducts.map {
                it.id
            }
            remoteDataSource.getProducts(sort)
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .doOnSuccess {

                    it.forEach { product ->
                        if (favoriteProductsIds.contains(product.id))
                            product.isFavorite = true
                    }
                }
        }

    override fun getFavoriteProducts(): Single<List<Product>> = localDataSource.getFavoriteProducts()
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

    override fun addToFavorites(product: Product): Completable = localDataSource.addToFavorites(product)

    override fun deleteFromFavorites(product: Product): Completable = localDataSource.deleteFromFavorites(product)
}