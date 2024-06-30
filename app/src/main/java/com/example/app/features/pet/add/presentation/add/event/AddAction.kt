package com.example.app.features.pet.add.presentation.add.event

import com.example.app.features.pet.add.presentation.add.model.PetRegister
import com.example.mvi.events.MviAction
import java.io.File

sealed class AddAction: MviAction {
    object LoadSpeciesListAction: AddAction()
    data class LoadBreedListAction(val specieCode: String): AddAction()
    data class AddPetAction(val data: PetRegister, val photo: File?) : AddAction()
}