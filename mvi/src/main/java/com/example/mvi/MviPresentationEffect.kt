package com.example.mvi

import com.example.mvi.events.MviEffect
import kotlinx.coroutines.flow.SharedFlow

interface MviPresentationEffect<TUiEffect : MviEffect> {
    fun uiEffect(): SharedFlow<TUiEffect>
}