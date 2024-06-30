package com.example.app.features.pet.add.presentation.add.event

import com.example.mvi.events.MviEffect
import com.example.network.utils.NetworkError


sealed class AddUiEffect: MviEffect {
    object NavToSuccessUiEffect: AddUiEffect()
    data class ShowRegistrationErrorUiEffect(val error: NetworkError): AddUiEffect()
}
