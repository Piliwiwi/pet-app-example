package com.example.app.features.pet.add.data.source

import com.example.app.features.pet.add.data.remote.model.PairDataResponse
import com.example.app.features.pet.add.data.remote.model.PetRegisterRequest
import java.io.File

interface AddRemote {
    suspend fun getSpecies(): List<PairDataResponse>
    suspend fun getBreeds(animalType: String): List<PairDataResponse>
    suspend fun registerPet(pet: PetRegisterRequest, profilePhotoFile: File?)
}