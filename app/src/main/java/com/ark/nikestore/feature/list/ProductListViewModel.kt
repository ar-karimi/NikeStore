package com.ark.nikestore.feature.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ark.nikestore.R
import com.ark.nikestore.common.BaseSingleObserver
import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.data.Product
import com.ark.nikestore.data.repo.ProductRepository

class ProductListViewModel(var sort: Int, val productRepository: ProductRepository) : BaseViewModel() {

    private val productsLivedata = MutableLiveData<List<Product>>()
    private val selectedSortTitleResIdLiveData = MutableLiveData<Int>()
    private val sortTitles = arrayOf(R.string.sortLatest, R.string.sortPopular
        , R.string.sortPriceHighToLow, R.string.sortPriceLowToHigh)

    init {
        selectedSortTitleResIdLiveData.value = sortTitles[sort]
        getProducts()
    }

    fun getProducts() {

        progressBarLiveData.value = true
        productRepository.getProducts(sort)
            .doFinally { progressBarLiveData.value = false}
            .subscribe(object : BaseSingleObserver<List<Product>>(compositeDisposable) {
                override fun onSuccess(t: List<Product>) {
                    productsLivedata.value = t
                }
            })
    }

    fun getProductsLiveData(): LiveData<List<Product>> = productsLivedata
    fun getSelectedSortTitleResIdLiveData(): LiveData<Int> = selectedSortTitleResIdLiveData

    fun onSelectedSortChange(sort: Int){
        this.sort = sort
        selectedSortTitleResIdLiveData.value = sortTitles[sort]
        getProducts()
    }
}