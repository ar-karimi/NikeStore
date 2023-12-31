package com.ark.nikestore.feature.favorites

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ark.nikestore.R
import com.ark.nikestore.data.Product
import com.ark.nikestore.databinding.ItemFavoriteProductBinding

class FavoriteProductsAdapter(private val productList: MutableList<Product>
  , val favoriteProductsCallbacks: FavoriteProductsCallbacks) : RecyclerView.Adapter<FavoriteProductsAdapter.ViewHolder>() {

    inner class ViewHolder(private val binding: ItemFavoriteProductBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(product: Product) {
            product.isFavorite = true
            binding.product = product

            binding.root.setOnClickListener {
                favoriteProductsCallbacks.onItemClick(product)
            }
            binding.root.setOnLongClickListener {
                productList.remove(product)
                notifyItemRemoved(adapterPosition)
                favoriteProductsCallbacks.onItemLongClick(product)
                if (productList.isEmpty())
                    favoriteProductsCallbacks.onListGettingEmpty()
                return@setOnLongClickListener false
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_favorite_product, parent, false))
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(productList[position])
    }

}

interface FavoriteProductsCallbacks{
    fun onItemClick(product: Product)
    fun onItemLongClick(product: Product)
    fun onListGettingEmpty()
}