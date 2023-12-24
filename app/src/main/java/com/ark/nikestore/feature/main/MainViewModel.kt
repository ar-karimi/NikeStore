package com.ark.nikestore.feature.main

import com.ark.nikestore.common.BaseSingleObserver
import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.data.CartItemCount
import com.ark.nikestore.data.TokenContainer
import com.ark.nikestore.data.repo.CartRepository
import io.reactivex.schedulers.Schedulers
import org.greenrobot.eventbus.EventBus

class MainViewModel(private val cartRepository: CartRepository): BaseViewModel() {

    fun getCartItemsCount(){
        if (!TokenContainer.token.isNullOrEmpty()){
            cartRepository.getCardItemsCount()
                .subscribeOn(Schedulers.io()) //no need to observe on main thread
                .subscribe(object : BaseSingleObserver<CartItemCount>(compositeDisposable){
                    override fun onSuccess(t: CartItemCount) {
                        EventBus.getDefault().postSticky(t) //Sticky Event holds last posted/emitted value (like LiveData)
                    }
                })
        }
    }
}