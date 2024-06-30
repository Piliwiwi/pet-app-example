package com.example.mvi

import com.example.mvi.events.MviUiState
import com.example.mvi.events.MviUserIntent
import kotlinx.coroutines.flow.Flow

interface MviUi<TUserIntent : MviUserIntent, in TUiState : MviUiState> {
    fun userIntents(): Flow<TUserIntent>
    fun renderUiStates(uiStates: TUiState)
}