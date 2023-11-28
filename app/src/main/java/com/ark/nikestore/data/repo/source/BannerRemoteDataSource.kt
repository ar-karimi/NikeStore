package com.ark.nikestore.data.repo.source

import com.ark.nikestore.data.Banner
import com.ark.nikestore.services.httpClient.ApiService
import io.reactivex.Single

class BannerRemoteDataSource(val apiService: ApiService):BannerDataSource {
    override fun getBanners(): Single<List<Banner>> = apiService.getBanners()
}