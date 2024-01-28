package com.ark.nikestore.feature.profile

import com.ark.nikestore.common.BaseViewModel
import com.ark.nikestore.data.CartItemCount
import com.ark.nikestore.data.TokenContainer
import com.ark.nikestore.data.repo.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository): BaseViewModel() {

    val userName: String
        get() = userRepository.getUserName()

    val isSignedIn: Boolean
        get() = TokenContainer.token != null

    fun signOut(){
        userRepository.signOut()
        EventBus.getDefault().post(CartItemCount(0))
    }
}