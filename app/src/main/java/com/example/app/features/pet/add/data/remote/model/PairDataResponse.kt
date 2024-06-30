package com.example.app.features.pet.add.data.remote.model

import com.google.gson.annotations.SerializedName

data class PairDataResponse(
    @SerializedName("key") var key: String? = null,
    @SerializedName("name") var name: String? = null,
)
