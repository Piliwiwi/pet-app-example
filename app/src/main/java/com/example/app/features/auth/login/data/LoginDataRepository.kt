package com.example.app.features.auth.login.data

import com.example.app.features.auth.login.data.remote.model.RemoteUserCredentialsParams
import com.example.app.features.auth.login.data.source.LoginCache
import com.example.app.features.auth.login.data.source.LoginRemote
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.flow.flow

class LoginDataRepository(
    private val remote: LoginRemote,
    private val cache: LoginCache
) {
    fun login(credentials: RemoteUserCredentialsParams) = flow {
        val auth = remote.login(credentials)
        cache.storeToken(auth.token)
        FirebaseCrashlytics.getInstance().setUserId(credentials.email)
        emit(auth)
    }
}