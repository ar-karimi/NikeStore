package com.ark.nikestore.data.repo.source

import com.ark.nikestore.data.MessageResponse
import com.ark.nikestore.data.TokenResponse
import io.reactivex.Single

interface UserDataSource {

    fun login(userName: String, password: String): Single<TokenResponse>
    fun signUp(userName: String, password: String): Single<MessageResponse>
    fun loadToken()
    fun saveToken(token: String, refreshToken: String)
}