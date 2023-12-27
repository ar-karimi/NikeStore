package com.ark.nikestore.feature.profile

import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.data.CartItemCount
import com.ark.nikestore.data.TokenContainer
import com.ark.nikestore.data.repo.UserRepository
import org.greenrobot.eventbus.EventBus

class ProfileViewModel(private val userRepository: UserRepository): BaseViewModel() {

    val userName: String
        get() = userRepository.getUserName()

    val isSignedIn: Boolean
        get() = TokenContainer.token != null

    fun signOut(){
        userRepository.signOut()
        EventBus.getDefault().post(CartItemCount(0))
    }
}