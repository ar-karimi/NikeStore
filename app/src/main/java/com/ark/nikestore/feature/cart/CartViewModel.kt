package com.ark.nikestore.feature.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ark.nikestore.common.BaseSingleObserver
import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.data.CartItem
import com.ark.nikestore.data.CartResponse
import com.ark.nikestore.data.PurchaseDetail
import com.ark.nikestore.data.TokenContainer
import com.ark.nikestore.data.repo.CartRepository
import io.reactivex.Completable

class CartViewModel(private val cartRepository: CartRepository) : BaseViewModel() {

    private val cartItemsLiveData = MutableLiveData<List<CartItem>>()
    private val purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()
    private fun getCartItems() {
        if (!TokenContainer.token.isNullOrEmpty()) {
            progressBarLiveData.value = true
            cartRepository.get()
                .doFinally { progressBarLiveData.value = false }
                .subscribe(object : BaseSingleObserver<CartResponse>(compositeDisposable) {
                    override fun onSuccess(t: CartResponse) {
                        if (t.cart_items.isNotEmpty()){
                            cartItemsLiveData.value = t.cart_items
                            purchaseDetailLiveData.value =
                                PurchaseDetail(t.total_price, t.shipping_cost, t.payable_price)
                        }
                    }
                })
        }
    }

    fun removeItemFromCart(cartItem: CartItem): Completable{
        progressBarLiveData.value = true
        return cartRepository.remove(cartItem.cart_item_id)
            .doAfterSuccess { calculateAndPublishPurchaseDetail() }       //Calls after onComplete and onSuccess
            .doFinally { progressBarLiveData.value = false }
            .ignoreElement()
    }

    fun increaseCartItemCount(cartItem: CartItem): Completable{

       /* Here ++ can change the value of original cartItem in the passed list from cartItemsLiveData.value,
         because actually its reference passed, even it is List<> or MutableList<> */

        return cartRepository.changeCount(cartItem.cart_item_id, ++cartItem.count)
            .doAfterSuccess { calculateAndPublishPurchaseDetail() }
            .ignoreElement()
    }
    fun decreaseCartItemCount(cartItem: CartItem): Completable{
        return cartRepository.changeCount(cartItem.cart_item_id, --cartItem.count)
            .doAfterSuccess { calculateAndPublishPurchaseDetail() }
            .ignoreElement()
    }

    fun refresh(){
        getCartItems()
    }

    private fun calculateAndPublishPurchaseDetail(){
        cartItemsLiveData.value?.let { cartItems ->
            purchaseDetailLiveData.value?.let {purchaseDetail ->
                var totalPrice = 0
                var payablePrice = 0
                cartItems.forEach {
                    totalPrice += (it.product.price + it.product.discount) * it.count     //we don't have prev price in this request and we should calculate it
                    payablePrice += it.product.price * it.count
                }
                purchaseDetail.total_price = totalPrice
                purchaseDetail.payable_price = payablePrice
                purchaseDetailLiveData.postValue(purchaseDetail)
            }
        }
    }

    fun getCartItemsLiveData(): LiveData<List<CartItem>> = cartItemsLiveData
    fun getPurchaseDetailLiveData(): LiveData<PurchaseDetail> = purchaseDetailLiveData
}