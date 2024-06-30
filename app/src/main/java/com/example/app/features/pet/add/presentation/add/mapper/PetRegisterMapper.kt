package com.example.app.features.pet.add.presentation.add.mapper

import com.example.app.features.pet.add.data.remote.model.PetRegisterRequest
import com.example.app.features.pet.add.presentation.add.model.PetRegister

class PetRegisterMapper {
    fun PetRegister.toRemote() = PetRegisterRequest(
        name = name,
        breedCode = breedCode,
        animalType = specie?.code,
        genre = genre,
        description = description,
        birthDateMillis = birthDate?.millis,
        nickName = nickName
    )
}