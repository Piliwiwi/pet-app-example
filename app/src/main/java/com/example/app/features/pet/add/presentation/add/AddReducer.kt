package com.example.app.features.pet.add.presentation.add

import com.example.app.features.pet.add.presentation.add.event.AddResult
import com.example.app.features.pet.add.presentation.add.event.AddResult.AddPetResult
import com.example.app.features.pet.add.presentation.add.event.AddResult.LoadBreedListResult
import com.example.app.features.pet.add.presentation.add.event.AddResult.LoadSpeciesListResult
import com.example.app.features.pet.add.presentation.add.event.AddUiState
import com.example.mvi.MviReducer

class AddReducer : MviReducer<AddUiState, AddResult> {
    override fun AddUiState.reduceWith(result: AddResult): AddUiState {
        return when (val currentState = this) {
            is AddUiState.DefaultUiState -> currentState reduceWith result
            is AddUiState.LoadingUiState -> currentState reduceWith result
            is AddUiState.ShowContentUiState -> currentState reduceWith result
            is AddUiState.ErrorUiState -> currentState reduceWith result
        }
    }

    private infix fun AddUiState.DefaultUiState.reduceWith(result: AddResult): AddUiState {
        return when (result) {
            LoadSpeciesListResult.InProgress -> AddUiState.LoadingUiState
            LoadBreedListResult.InProgress -> AddUiState.LoadingUiState
            else -> this
        }
    }

    private infix fun AddUiState.LoadingUiState.reduceWith(result: AddResult): AddUiState {
        return when (result) {
            is LoadSpeciesListResult.NetworkError -> AddUiState.ErrorUiState
            is LoadBreedListResult.NetworkError -> AddUiState.ErrorUiState
            is LoadSpeciesListResult.Success -> AddUiState.ShowContentUiState(result.data)
            is LoadBreedListResult.Success -> AddUiState.ShowContentUiState(result.data)
            else -> this
        }
    }

    private infix fun AddUiState.ErrorUiState.reduceWith(result: AddResult): AddUiState {
        return when (result) {
            LoadSpeciesListResult.InProgress -> AddUiState.LoadingUiState
            LoadBreedListResult.InProgress -> AddUiState.LoadingUiState
            else -> this
        }
    }

    private infix fun AddUiState.ShowContentUiState.reduceWith(result: AddResult): AddUiState {
        return when (result) {
            AddPetResult.InProgress -> AddUiState.LoadingUiState
            else -> this
        }
    }
}