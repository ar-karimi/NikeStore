package com.ark.nikestore.feature.product

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseActivity
import com.ark.nikestore.common.BaseCompletableObserver
import com.ark.nikestore.common.EXTRA_KEY_DATA
import com.ark.nikestore.common.EXTRA_KEY_ID
import com.ark.nikestore.data.Comment
import com.ark.nikestore.databinding.ActivityProductDetailBinding
import com.ark.nikestore.feature.product.comment.CommentListActivity
import com.ark.nikestore.view.customViews.scrollView.ObservableScrollViewCallbacks
import com.ark.nikestore.view.customViews.scrollView.ScrollState
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.disposables.CompositeDisposable

@AndroidEntryPoint
class ProductDetailActivity : BaseActivity<ActivityProductDetailBinding>(R.layout.activity_product_detail) {

    private val viewModel: ProductDetailViewModel by viewModels()
    private val commentAdapter = CommentAdapter()
    private val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.initProduct(intent.extras!!.getParcelable(EXTRA_KEY_DATA)!!)

        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.getCommentsLiveData().observe(this) { comments ->
            commentAdapter.comments = comments as ArrayList<Comment>
            if (comments.size > 5) {
                binding.viewAllCommentsBtn.visibility = View.VISIBLE

                binding.viewAllCommentsBtn.setOnClickListener {
                    startActivity(Intent(this, CommentListActivity::class.java).apply {
                        putExtra(EXTRA_KEY_ID, viewModel.getProductLiveData().value!!.id)
                    })
                }
            }
        }

        viewModel.progressBarLiveData.observe(this) {
            showProgressBar(it)
        }

        initViews()
    }

    private fun initViews() {

        //To get Iv's height and set Sv's callback after draw layout completely
        binding.productIv.post {
            val toolbarView = binding.toolbarView
            val productIv = binding.productIv
            val productIvHeight = productIv.height
            binding.observableSv.addScrollViewCallbacks(object : ObservableScrollViewCallbacks {
                override fun onScrollChanged(
                    scrollY: Int,
                    firstScroll: Boolean,
                    dragging: Boolean
                ) {
                    toolbarView.alpha = scrollY.toFloat() / productIvHeight.toFloat()
                    productIv.translationY = scrollY.toFloat() / 2
                }

                override fun onDownMotionEvent() {
                }

                override fun onUpOrCancelMotionEvent(scrollState: ScrollState?) {
                }

            })
        }


        binding.commentsRv.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.commentsRv.adapter = commentAdapter


        binding.addToCartBtn.setOnClickListener { //without using LiveData, because is no need to hold Response
            viewModel.onAddToCartBtnClick()
                .subscribe(object : BaseCompletableObserver(compositeDisposable) {
                    override fun onComplete() {
                        showToast(getString(R.string.success_addToCart))
                    }
                })
        }

        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.favoriteBtn.setOnClickListener {
            viewModel.changeFavoriteProduct()
        }
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}