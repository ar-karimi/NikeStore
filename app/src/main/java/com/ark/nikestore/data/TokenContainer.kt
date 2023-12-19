package com.ark.nikestore.data

import timber.log.Timber

object TokenContainer {

    var token: String? = null
        private set
    var refreshToken: String? = null
        private set

    fun update(token: String?, refreshToken: String?) {
        this.token = token
        this.refreshToken = refreshToken
        Timber.i("AccessToken -> ${token?.substring(0, 10)}, RefreshToken -> ${refreshToken?.substring(0, 10)}")
    }
}