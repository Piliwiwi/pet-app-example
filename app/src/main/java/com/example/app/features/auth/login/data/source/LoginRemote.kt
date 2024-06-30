package com.example.app.features.auth.login.data.source

interface LoginRemote {
    suspend fun login(credentials: com.example.app.features.auth.login.data.remote.model.RemoteUserCredentialsParams): com.example.app.features.auth.login.data.remote.model.RemoteAuthCredentials
}