package com.example.app.features.auth.login.presentation.login.events

import com.example.app.features.auth.login.presentation.login.model.UserCredentials
import com.example.mvi.events.MviAction

sealed class LoginAction : MviAction {
    data class HandleLoginAction(val credentials: UserCredentials) : LoginAction()
    object GoToForgotPasswordAction : LoginAction()
}