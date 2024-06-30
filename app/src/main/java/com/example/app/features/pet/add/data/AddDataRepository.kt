package com.example.app.features.pet.add.data

import com.example.app.features.pet.add.data.remote.model.PetRegisterRequest
import com.example.app.features.pet.add.data.source.AddRemote
import java.io.File
import kotlinx.coroutines.flow.flow


class AddDataRepository(private val remote: AddRemote) {

    fun getSpecies() = flow {
        val species = remote.getSpecies()
        emit(species)
    }

    fun getBreeds(animalType: String) = flow {
        val breeds = remote.getBreeds(animalType)
        emit(breeds)
    }

    fun addPet(pet: PetRegisterRequest, profilePhotoFile: File?) = flow {
        val response = remote.registerPet(pet, profilePhotoFile)
        emit(response)
    }
}