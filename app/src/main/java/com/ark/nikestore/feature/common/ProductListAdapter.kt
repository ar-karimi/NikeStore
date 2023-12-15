package com.ark.nikestore.feature.common

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.ark.nikestore.R
import com.ark.nikestore.common.implementSpringAnimationTrait
import com.ark.nikestore.data.Product
import com.ark.nikestore.databinding.ItemProductBinding
import com.ark.nikestore.databinding.ItemProductLargeBinding
import com.ark.nikestore.databinding.ItemProductSmallBinding
import java.lang.IllegalStateException

const val VIEW_TYPE_ROUND = 0
const val VIEW_TYPE_SMALL = 1
const val VIEW_TYPE_LARGE = 2

class ProductListAdapter(val productCallBack: ProductCallBack, var viewType: Int = VIEW_TYPE_ROUND):RecyclerView.Adapter<ProductListAdapter.ViewHolder<ViewDataBinding>>() {

    var products = ArrayList<Product>()
        set(value){
            field = value
            notifyDataSetChanged()
        }
    inner class ViewHolder<T : ViewDataBinding>(val binding : T) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product){

            when (binding) {
                is ItemProductBinding -> {
                    val newBinding = binding as ItemProductBinding
                    newBinding.product = product
                }
                is ItemProductSmallBinding -> {
                    val newBinding = binding as ItemProductSmallBinding
                    newBinding.product = product
                }
                else -> {
                    val newBinding = binding as ItemProductLargeBinding
                    newBinding.product = product
                }
            }

            binding.root.implementSpringAnimationTrait()
            binding.root.setOnClickListener {
                productCallBack.onProductItemClick(product)
            }

            binding.executePendingBindings()
        }
    }

    interface ProductCallBack{
        fun onProductItemClick(product: Product)
    }

    override fun getItemViewType(position: Int): Int {
        return viewType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder<ViewDataBinding> {

        return when(viewType){
            VIEW_TYPE_ROUND -> ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_product, parent, false))
            VIEW_TYPE_SMALL -> ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_product_small, parent, false))
            VIEW_TYPE_LARGE -> ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_product_large, parent, false))
            else -> throw IllegalStateException("viewType is not valid!")
        }
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder<ViewDataBinding>, position: Int) = holder.bind(products[position])
}