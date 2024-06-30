package com.example.app.features.home.presentation.list

import com.example.app.features.home.presentation.list.event.ListResult
import com.example.app.features.home.presentation.list.event.ListResult.LoadPetListResult.Error
import com.example.app.features.home.presentation.list.event.ListResult.LoadPetListResult.InProgress
import com.example.app.features.home.presentation.list.event.ListResult.LoadPetListResult.Success
import com.example.app.features.home.presentation.list.event.ListUiState
import com.example.app.features.home.presentation.list.event.ListUiState.DefaultUiState
import com.example.app.features.home.presentation.list.event.ListUiState.ErrorUiState
import com.example.app.features.home.presentation.list.event.ListUiState.LoadingUiState
import com.example.app.features.home.presentation.list.event.ListUiState.ShowContentUiState
import com.example.mvi.MviReducer
import com.example.mvi.exception.UnsupportedReduceException

class ListReducer : MviReducer<ListUiState, ListResult> {
    override fun ListUiState.reduceWith(result: ListResult): ListUiState {
        return when (val currentState = this) {
            is DefaultUiState -> currentState reduceWith result
            is LoadingUiState -> currentState reduceWith result
            is ShowContentUiState -> currentState reduceWith result
            is ErrorUiState -> currentState reduceWith result
        }
    }

    internal infix fun DefaultUiState.reduceWith(result: ListResult): ListUiState {
        return when (result) {
            InProgress -> LoadingUiState
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    internal infix fun LoadingUiState.reduceWith(result: ListResult): ListUiState {
        return when (result) {
            is Error -> ErrorUiState(result.error)
            is Success -> ShowContentUiState(result.pets)
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    internal infix fun ErrorUiState.reduceWith(result: ListResult): ListUiState {
        return when (result) {
            InProgress -> LoadingUiState
            else -> throw UnsupportedReduceException(this, result)
        }
    }

    internal infix fun ShowContentUiState.reduceWith(result: ListResult): ListUiState {
        return when (result) {
            InProgress -> LoadingUiState
            else -> throw UnsupportedReduceException(this, result)
        }
    }
}