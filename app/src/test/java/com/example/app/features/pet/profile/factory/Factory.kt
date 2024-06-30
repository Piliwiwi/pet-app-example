package com.example.app.features.pet.profile.factory

import com.example.app.features.pet.profile.example.data.remote.model.RemoteProfile
import com.example.app.features.pet.profile.example.presentation.model.PetProfile
import com.example.utils.testingtools.RandomFactory

object Factory {
    fun makeRemoteProfile() = RemoteProfile(
        name = RandomFactory.generateString(),
        petId = RandomFactory.generateString(),
        nickName = RandomFactory.generateString(),
        birthDate = RandomFactory.generateString(),
        gender = RandomFactory.generateString(),
        breed = RandomFactory.generateString(),
        animal = RandomFactory.generateString(),
        age = RandomFactory.generateString(),
        profileImage = RandomFactory.generateString(),
        description = RandomFactory.generateString(),
    )

    fun makeProfile() = PetProfile(
        name = RandomFactory.generateString(),
        petId = RandomFactory.generateString(),
        nickName = RandomFactory.generateString(),
        birthDate = RandomFactory.generateString(),
        gender = RandomFactory.generateString(),
        breed = RandomFactory.generateString(),
        animal = RandomFactory.generateString(),
        age = RandomFactory.generateString(),
        profileImage = RandomFactory.generateString(),
        description = RandomFactory.generateString(),
    )
}