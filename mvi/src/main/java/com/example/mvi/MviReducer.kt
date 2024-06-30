package com.example.mvi

import com.example.mvi.events.MviResult
import com.example.mvi.events.MviUiState

interface MviReducer<TUiState : MviUiState, TResult : MviResult> {
    infix fun TUiState.reduceWith(result: TResult): TUiState
}