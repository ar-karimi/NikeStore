package com.ark.nikestore.feature.product

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.common.EXTRA_KEY_DATA
import com.ark.nikestore.data.Product

class ProductDetailViewModel(bundle: Bundle):BaseViewModel() {

    private val productLiveData = MutableLiveData<Product>()
    init {
        productLiveData.value = bundle.getParcelable(EXTRA_KEY_DATA)
    }

    fun getProductLiveData(): LiveData<Product> = productLiveData
}