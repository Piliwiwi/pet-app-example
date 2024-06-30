package com.example.app.features.auth.login.presentation.login.events

import com.example.mvi.events.MviResult
import com.example.network.utils.NetworkError

sealed class LoginResult : MviResult {
    object RenderUiResult : LoginResult()

    sealed class HandLeLoginResult : LoginResult() {
        object InProgress : HandLeLoginResult()
        data class APIError(val error: NetworkError) : HandLeLoginResult()
        data class Success(val token: String) : HandLeLoginResult()
    }

    object GoToForgotPasswordResult : LoginResult()
}