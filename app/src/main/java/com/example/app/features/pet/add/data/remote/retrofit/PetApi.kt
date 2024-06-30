package com.example.app.features.pet.add.data.remote.retrofit

import com.example.app.features.pet.add.data.remote.model.PairDataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface PetApi {

    @GET("api/frontend/animals/all")
    suspend fun getSpeciesList(): List<PairDataResponse>

    @GET("api/frontend/breeds/{animal_type}")
    suspend fun getBreedList(@Path("animal_type") animalType: String): List<PairDataResponse>

    @Multipart
    @POST("api/frontend/pet/add")
    suspend fun registerPet(
        @Part("body") body: RequestBody,
        @Part profilePhotoFile: MultipartBody.Part?
    ): Unit
}