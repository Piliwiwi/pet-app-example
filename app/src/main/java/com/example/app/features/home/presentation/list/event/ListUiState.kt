package com.example.app.features.home.presentation.list.event

import com.example.mvi.events.MviUiState
import com.example.uicomponents.data.PetCardResponse

sealed class ListUiState : MviUiState {
    object DefaultUiState : ListUiState()
    data class ErrorUiState(val error: Throwable) : ListUiState()
    object LoadingUiState : ListUiState()
    data class ShowContentUiState(val pets: List<PetCardResponse>) : ListUiState()
}