package com.example.app.features.auth.login.presentation.login.events

import com.example.app.features.auth.login.presentation.login.model.UserCredentials
import com.example.mvi.events.MviUserIntent

sealed class LoginUIntent: MviUserIntent {
    data class LoggingUIntent(val credentials: UserCredentials): LoginUIntent()
    object ForgotPasswordUIntent: LoginUIntent()
}