package com.example.app.features.pet.profile.example.data.remote.model

import com.example.app.features.pet.profile.example.data.remote.model.Constants.ID
import com.example.app.features.pet.profile.example.data.remote.model.Constants.LOCATION
import com.example.app.features.pet.profile.example.data.remote.model.Constants.NAME
import com.example.app.features.pet.profile.example.data.remote.model.Constants.PROFILE_IMAGE
import com.google.gson.annotations.SerializedName

data class RemoteOwnerResponse(
    @SerializedName(NAME) val ownerName: String?,
    @SerializedName(PROFILE_IMAGE) val ownerProfileImage: String?,
    @SerializedName(LOCATION) val region: String?,
    @SerializedName(ID) val ownerId: String?
)
