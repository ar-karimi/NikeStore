package com.ark.nikestore.feature.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ark.nikestore.R
import com.ark.nikestore.common.implementSpringAnimationTrait
import com.ark.nikestore.data.Product
import com.ark.nikestore.databinding.ItemProductBinding

class ProductListAdapter:RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    var products = ArrayList<Product>()
        set(value){
            field = value
            notifyDataSetChanged()
        }
    inner class ViewHolder(val binding: ItemProductBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(product: Product){
            binding.product = product

            binding.root.implementSpringAnimationTrait()
            binding.root.setOnClickListener {

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding: ItemProductBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context)
            , R.layout.item_product, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount() = products.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) = holder.bind(products[position])
}