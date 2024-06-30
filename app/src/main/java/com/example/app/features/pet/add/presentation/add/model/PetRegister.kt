package com.example.app.features.pet.add.presentation.add.model


data class PetRegister(
    var name: String? = null,
    var breedCode: String? = null,
    var specie: AnimalPair? = null,
    var birthDate: DatePair? = null,
    var genre: String? = null,
    var description: String? = null,
    var photo: String? = null,
    var nickName: String? = null
)

data class AnimalPair(
    var name: String? = null,
    var code: String? = null
)

data class DatePair(
    var name: String? = null,
    var millis: Long? = null
)