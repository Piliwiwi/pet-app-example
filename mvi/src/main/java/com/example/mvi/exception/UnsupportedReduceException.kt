package com.example.mvi.exception

import com.example.mvi.events.MviResult
import com.example.mvi.events.MviUiState
import java.lang.RuntimeException

class UnsupportedReduceException(state: MviUiState, result: MviResult) :
    RuntimeException("Cannot reduce state: $state with result: $result")