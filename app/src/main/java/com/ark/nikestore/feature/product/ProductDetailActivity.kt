package com.ark.nikestore.feature.product

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseActivity
import com.ark.nikestore.databinding.ActivityProductDetailBinding
import com.ark.nikestore.view.customViews.scrollView.ObservableScrollViewCallbacks
import com.ark.nikestore.view.customViews.scrollView.ScrollState
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductDetailActivity : BaseActivity() {

    private lateinit var binding: ActivityProductDetailBinding
    private val productDetailViewModel: ProductDetailViewModel by viewModel(){ parametersOf(intent.extras) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_detail)

        productDetailViewModel.getProductLiveData().observe(this){
            binding.product = it
        }


        //To get Iv's height and set Sv's callback after draw layout completely
        binding.productIv.post {
            val toolbarView = binding.toolbarView
            val productIv = binding.productIv
            val productIvHeight = productIv.height
            binding.observableSv.addScrollViewCallbacks(object : ObservableScrollViewCallbacks{
                override fun onScrollChanged(scrollY: Int, firstScroll: Boolean, dragging: Boolean) {
                    toolbarView.alpha = scrollY.toFloat() / productIvHeight.toFloat()
                    productIv.translationY = scrollY.toFloat() / 2
                }

                override fun onDownMotionEvent() {
                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
                }

            })
        }

    }
}