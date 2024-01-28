package com.ark.nikestore.data.repo

import com.ark.nikestore.data.Banner
import com.ark.nikestore.data.repo.source.BannerDataSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(private val bannerRemoteDataSource: BannerDataSource) :
    BannerRepository {
    override fun getBanners(): Single<List<Banner>> = bannerRemoteDataSource.getBanners()
        .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
}