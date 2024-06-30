package com.example.app.features.home.factory

import com.example.uicomponents.data.AnimalTypeResponse
import com.example.uicomponents.data.BreedTypeResponse
import com.example.uicomponents.data.PetCardResponse
import com.example.utils.testingtools.RandomFactory

object Factory {

    fun makeRemotePetCardResponseList(count: Int) = (0..count).map { makeRemotePetCardResponse() }
    fun makeRemotePetCardResponse() = PetCardResponse(
        id = RandomFactory.generateString(),
        name = RandomFactory.generateString(),
        breed = makeBreedTypeResponse(),
        description = RandomFactory.generateString(),
        genre = RandomFactory.generateString(),
        image = RandomFactory.generateString(),
        age = RandomFactory.generateString(),
        animal = makeAnimalTypeResponse(),
    )

    private fun makeBreedTypeResponse() = BreedTypeResponse(
        name = RandomFactory.generateString(),
        code = RandomFactory.generateString(),
        description = RandomFactory.generateString(),
        refImage = RandomFactory.generateString(),
    )

    private fun makeAnimalTypeResponse() = AnimalTypeResponse(
        name = RandomFactory.generateString(),
        type = RandomFactory.generateString(),
    )
}
