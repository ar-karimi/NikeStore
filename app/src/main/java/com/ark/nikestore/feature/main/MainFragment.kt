package com.ark.nikestore.feature.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseFragment
import com.ark.nikestore.common.EXTRA_KEY_DATA
import com.ark.nikestore.common.dpToPx
import com.ark.nikestore.data.Product
import com.ark.nikestore.databinding.FragmentMainBinding
import com.ark.nikestore.feature.product.ProductDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : BaseFragment<FragmentMainBinding>(), ProductListAdapter.ProductCallBack {

    private val mainViewModel: MainViewModel by viewModel()
    private val latestProductListAdapter = ProductListAdapter(this)
    private val popularProductListAdapter = ProductListAdapter(this)
    override fun getLayoutRes() = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Banners
        mainViewModel.getBanners().observe(viewLifecycleOwner) {
            //Timber.i("Banner list is: $it")

            val bannerSliderAdapter = BannerSliderAdapter(this, it)
            binding.bannerSliderViewPager.adapter = bannerSliderAdapter

            //Indicator
            binding.sliderIndicator.attachTo(binding.bannerSliderViewPager)

            //Calculate the appropriate height, because wrap_content not working for viewPager2's layout_height
            val viewPagerHeight =
                ((binding.bannerSliderViewPager.measuredWidth - requireContext().dpToPx(32)) * 173) / 328

            val layoutParams = binding.bannerSliderViewPager.layoutParams
            layoutParams.height = viewPagerHeight
            binding.bannerSliderViewPager.layoutParams = layoutParams

        }

        //latestProducts
        binding.latestProductsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.latestProductsRv.adapter = latestProductListAdapter

        mainViewModel.getLatestProductsLiveData().observe(viewLifecycleOwner) { products ->
            //Timber.i("Product list is: $products")
            latestProductListAdapter.products = products as ArrayList<Product>
        }

        //popularProducts
        binding.popularProductsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.popularProductsRv.adapter = popularProductListAdapter

        mainViewModel.getPopularProductsLiveData().observe(viewLifecycleOwner){ products ->
            popularProductListAdapter.products = products as ArrayList<Product>
        }

        mainViewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            showProgressBar(it)
        }
    }

    override fun onProductItemClick(product: Product) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }
}