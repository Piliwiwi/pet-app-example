package com.example.app.features.pet.add.data.remote

import com.example.app.features.pet.add.data.remote.model.PairDataResponse
import com.example.app.features.pet.add.data.remote.model.PetRegisterRequest
import com.example.app.features.pet.add.data.remote.retrofit.PetApi
import com.example.app.features.pet.add.data.source.AddRemote
import com.google.gson.Gson
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

class AddRemoteImpl(private val api: PetApi) : AddRemote {
    override suspend fun getSpecies(): List<PairDataResponse> = api.getSpeciesList()
    override suspend fun getBreeds(animalType: String): List<PairDataResponse> =
        api.getBreedList(animalType)

    override suspend fun registerPet(
        pet: PetRegisterRequest,
        profilePhotoFile: File?
    ) {
        var photo: MultipartBody.Part? = null
        profilePhotoFile?.let {
            val requestFile = RequestBody.create("image/*".toMediaTypeOrNull(), profilePhotoFile)
            photo = MultipartBody.Part.createFormData("profilePhotoFile", it.name, requestFile)
        }
        api.registerPet(
            body = Gson().toJson(pet)
                .toRequestBody("application/json".toMediaTypeOrNull()),
            profilePhotoFile = photo
        )
    }
}