package com.ark.nikestore.feature.order

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseActivity
import com.ark.nikestore.databinding.ActivityOrderHistoryBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderHistoryActivity : BaseActivity<ActivityOrderHistoryBinding>(R.layout.activity_order_history) {

    val viewModel: OrderHistoryViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding.toolbarView.onBackBtnClickListener = View.OnClickListener {
            finish()
        }

        viewModel.getOrderHistoryItemsLiveData().observe(this){
            binding.orderHistoryRv.layoutManager = LinearLayoutManager(this,
                RecyclerView.VERTICAL, false)
            binding.orderHistoryRv.adapter = OrderHistoryItemAdapter(this, it)
        }

        viewModel.progressBarLiveData.observe(this){
            showProgressBar(it)
        }
    }
}