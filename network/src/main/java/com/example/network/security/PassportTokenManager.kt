package com.example.network.security

import android.content.SharedPreferences

class PassportTokenManager(
    private val sharedPreferences: SharedPreferences? //TODO - Use Secure EncryptedSharedPreference
) : TokenManager {
    override fun getToken() = sharedPreferences?.getString(KEY_PASSPORT_TOKEN, "").orEmpty()

    override fun setToken(token: String) {
        sharedPreferences?.edit()?.putString(KEY_PASSPORT_TOKEN, token)?.apply()
    }

    override fun revoke() {
        sharedPreferences?.edit()?.remove(KEY_PASSPORT_TOKEN)?.apply()
    }

    override fun hasToken() = sharedPreferences?.contains(KEY_PASSPORT_TOKEN) == true

    override fun isValid(token: String): Boolean {
        if (token.isEmpty()) return false
        val currentToken = getToken()
        if (currentToken.isEmpty()) return false
        return token == currentToken
    }

    companion object {
        const val KEY_PASSPORT_TOKEN = "com.example.passport.token"
    }
}