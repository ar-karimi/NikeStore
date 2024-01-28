package com.ark.nikestore.feature.checkout

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseActivity
import com.ark.nikestore.common.EXTRA_KEY_ID
import com.ark.nikestore.databinding.ActivityCheckOutBinding
import com.ark.nikestore.feature.order.OrderHistoryActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CheckOutActivity : BaseActivity<ActivityCheckOutBinding>(R.layout.activity_check_out) {

    private val viewModel: CheckOutViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val uri: Uri? = intent.data
        if (uri != null) // in case of online payment (comes from browser)
            viewModel.checkout(uri.getQueryParameter("order_id")!!.toInt())
        else // in case of COD payment (comes from ShippingActivity)
            viewModel.checkout(intent.extras!!.getInt(EXTRA_KEY_ID))

        binding.viewModel = viewModel
        binding.executePendingBindings()

        viewModel.progressBarLiveData.observe(this){
            showProgressBar(it)
        }

        binding.toolbar.onBackBtnClickListener = View.OnClickListener {
            finish()
        }

        binding.returnHomeBtn.setOnClickListener {
            finish()
        }

        binding.orderHistoryBtn.setOnClickListener {
            startActivity(Intent(this, OrderHistoryActivity::class.java))
            finish()
        }
    }
}