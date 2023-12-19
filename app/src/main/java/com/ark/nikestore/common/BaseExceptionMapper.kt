package com.ark.nikestore.common

import com.ark.nikestore.R
import org.json.JSONObject
import retrofit2.HttpException
import timber.log.Timber

class BaseExceptionMapper {

    companion object {
        fun map(throwable: Throwable): BaseException {
            if (throwable is HttpException) {
                try {
                    val errorJasonObject = JSONObject(throwable.response()?.errorBody()!!.string())
                    val errorMessage = errorJasonObject.getString("message")

                    val isInLoginReq = // to not emmit AUTH exception in unSuccessful login and open login page again
                        throwable.response()?.raw()!!.request.url.pathSegments.last().equals("token", false)

                    return BaseException(
                        if (throwable.code() == 401 && !isInLoginReq) BaseException.Type.AUTH else BaseException.Type.SIMPLE,
                        serverMessage = errorMessage
                    )
                } catch (exception: Exception) {
                    Timber.e(exception)
                }
            }
            return BaseException(BaseException.Type.SIMPLE, R.string.unknown_error)
        }
    }
}