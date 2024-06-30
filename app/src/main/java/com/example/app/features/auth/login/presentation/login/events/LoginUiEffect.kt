package com.example.app.features.auth.login.presentation.login.events

import com.example.mvi.events.MviEffect
import com.example.network.utils.NetworkError

sealed class LoginUiEffect : MviEffect {
    data class NetworkErrorUiEffect(val error: NetworkError) : LoginUiEffect()
}