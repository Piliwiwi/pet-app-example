package com.example.app.features.pet.add.ui.navitgation

import android.view.View
import com.example.app.common.utils.safeNavigation
import com.example.app.features.pet.add.ui.add.AddFragmentDirections
import com.example.app.features.pet.add.ui.add.PetDescriptionFragmentDirections

class PetNavigator {

    fun goToAddPetDescription(
        view: View?,
        petName: String,
        nickName: String?,
        photoUri: String?,
        specie: String,
        specieName: String,
        gender: String?,
        birthDate: Long
    ) = view?.apply {
        val direction = AddFragmentDirections.fromAddToDescription(
            petName = petName,
            photoUri = photoUri,
            specie = specie,
            gender = gender,
            birthDate = birthDate,
            nickName = nickName,
            specieName = specieName
        )
        safeNavigation(this, direction)
    }

    fun goToSuccess(view: View?) = view?.apply {
        safeNavigation(this, PetDescriptionFragmentDirections.fromAddToSuccess())
    }
}