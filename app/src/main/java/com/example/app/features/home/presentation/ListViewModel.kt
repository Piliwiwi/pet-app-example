package com.example.app.features.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.features.home.presentation.list.ListProcessor
import com.example.app.features.home.presentation.list.ListReducer
import com.example.app.features.home.presentation.list.event.ListAction.LoadPetListAction
import com.example.app.features.home.presentation.list.event.ListUIntent
import com.example.app.features.home.presentation.list.event.ListUIntent.InitialUIntent
import com.example.app.features.home.presentation.list.event.ListUIntent.ReloadUIntent
import com.example.app.features.home.presentation.list.event.ListUiState
import com.example.app.features.home.presentation.list.event.ListUiState.DefaultUiState
import com.example.mvi.MviPresentation
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan

class ListViewModel(
    private val processor: ListProcessor,
    private val reducer: ListReducer
) : ViewModel(),
    MviPresentation<ListUIntent, ListUiState> {
    private val defaultUiState: ListUiState = DefaultUiState
    private val uiState: MutableStateFlow<ListUiState> = MutableStateFlow(defaultUiState)

    fun processUserIntents(
        userIntents: Flow<ListUIntent>,
        scope: CoroutineScope = viewModelScope
    ) {
        userIntents
            .buffer()
            .flatMapMerge { userIntent ->
                processor.actionProcessor(userIntent.toAction())
            }
            .scan(defaultUiState) { currentState, result ->
                with(reducer) { currentState reduceWith result }
            }
            .onEach { uiState.value = it }
            .launchIn(scope)
    }

    private fun ListUIntent.toAction() =
        when (this) {
            is InitialUIntent -> LoadPetListAction
            is ReloadUIntent -> LoadPetListAction
        }

    override fun uiStates() = uiState.asStateFlow()
}