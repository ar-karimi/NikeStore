package com.ark.nikestore.data.repo

import com.ark.nikestore.data.Banner
import com.ark.nikestore.data.repo.source.BannerDataSource
import io.reactivex.Single

class BannerRepositoryImpl(val bannerRemoteDataSource: BannerDataSource):BannerRepository {
    override fun getBanners(): Single<List<Banner>> = bannerRemoteDataSource.getBanners()
}