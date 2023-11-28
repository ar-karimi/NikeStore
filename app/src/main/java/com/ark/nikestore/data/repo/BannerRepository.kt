package com.ark.nikestore.data.repo

import com.ark.nikestore.data.Banner
import io.reactivex.Single

interface BannerRepository {
    fun getBanners():Single<List<Banner>>
}