package com.example.app.features.auth.login.data.source

interface LoginCache {
    suspend fun storeToken(token: String)
}