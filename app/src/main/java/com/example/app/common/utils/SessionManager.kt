package com.example.app.common.utils

import android.app.Activity
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import com.example.app.features.auth.login.ui.AuthActivity
import com.example.network.security.TokenManager
import java.lang.ref.WeakReference

class SessionManager(private val tokenManager: TokenManager) {
    fun logout(activityReference: WeakReference<Activity>?) {
        tokenManager.revoke()
        activityReference?.get()?.apply {
            val intent = AuthActivity.makeIntent(this).apply {
                flags = FLAG_ACTIVITY_NEW_TASK or FLAG_ACTIVITY_CLEAR_TASK
            }
            startActivity(intent)
            finish()
        }
    }
}