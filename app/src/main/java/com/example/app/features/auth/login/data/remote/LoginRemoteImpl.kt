package com.example.app.features.auth.login.data.remote

import com.example.app.features.auth.login.data.remote.model.RemoteUserCredentialsParams
import com.example.app.features.auth.login.data.remote.retrofit.LoginWebServiceAPI
import com.example.app.features.auth.login.data.source.LoginRemote

class LoginRemoteImpl(private val api: LoginWebServiceAPI) :
    LoginRemote {
    override suspend fun login(credentials: RemoteUserCredentialsParams) = api.login(credentials)
}