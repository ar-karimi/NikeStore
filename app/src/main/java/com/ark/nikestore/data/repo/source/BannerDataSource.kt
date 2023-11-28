package com.ark.nikestore.data.repo.source

import com.ark.nikestore.data.Banner
import io.reactivex.Single

interface BannerDataSource {
    fun getBanners():Single<List<Banner>>
}