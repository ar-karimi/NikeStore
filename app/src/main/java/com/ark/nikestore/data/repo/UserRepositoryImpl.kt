package com.ark.nikestore.data.repo

import com.ark.nikestore.data.TokenContainer
import com.ark.nikestore.data.TokenResponse
import com.ark.nikestore.data.repo.source.UserDataSource
import io.reactivex.Completable

class UserRepositoryImpl(val userRemoteDataSource: UserDataSource, val userLocalDataSource: UserDataSource)
    : UserRepository {
    override fun login(userName: String, password: String): Completable {
        return userRemoteDataSource.login(userName, password).doOnSuccess {
            onSuccessfulLogin(it)
        }.ignoreElement()
    }

    override fun signUp(userName: String, password: String): Completable {
        /*return userRemoteDataSource.signUp(userName, password).doOnSuccess {
            login(userName, password)
        }.ignoreElement()*/             //in this method login status will not check!!

        return userRemoteDataSource.signUp(userName, password).flatMap {
            userRemoteDataSource.login(userName, password)
        }.doOnSuccess {
            onSuccessfulLogin(it)
        }.ignoreElement()
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    private fun onSuccessfulLogin(tokenResponse: TokenResponse){
        userLocalDataSource.saveToken(tokenResponse.access_token, tokenResponse.refresh_token)
        TokenContainer.update(tokenResponse.access_token, tokenResponse.refresh_token)
    }
}