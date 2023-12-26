package com.ark.nikestore.feature.shipping

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseActivity
import com.ark.nikestore.common.BaseSingleObserver
import com.ark.nikestore.common.EXTRA_KEY_DATA
import com.ark.nikestore.common.EXTRA_KEY_ID
import com.ark.nikestore.common.openUrlInCustomTab
import com.ark.nikestore.data.PurchaseDetail
import com.ark.nikestore.data.SubmitOrderResult
import com.ark.nikestore.databinding.ActivityShippingBinding
import com.ark.nikestore.feature.checkout.CheckOutActivity
import io.reactivex.disposables.CompositeDisposable
import java.lang.IllegalStateException
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShippingActivity : BaseActivity() {

    lateinit var binding: ActivityShippingBinding
    val viewModel: ShippingViewModel by viewModel()
    val compositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shipping)
        binding.lifecycleOwner = this

        val purchaseDetail = intent.getParcelableExtra<PurchaseDetail>(EXTRA_KEY_DATA)
            ?: throw IllegalStateException("PurchaseDetail can't be null")

        binding.purchaseDetailView.purchaseDetail = purchaseDetail
        binding.purchaseDetailView.executePendingBindings()

        val onClickListener = View.OnClickListener {
            val firstName = binding.firstNameEt.text.toString()
            val lastName = binding.lastNameEt.text.toString()
            val postalCode = binding.postalCodeEt.text.toString()
            val phoneNumber = binding.phoneNumberEt.text.toString()
            val address = binding.addressEt.text.toString()

            if (firstName.isEmpty() || lastName.isEmpty() || postalCode.length != 10
                || phoneNumber.length != 11 || !phoneNumber.startsWith("09") || address.length < 20) {
                showToast("لطفا تمام فیلدها را به صورت صحیح وارد نمایید")
                return@OnClickListener
            }

            viewModel.submitOrder(firstName,lastName, postalCode, phoneNumber, address
                , if (it.id == R.id.onlinePaymentBtn) PAYMENT_METHOD_ONLINE else PAYMENT_METHOD_COD)

                .subscribe(object : BaseSingleObserver<SubmitOrderResult>(compositeDisposable){
                    override fun onSuccess(t: SubmitOrderResult) {
                        if (t.bank_gateway_url.isNotEmpty())
                            openUrlInCustomTab(this@ShippingActivity, t.bank_gateway_url)
                        else
                            startActivity(
                                Intent(this@ShippingActivity, CheckOutActivity::class.java).apply {
                                    putExtra(EXTRA_KEY_ID, t.order_id)
                                })

                        finish()
                    }
                })
        }

        binding.onlinePaymentBtn.setOnClickListener(onClickListener)
        binding.cashOnDeliveryBtn.setOnClickListener(onClickListener)

        viewModel.progressBarLiveData.observe(this){
            showProgressBar(it)
        }

        binding.toolbar.onBackBtnClickListener = View.OnClickListener {
            finish()
        }
    }
}