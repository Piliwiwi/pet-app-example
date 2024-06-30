package com.example.app.features.pet.profile.example.presentation.events

import com.example.app.features.pet.profile.example.presentation.model.PetProfile
import com.example.mvi.events.MviUiState

sealed class ProfileUiStates : MviUiState {
    object DefaultUiState : ProfileUiStates()
    object LoadingUiState : ProfileUiStates()
    data class ErrorUiState(val error: Throwable) : ProfileUiStates()
    data class DisplayProfileUiState(val profile: PetProfile) : ProfileUiStates()
}
