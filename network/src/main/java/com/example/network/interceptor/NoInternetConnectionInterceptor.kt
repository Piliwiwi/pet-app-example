package com.example.network.interceptor

import com.example.network.exception.NetworkException.NoInternetConnectionException
import com.example.network.utils.NetworkConnection
import okhttp3.Interceptor
import okhttp3.Response

class NoInternetConnectionInterceptor(private val networkConnection: NetworkConnection) :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (networkConnection.isNetworkAvailable()) {
            return chain.proceed(chain.request())
        } else {
            throw NoInternetConnectionException
        }
    }
}