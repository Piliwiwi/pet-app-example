package com.example.network.interceptor

import com.example.network.event.ExpiredTokenEvent.isForbiddenResponse
import com.example.network.security.TokenManager
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor constructor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val headers = if (tokenManager.getToken().isNotEmpty()) {
            chain.request().headers
                .newBuilder()
                .add("Authorization", "SecretWord ${tokenManager.getToken()}")
                .build()
        } else {
            chain.request().headers
                .newBuilder()
                .build()
        }
        val request = chain.request().newBuilder().headers(headers).build()
        val response = chain.proceed(request)
        isForbiddenResponse.value = response.code == 401
        return response
    }
}