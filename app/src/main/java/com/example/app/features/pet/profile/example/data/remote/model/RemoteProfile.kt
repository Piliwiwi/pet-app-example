package com.example.app.features.pet.profile.example.data.remote.model

import com.example.app.features.pet.profile.example.data.remote.model.Constants.AGE
import com.example.app.features.pet.profile.example.data.remote.model.Constants.ANIMAL
import com.example.app.features.pet.profile.example.data.remote.model.Constants.BIRTH_DATE
import com.example.app.features.pet.profile.example.data.remote.model.Constants.BREED
import com.example.app.features.pet.profile.example.data.remote.model.Constants.DESCRIPTION
import com.example.app.features.pet.profile.example.data.remote.model.Constants.GENDER
import com.example.app.features.pet.profile.example.data.remote.model.Constants.NAME
import com.example.app.features.pet.profile.example.data.remote.model.Constants.NICK_NAME
import com.google.gson.annotations.SerializedName

data class RemoteProfile(
    @SerializedName(NAME) val name: String?,
    @SerializedName("id") val petId: String?,
    @SerializedName(NICK_NAME) val nickName: String?,
    @SerializedName(BIRTH_DATE) val birthDate: String?,
    @SerializedName(GENDER) val gender: String?,
    @SerializedName(BREED) val breed: String?,
    @SerializedName(ANIMAL) val animal: String?,
    @SerializedName(AGE) val age: String?,
    @SerializedName("profileImage") val profileImage: String?,
    @SerializedName(DESCRIPTION) val description: String?,
)
