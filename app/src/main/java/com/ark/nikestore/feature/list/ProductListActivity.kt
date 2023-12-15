package com.ark.nikestore.feature.list

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
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
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class ProductListActivity : BaseActivity(), ProductListAdapter.ProductCallBack {

    private lateinit var binding: ActivityProductListBinding
    private val viewModel : ProductListViewModel by viewModel {parametersOf(intent.extras!!.getInt(EXTRA_KEY_DATA))}
    private val productListAdapter = ProductListAdapter(this, VIEW_TYPE_SMALL)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_list)
        binding.lifecycleOwner = this

        binding.viewModel = viewModel
        binding.executePendingBindings()

        binding.toolbarView.onBackBtnClickListener = View.OnClickListener {
            finish()
        }

        val gridLayoutManager = GridLayoutManager(this,2)
        binding.productsRv.layoutManager = gridLayoutManager
        binding.productsRv.adapter = productListAdapter

        binding.viewTypeChangerBtn.setOnClickListener{
            if (productListAdapter.viewType == VIEW_TYPE_SMALL){
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

        viewModel.getProductsLiveData().observe(this){
            productListAdapter.products = it as ArrayList<Product>
        }

        viewModel.progressBarLiveData.observe(this){
            showProgressBar(it)
        }

        binding.sortBtn.setOnClickListener {
            val dialog = MaterialAlertDialogBuilder(this)
                .setSingleChoiceItems(R.array.sortTitlesArray, viewModel.sort) { dialog, selectedSortIndex ->
                    viewModel.onSelectedSortChange(selectedSortIndex)
                    dialog.dismiss()
                }.setTitle(getString(R.string.sort))
            dialog.show()
        }
    }

    override fun onProductItemClick(product: Product) {
        startActivity(Intent(this, ProductDetailActivity::class.java).apply {
            putExtra(EXTRA_KEY_DATA, product)
        })
    }
}