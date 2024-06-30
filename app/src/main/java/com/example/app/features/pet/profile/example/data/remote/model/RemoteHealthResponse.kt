package com.example.app.features.pet.profile.example.data.remote.model

import com.example.app.features.pet.profile.example.data.remote.model.Constants.ICON
import com.example.app.features.pet.profile.example.data.remote.model.Constants.LABEL
import com.example.app.features.pet.profile.example.data.remote.model.Constants.URL
import com.google.gson.annotations.SerializedName

data class RemoteHealthResponse(
    @SerializedName(LABEL) val label: String?,
    @SerializedName(ICON) val icon: String?,
    @SerializedName(URL) val url: String?,
)
