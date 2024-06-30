package com.example.app.features.pet.add.presentation.add.event

import com.example.app.features.pet.add.presentation.add.model.SelectorData
import com.example.mvi.events.MviResult


sealed class AddResult: MviResult {
    sealed class LoadSpeciesListResult: AddResult() {
        object InProgress: LoadSpeciesListResult()
        object NetworkError: LoadSpeciesListResult()
        data class Success(val data: List<SelectorData>): LoadSpeciesListResult()
    }

    sealed class LoadBreedListResult: AddResult() {
        object InProgress: LoadBreedListResult()
        object NetworkError: LoadBreedListResult()
        data class Success(val data: List<SelectorData>): LoadBreedListResult()
    }

    sealed class AddPetResult: AddResult() {
        object InProgress: AddPetResult()
        data class NetworkError(val error: com.example.network.utils.NetworkError): AddPetResult()
        object Success: AddPetResult()
    }
}