package com.example.app.features.pet.add.presentation.add.event

import com.example.app.features.pet.add.presentation.add.model.SelectorData
import com.example.mvi.events.MviUiState

sealed class AddUiState: MviUiState {
    object DefaultUiState: AddUiState()
    object LoadingUiState: AddUiState()
    object ErrorUiState: AddUiState()
    data class ShowContentUiState(val data: List<SelectorData>): AddUiState()
}