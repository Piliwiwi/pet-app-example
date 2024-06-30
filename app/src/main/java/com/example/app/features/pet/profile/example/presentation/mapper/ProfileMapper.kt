package com.example.app.features.pet.profile.example.presentation.mapper

import com.example.app.features.pet.profile.example.data.remote.model.RemoteProfile
import com.example.app.features.pet.profile.example.presentation.model.PetProfile

class ProfileMapper {
    fun RemoteProfile.toPresentation() = PetProfile(
        name = name.orEmpty(),
        nickName = nickName.orEmpty(),
        gender = gender.orEmpty(),
        birthDate = birthDate,
        age = age,
        animal = animal.orEmpty(),
        description = description.orEmpty(),
        breed = breed,
        profileImage = profileImage.orEmpty(),
        petId = petId.orEmpty()
    )
}
