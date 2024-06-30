package com.example.app.features.pet.profile.example.presentation.model

data class PetProfile(
    val petId: String,
    val name: String,
    val nickName: String,
    val gender: String,
    val breed: String?,
    val animal: String,
    val birthDate: String?,
    val age: String?,
    val profileImage: String,
    val description: String,
)
