package com.example.app.features.pet.add.data.remote.model

import com.google.gson.annotations.SerializedName

data class PetRegisterRequest(
    @SerializedName("name") var name: String? = "",
    @SerializedName("breedCode") var breedCode: String? = null,
    @SerializedName("animalType") var animalType: String? = "",
    @SerializedName("genre") var genre: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("birthDateMillis") var birthDateMillis: Long? = null,
    @SerializedName("nickName") var nickName: String? = null
)