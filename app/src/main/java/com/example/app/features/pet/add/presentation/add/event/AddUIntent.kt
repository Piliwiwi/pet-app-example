package com.example.app.features.pet.add.presentation.add.event

import com.example.app.features.pet.add.presentation.add.model.PetRegister
import com.example.mvi.events.MviUserIntent
import java.io.File

sealed class AddUIntent : MviUserIntent {
    object InitialUIntent : AddUIntent()
    data class BreedInitialUIntent(val specieCode: String) : AddUIntent()
    data class RegisterPetUIntent(val data: PetRegister, val photo: File?) : AddUIntent()
}