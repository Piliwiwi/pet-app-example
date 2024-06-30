package com.example.app.features.auth.login.presentation.login

import com.example.app.features.auth.login.presentation.login.events.LoginResult
import com.example.app.features.auth.login.presentation.login.events.LoginResult.GoToForgotPasswordResult
import com.example.app.features.auth.login.presentation.login.events.LoginResult.HandLeLoginResult.APIError
import com.example.app.features.auth.login.presentation.login.events.LoginResult.HandLeLoginResult.InProgress
import com.example.app.features.auth.login.presentation.login.events.LoginResult.HandLeLoginResult.Success
import com.example.app.features.auth.login.presentation.login.events.LoginUiState
import com.example.app.features.auth.login.presentation.login.events.LoginUiState.DefaultUiState
import com.example.app.features.auth.login.presentation.login.events.LoginUiState.LoadingUiState
import com.example.app.features.auth.login.presentation.login.events.LoginUiState.ShowLoginScreenUiState
import com.example.mvi.MviReducer

class LoginReducer : MviReducer<LoginUiState, LoginResult> {
    override fun LoginUiState.reduceWith(result: LoginResult): LoginUiState {
        return when (val currentState = this) {
            is DefaultUiState -> currentState reduceWith result
            is LoadingUiState -> currentState reduceWith result
            is ShowLoginScreenUiState -> currentState reduceWith result
            else -> this
        }
    }

    infix fun DefaultUiState.reduceWith(result: LoginResult): LoginUiState {
        return when (result) {
            InProgress -> LoadingUiState
            else -> this
        }
    }

    infix fun LoadingUiState.reduceWith(result: LoginResult): LoginUiState {
        return when (result) {
            is Success -> ShowLoginScreenUiState
            is APIError -> DefaultUiState
            else -> this
        }
    }

    infix fun ShowLoginScreenUiState.reduceWith(result: LoginResult): LoginUiState {
        return when (result) {
            InProgress -> LoadingUiState
            GoToForgotPasswordResult -> this
            else -> this
        }
    }
}