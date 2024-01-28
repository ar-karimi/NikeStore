package com.ark.nikestore.feature.favorites

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseActivity
import com.ark.nikestore.common.EXTRA_KEY_DATA
import com.ark.nikestore.data.Product
import com.ark.nikestore.databinding.ActivityFavoriteProductsBinding
import com.ark.nikestore.feature.product.ProductDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavoriteProductsActivity :
    BaseActivity<ActivityFavoriteProductsBinding>(R.layout.activity_favorite_products),
    FavoriteProductsCallbacks {

    private val viewModel: FavoriteProductsViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.toolbarView.onBackBtnClickListener = View.OnClickListener {
            finish()
        }

        viewModel.getFavoriteProductsLiveData().observe(this) {
            if (it.isNotEmpty()) {
                binding.favoriteProductsRv.layoutManager = LinearLayoutManager(
                    this, RecyclerView.VERTICAL, false
                )
                binding.favoriteProductsRv.adapter =
                    FavoriteProductsAdapter(it as MutableList<Product>, this)
            } else {
                binding.favoriteProductsRv.adapter = null   //to clear prev list
                showEmptyState(R.layout.view_default_empty_state)?.findViewById<TextView>(
                    R.id.emptyStateMessageTv
                )?.text = getString(R.string.favoritesEmptyStateMessage)
            }
        }

        viewModel.progressBarLiveData.observe(this) {
            showProgressBar(it)
        }

        binding.helpBtn.setOnClickListener {
            showToast(getString(R.string.favoritesHelpMessage))
        }

    }

    override fun onResume() {
        super.onResume()
        viewModel.getFavoriteProducts()
    }

    override fun onItemClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }

    override fun onItemLongClick(product: Product) {
        viewModel.removeFromFavorites(product)
    }

    override fun onListGettingEmpty() {
        showEmptyState(R.layout.view_default_empty_state)?.findViewById<TextView>(R.id.emptyStateMessageTv)
            ?.text = getString(R.string.favoritesEmptyStateMessage)
    }
}