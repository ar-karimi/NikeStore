package com.ark.nikestore.feature.cart

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ark.nikestore.R
import com.ark.nikestore.data.CartItem
import com.ark.nikestore.data.PurchaseDetail
import com.ark.nikestore.databinding.ItemCartBinding
import com.ark.nikestore.databinding.ItemPurchaseDetailsBinding

const val VIEW_TYPE_CART_ITEM = 0
const val VIEW_TYPE_PURCHASE_DETAILS = 1
class CartAdapter(val cartItemList: MutableList<CartItem>, val callbacks: CartItemViewCallbacks): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var purchaseDetail: PurchaseDetail? = null
    inner class CartItemViewHolder(val binding: ItemCartBinding): RecyclerView.ViewHolder(binding.root){
        fun bindCartItem(cartItem: CartItem){
            binding.cartItem = cartItem

            binding.removeFromCartBtn.setOnClickListener { callbacks.onRemoveItemClick(cartItem) }

            binding.changeCountPb.visibility = if (cartItem.changeCountPbIsVisible) View.VISIBLE else View.GONE
            binding.cartItemCountTv.visibility = if (cartItem.changeCountPbIsVisible) View.INVISIBLE else View.VISIBLE

            binding.increaseBtn.setOnClickListener {
                cartItem.changeCountPbIsVisible = true     //hold it in dataModel to prevent bug when scroll and recycle items
                binding.changeCountPb.visibility = View.VISIBLE
                binding.cartItemCountTv.visibility = View.INVISIBLE
                callbacks.onIncreaseItemClick(cartItem)
            }
            binding.decreaseBtn.setOnClickListener {
                if (cartItem.count > 1){
                    cartItem.changeCountPbIsVisible = true
                    binding.changeCountPb.visibility = View.VISIBLE
                    binding.cartItemCountTv.visibility = View.INVISIBLE
                    callbacks.onDecreaseItemClick(cartItem)
                }
            }
            binding.productIv.setOnClickListener { callbacks.onProductImageClick(cartItem) }

            binding.executePendingBindings()
        }
    }

    inner class PurchaseDetailsViewHolder(val binding: ItemPurchaseDetailsBinding): RecyclerView.ViewHolder(binding.root){
        fun bindPurchaseDetails(purchaseDetail: PurchaseDetail){
            binding.purchaseDetail = purchaseDetail
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == VIEW_TYPE_CART_ITEM)
            CartItemViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_cart, parent, false))
        else
            PurchaseDetailsViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_purchase_details, parent, false))
    }

    override fun getItemCount(): Int = cartItemList.size + 1 // +1 for Purchase details item

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is CartItemViewHolder)
            holder.bindCartItem(cartItemList[position])
        else if (holder is PurchaseDetailsViewHolder)
            purchaseDetail?.let {
                holder.bindPurchaseDetails(it)
            }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == cartItemList.size)
            VIEW_TYPE_PURCHASE_DETAILS
        else
            VIEW_TYPE_CART_ITEM
    }

    fun removeCartItem(cartItem: CartItem){
        val index = cartItemList.indexOf(cartItem)
        if (index > -1){
            cartItemList.removeAt(index)   //We need MutableList<> to call this
            notifyItemRemoved(index)
        }
    }

    fun increaseOrDecreaseCount(cartItem: CartItem){
        val index = cartItemList.indexOf(cartItem)
        if (index > -1){
            cartItemList[index].changeCountPbIsVisible = false
            notifyItemChanged(index)
        }
    }



    interface CartItemViewCallbacks{
        fun onRemoveItemClick(cartItem: CartItem)
        fun onIncreaseItemClick(cartItem: CartItem)
        fun onDecreaseItemClick(cartItem: CartItem)
        fun onProductImageClick(cartItem: CartItem)
    }
}