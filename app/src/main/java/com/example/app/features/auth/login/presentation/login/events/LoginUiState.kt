package com.example.app.features.auth.login.presentation.login.events

import com.example.mvi.events.MviUiState

sealed class LoginUiState : MviUiState {
    object DefaultUiState : LoginUiState()
    object LoadingUiState : LoginUiState()
    object ShowLoginScreenUiState : LoginUiState()
}