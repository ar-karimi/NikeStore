package com.ark.nikestore.feature.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ark.nikestore.R
import com.ark.nikestore.common.EXTRA_KEY_DATA
import com.ark.nikestore.data.Banner
import com.ark.nikestore.services.ImageLoadingService
import com.ark.nikestore.view.customViews.CustomImageView
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class BannerFragment : Fragment() {

    @Inject
    lateinit var imageLoadingService: ImageLoadingService
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val imageView = inflater.inflate(R.layout.fargment_banner, container, false) as CustomImageView
        val banner =
            requireArguments().getParcelable<Banner>(EXTRA_KEY_DATA) ?: throw IllegalStateException("Banner can't be null")
        imageLoadingService.loadImage(imageView, banner.image)
        return imageView
    }

    companion object {
        fun getInstance(banner: Banner): BannerFragment {
            /*val bannerFragment = BannerFragment()
            val bundle = Bundle()
            bundle.putParcelable(EXTRA_KEY_DATA, banner)
            bannerFragment.arguments = bundle
            return bannerFragment*/

            //new method
            return BannerFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(EXTRA_KEY_DATA, banner)
                }
            }

        }
    }
}