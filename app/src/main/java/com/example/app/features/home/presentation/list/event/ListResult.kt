package com.example.app.features.home.presentation.list.event

import com.example.mvi.events.MviResult
import com.example.uicomponents.data.PetCardResponse

sealed class ListResult : MviResult {
    sealed class LoadPetListResult : ListResult() {
        object InProgress : LoadPetListResult()
        data class Error(val error: Throwable) : LoadPetListResult()
        data class Success(val pets: List<PetCardResponse>) : LoadPetListResult()
    }
}