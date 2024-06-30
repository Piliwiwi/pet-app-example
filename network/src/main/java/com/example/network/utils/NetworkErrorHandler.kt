package com.example.network.utils

import com.google.gson.Gson
import retrofit2.HttpException

class NetworkErrorHandler {
    fun Throwable.toNetworkError(genericMessage: String): NetworkError =
        if (this is HttpException) {
            val jsonError = response()?.errorBody()?.string()
            jsonError?.toNetworkError() ?: NetworkError(genericMessage)
        } else {
            NetworkError(genericMessage)
        }

    private fun String.toNetworkError() =
        try {
            Gson().fromJson(this, NetworkError::class.java)
        } catch (e: Exception) {
            null
        }
}