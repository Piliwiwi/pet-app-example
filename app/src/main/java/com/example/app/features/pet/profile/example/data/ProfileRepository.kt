package com.example.app.features.pet.profile.example.data

import com.example.app.features.pet.profile.example.data.source.ProfileRemote
import kotlinx.coroutines.flow.flow

class ProfileRepository(private val remote: ProfileRemote) {

    fun getProfile(petId: String) = flow {
        val response = remote.getProfile(petId)
        emit(response)
    }
}
