package com.example.app.features.auth.login.data.cache

import com.example.network.security.PassportTokenManager

class LoginCacheImpl(
    private val passportManager: PassportTokenManager
) : com.example.app.features.auth.login.data.source.LoginCache {
    override suspend fun storeToken(token: String) {
        passportManager.setToken(token)
    }
}