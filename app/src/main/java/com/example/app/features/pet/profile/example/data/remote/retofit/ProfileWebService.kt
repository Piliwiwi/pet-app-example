package com.example.app.features.pet.profile.example.data.remote.retofit

import com.example.app.features.pet.profile.example.data.remote.model.RemoteProfile
import retrofit2.http.GET
import retrofit2.http.Path

interface ProfileWebService {
    @GET("api/frontend/pet/profile/{pet-id}")
    suspend fun getProfile(@Path("pet-id") petId: String): RemoteProfile
}
