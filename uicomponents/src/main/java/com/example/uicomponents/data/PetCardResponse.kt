package com.example.uicomponents.data

import com.example.uicomponents.Component
import com.google.gson.annotations.SerializedName

data class PetCardResponse(
    @SerializedName("id") val id: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("breed") val breed: BreedTypeResponse? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("genre") val genre: String? = null,
    @SerializedName("profilePhoto") val image: String? = null,
    @SerializedName("age") val age: String? = null,
    @SerializedName("animal") val animal: AnimalTypeResponse? = null
) : Component

data class AnimalTypeResponse(
    @SerializedName("name") val name: String? = null,
    @SerializedName("type") val type: String? = null,
)

data class BreedTypeResponse(
    @SerializedName("name") val name: String? = null,
    @SerializedName("code") val code: String? = null,
    @SerializedName("description") val description: String? = null,
    @SerializedName("refImage") val refImage: String? = null,
)