package com.ark.nikestore.feature.checkout

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseActivity
import com.ark.nikestore.common.EXTRA_KEY_ID
import com.ark.nikestore.databinding.ActivityCheckOutBinding
import com.ark.nikestore.feature.order.OrderHistoryActivity
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class CheckOutActivity : BaseActivity() {

    private lateinit var binding: ActivityCheckOutBinding
    private val viewModel: CheckOutViewModel by viewModel() {
        val uri: Uri? = intent.data
        if (uri != null) // in case of online payment (comes from browser)
            parametersOf(uri.getQueryParameter("order_id")!!.toInt())
        else // in case of cod payment (comes from ShippingActivity)
            parametersOf(intent.extras!!.getInt(EXTRA_KEY_ID))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_check_out)
        binding.lifecycleOwner = this

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