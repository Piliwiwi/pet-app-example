package com.example.app.features.auth.login.data.remote.retrofit

import retrofit2.http.Body
import retrofit2.http.POST

interface LoginWebServiceAPI {
    @POST("api/auth/login")
    suspend fun login(@Body request: com.example.app.features.auth.login.data.remote.model.RemoteUserCredentialsParams): com.example.app.features.auth.login.data.remote.model.RemoteAuthCredentials
}