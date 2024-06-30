package com.example.app.features.home.data.remote.retrofit

import com.example.uicomponents.data.PetCardResponse
import retrofit2.http.GET


interface HomeApi {
    @GET("api/frontend/pets")
    suspend fun getPetList(): List<PetCardResponse>
}