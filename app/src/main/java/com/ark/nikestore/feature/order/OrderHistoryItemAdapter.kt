package com.ark.nikestore.feature.order

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ark.nikestore.R
import com.ark.nikestore.common.dpToPx
import com.ark.nikestore.data.OrderHistoryItem
import com.ark.nikestore.databinding.ItemOrderHistoryBinding
import com.ark.nikestore.view.customViews.BaseImageView

class OrderHistoryItemAdapter(private val context: Context, private val orderHistoryList: List<OrderHistoryItem>): RecyclerView.Adapter<OrderHistoryItemAdapter.ViewHolder>() {

    private val layoutParams : LinearLayout.LayoutParams

        init {
            val size = context.dpToPx(100)
            val margin = context.dpToPx(8)
            layoutParams = LinearLayout.LayoutParams(size, size)
            layoutParams.setMargins(margin, 0, margin, 0)
        }

    inner class ViewHolder(val binding: ItemOrderHistoryBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(orderHistoryItem: OrderHistoryItem){
            binding.orderHistoryItem = orderHistoryItem

            binding.orderProductsLl.removeAllViews() //to prevent duplicate items when scroll (Recycle)
            orderHistoryItem.order_items.forEach {
                val baseImageView = BaseImageView(context)
                baseImageView.layoutParams = layoutParams
                baseImageView.setImageURI(it.product.image)

                binding.orderProductsLl.addView(baseImageView)
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_order_history, parent, false))

    override fun getItemCount(): Int = orderHistoryList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(orderHistoryList[position])
    }

}