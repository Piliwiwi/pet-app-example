package com.example.app.features.pet.profile.example.data.source

import com.example.app.features.pet.profile.example.data.remote.model.RemoteProfile

interface ProfileRemote {
    suspend fun getProfile(petId: String): RemoteProfile
}
