package com.ark.nikestore.data.repo

import com.ark.nikestore.data.TokenContainer
import com.ark.nikestore.data.TokenResponse
import com.ark.nikestore.data.repo.source.UserDataSource
import com.ark.nikestore.di.repositories.userRepository.UserLocalDataSourceQualifier
import com.ark.nikestore.di.repositories.userRepository.UserRemoteDataSourceQualifier
import io.reactivex.Completable
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    @UserRemoteDataSourceQualifier
    private val userRemoteDataSource: UserDataSource,
    @UserLocalDataSourceQualifier
    private val userLocalDataSource: UserDataSource
) : UserRepository {
    override fun login(userName: String, password: String): Completable {
        return userRemoteDataSource.login(userName, password).doOnSuccess {
            onSuccessfulLogin(userName, it)
        }.ignoreElement()
    }

    override fun signUp(userName: String, password: String): Completable {
        /*return userRemoteDataSource.signUp(userName, password).doOnSuccess {
            login(userName, password)
        }.ignoreElement()*/             //in this method login status will not check!!

        return userRemoteDataSource.signUp(userName, password).flatMap {
            userRemoteDataSource.login(userName, password)
        }.doOnSuccess {
            onSuccessfulLogin(userName, it)
        }.ignoreElement()
    }

    override fun loadToken() {
        userLocalDataSource.loadToken()
    }

    override fun getUserName(): String = userLocalDataSource.getUserName()
    override fun signOut() {
        userLocalDataSource.signOut()
        TokenContainer.update(null, null)
    }

    private fun onSuccessfulLogin(userName: String, tokenResponse: TokenResponse) {
        userLocalDataSource.saveToken(tokenResponse.access_token, tokenResponse.refresh_token)
        TokenContainer.update(tokenResponse.access_token, tokenResponse.refresh_token)

        userLocalDataSource.saveUserName(userName)
    }
}