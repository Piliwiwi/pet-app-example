package com.example.app.features.auth.login.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.features.auth.login.presentation.login.LoginProcessor
import com.example.app.features.auth.login.presentation.login.LoginReducer
import com.example.app.features.auth.login.presentation.login.events.LoginAction.GoToForgotPasswordAction
import com.example.app.features.auth.login.presentation.login.events.LoginAction.HandleLoginAction
import com.example.app.features.auth.login.presentation.login.events.LoginResult
import com.example.app.features.auth.login.presentation.login.events.LoginResult.HandLeLoginResult.APIError
import com.example.app.features.auth.login.presentation.login.events.LoginUIntent
import com.example.app.features.auth.login.presentation.login.events.LoginUIntent.ForgotPasswordUIntent
import com.example.app.features.auth.login.presentation.login.events.LoginUIntent.LoggingUIntent
import com.example.app.features.auth.login.presentation.login.events.LoginUiEffect
import com.example.app.features.auth.login.presentation.login.events.LoginUiEffect.NetworkErrorUiEffect
import com.example.app.features.auth.login.presentation.login.events.LoginUiState
import com.example.app.features.auth.login.presentation.login.events.LoginUiState.DefaultUiState
import com.example.mvi.MviPresentation
import com.example.mvi.MviPresentationEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan

class LoginViewModel(
    private val processor: LoginProcessor,
    private val reducer: LoginReducer
) : ViewModel(),
    MviPresentation<LoginUIntent, LoginUiState>,
    MviPresentationEffect<LoginUiEffect> {

    private val defaultUiState: LoginUiState = DefaultUiState
    private val uiState: MutableStateFlow<LoginUiState> = MutableStateFlow(defaultUiState)
    private val uiEffect: MutableSharedFlow<LoginUiEffect> = MutableSharedFlow()

    fun processUserIntents(
        userIntents: Flow<LoginUIntent>,
        scope: CoroutineScope = viewModelScope
    ) {
        userIntents
            .buffer()
            .flatMapMerge { userIntent ->
                processor.actionProcessor(userIntent.toAction())
            }
            .handleEffect()
            .scan(defaultUiState) { currentState, result ->
                with(reducer) { currentState reduceWith result }
            }
            .onEach { uiState.value = it }
            .launchIn(scope)
    }

    private fun LoginUIntent.toAction() =
        when (this) {
            is LoggingUIntent -> HandleLoginAction(credentials)
            ForgotPasswordUIntent -> GoToForgotPasswordAction
        }

    private fun Flow<LoginResult>.handleEffect(): Flow<LoginResult> =
        onEach { change ->
            val event = when (change) {
                is APIError -> NetworkErrorUiEffect(change.error)
                else -> return@onEach
            }
            uiEffect.emit(event)
        }

    override fun uiStates() = uiState.asStateFlow()
    override fun uiEffect() = uiEffect.asSharedFlow()
}