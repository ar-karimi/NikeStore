package com.ark.nikestore.feature.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseFragment
import com.ark.nikestore.common.EXTRA_KEY_DATA
import com.ark.nikestore.common.dpToPx
import com.ark.nikestore.data.Product
import com.ark.nikestore.data.SORT_LATEST
import com.ark.nikestore.data.SORT_POPULAR
import com.ark.nikestore.databinding.FragmentHomeBinding
import com.ark.nikestore.feature.common.ProductListAdapter
import com.ark.nikestore.feature.list.ProductListActivity
import com.ark.nikestore.feature.product.ProductDetailActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : BaseFragment<FragmentHomeBinding>(), ProductListAdapter.ProductCallBacks {

    private val viewModel: HomeViewModel by viewModel()
    private var bannerSliderAdapter: BannerSliderAdapter? = null
    private val latestProductListAdapter = ProductListAdapter(this)
    private val popularProductListAdapter = ProductListAdapter(this)
    override fun getLayoutRes() = R.layout.fragment_home

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Banners
        viewModel.getBanners().observe(viewLifecycleOwner) {
            //Timber.i("Banner list is: $it")
            bannerSliderAdapter = BannerSliderAdapter(this, it)
            binding.bannerSliderViewPager.adapter = bannerSliderAdapter
            initBannerSliderView()
        }

        //latestProducts
        binding.latestProductsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.latestProductsRv.adapter = latestProductListAdapter

        viewModel.getLatestProductsLiveData().observe(viewLifecycleOwner) { products ->
            //Timber.i("Product list is: $products")
            latestProductListAdapter.products = products as ArrayList<Product>
        }

        //popularProducts
        binding.popularProductsRv.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.popularProductsRv.adapter = popularProductListAdapter

        viewModel.getPopularProductsLiveData().observe(viewLifecycleOwner){ products ->
            popularProductListAdapter.products = products as ArrayList<Product>
        }

        viewModel.progressBarLiveData.observe(viewLifecycleOwner) {
            showProgressBar(it)
        }

        binding.viewAllLatestProductsBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, SORT_LATEST)
            })
        }

        binding.viewAllPopularProductsBtn.setOnClickListener {
            startActivity(Intent(requireContext(), ProductListActivity::class.java).apply {
                putExtra(EXTRA_KEY_DATA, SORT_POPULAR)
            })
        }
    }

    private fun initBannerSliderView(){

        //Indicator
        binding.sliderIndicator.attachTo(binding.bannerSliderViewPager)

        //Calculate the appropriate height, because wrap_content not working for viewPager2's layout_height
        val viewPagerHeight =
            ((binding.bannerSliderViewPager.measuredWidth - requireContext().dpToPx(32)) * 173) / 328

        val layoutParams = binding.bannerSliderViewPager.layoutParams
        layoutParams.height = viewPagerHeight
        binding.bannerSliderViewPager.layoutParams = layoutParams
    }

    override fun onResume() {
        super.onResume()
        viewModel.getProductsLists()

        bannerSliderAdapter?.let {//after change tab, must set slider height again
            initBannerSliderView()
        }
    }

    override fun onProductItemClick(product: Product) {
        startActivity(Intent(requireContext(), ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }

    override fun onProductFavoriteClick(product: Product) {
        viewModel.changeFavoriteProduct(product)
    }
}