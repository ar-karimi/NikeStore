package com.ark.nikestore.feature.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseActivity
import com.ark.nikestore.common.EXTRA_KEY_DATA
import com.ark.nikestore.data.Product
import com.ark.nikestore.databinding.ActivityProductListBinding
import com.ark.nikestore.feature.common.ProductListAdapter
import com.ark.nikestore.feature.common.VIEW_TYPE_LARGE
import com.ark.nikestore.feature.common.VIEW_TYPE_SMALL
import com.ark.nikestore.feature.product.ProductDetailActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductListActivity :
    BaseActivity<ActivityProductListBinding>(R.layout.activity_product_list),
    ProductListAdapter.ProductCallBacks {

    private val viewModel: ProductListViewModel by viewModels()
    private val productListAdapter = ProductListAdapter(this, VIEW_TYPE_SMALL)
    private var selectedSort : Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        selectedSort = intent.extras!!.getInt(EXTRA_KEY_DATA)

        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.toolbarView.onBackBtnClickListener = View.OnClickListener {
            finish()
        }

        val gridLayoutManager = GridLayoutManager(this, 2)
        binding.productsRv.layoutManager = gridLayoutManager
        binding.productsRv.adapter = productListAdapter

        binding.viewTypeChangerBtn.setOnClickListener {
            if (productListAdapter.viewType == VIEW_TYPE_SMALL) {
                productListAdapter.viewType = VIEW_TYPE_LARGE
                gridLayoutManager.spanCount = 1
                productListAdapter.notifyDataSetChanged()
                binding.viewTypeChangerBtn.setImageResource(R.drawable.ic_view_type_large)
            } else {
                productListAdapter.viewType = VIEW_TYPE_SMALL
                gridLayoutManager.spanCount = 2
                productListAdapter.notifyDataSetChanged()
                binding.viewTypeChangerBtn.setImageResource(R.drawable.ic_grid)
            }
        }

        viewModel.getProductsLiveData().observe(this) {
            productListAdapter.products = it as ArrayList<Product>
        }

        viewModel.progressBarLiveData.observe(this) {
            showProgressBar(it)
        }

        binding.sortBtn.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setSingleChoiceItems(
                    R.array.sortTitlesArray,
                    selectedSort
                ) { dialog, selectedSortIndex ->
                    selectedSort = selectedSortIndex
                    viewModel.setSelectedSort(selectedSort)
                    dialog.dismiss()
                }.setTitle(getString(R.string.sort))
            dialog.show()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.setSelectedSort(selectedSort)
    }

    override fun onProductItemClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }

    override fun onProductFavoriteClick(product: Product) {
        viewModel.changeFavoriteProduct(product)
    }
}