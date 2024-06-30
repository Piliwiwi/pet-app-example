package com.example.mvi

import com.example.mvi.events.MviUiState
import com.example.mvi.events.MviUserIntent
import kotlinx.coroutines.flow.StateFlow

interface MviPresentation<TUserIntent : MviUserIntent, TUiState : MviUiState> {
    fun uiStates(): StateFlow<TUiState>
}