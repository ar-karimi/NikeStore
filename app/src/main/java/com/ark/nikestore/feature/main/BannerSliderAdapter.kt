package com.ark.nikestore.feature.main

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.ark.nikestore.data.Banner

class BannerSliderAdapter(fragment: Fragment, val banners: List<Banner>) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = banners.size

    override fun createFragment(position: Int): Fragment = BannerFragment.getInstance(banners[position])
}