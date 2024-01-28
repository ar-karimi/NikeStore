package com.ark.nikestore.services.httpClient

import com.ark.nikestore.data.TokenContainer
import com.ark.nikestore.data.TokenResponse
import com.ark.nikestore.data.repo.source.CLIENT_ID
import com.ark.nikestore.data.repo.source.CLIENT_SECRET
import com.ark.nikestore.data.repo.source.UserDataSource
import com.google.gson.JsonObject
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import timber.log.Timber
import javax.inject.Inject

class BaseAuthenticator : Authenticator {

    @Inject
    lateinit var apiService: ApiService
    @Inject
    lateinit var userLocalDataSource: UserDataSource

    //Calls any time 401 occurred (is synchronous)
    override fun authenticate(route: Route?, response: Response): Request? {

        if (TokenContainer.token != null && TokenContainer.refreshToken != null &&
            !response.request.url.pathSegments.last().equals("token", false)) { // to not run in unSuccessful login and cause infinite loop

            try {
                val token = refreshToken()
                if (token.isEmpty())
                    return null

                return response.request.newBuilder().addHeader("Authorization", "Bearer $token")
                    .build()

            } catch (exception: Exception) {
                Timber.e(exception)
            }
        }

        return null
    }

    private fun refreshToken(): String {
        val response: retrofit2.Response<TokenResponse> = apiService.refreshToken(JsonObject().apply {
            addProperty("grant_type", "refresh_token")
            addProperty("refresh_token", TokenContainer.refreshToken)
            addProperty("client_id", CLIENT_ID)
            addProperty("client_secret", CLIENT_SECRET)
        }).execute()

        response.body()?.let {
            userLocalDataSource.saveToken(it.access_token, it.refresh_token)
            TokenContainer.update(it.access_token, it.refresh_token)
            return it.access_token
        }

        return ""
    }
}