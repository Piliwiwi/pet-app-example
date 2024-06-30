package com.example.app.features.pet.profile.example.data.remote

import com.example.app.features.pet.profile.example.data.remote.model.RemoteProfile
import com.example.app.features.pet.profile.example.data.remote.retofit.ProfileWebService
import com.example.app.features.pet.profile.example.data.source.ProfileRemote

class ProfileRemoteImpl(private val api: ProfileWebService) :
    ProfileRemote {
    override suspend fun getProfile(petId: String): RemoteProfile = api.getProfile(petId)
}
