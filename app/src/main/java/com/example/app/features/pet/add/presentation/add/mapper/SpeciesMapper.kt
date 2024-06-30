package com.example.app.features.pet.add.presentation.add.mapper

import com.example.app.features.pet.add.data.remote.model.PairDataResponse
import com.example.app.features.pet.add.presentation.add.model.SelectorData

class SpeciesMapper {
    fun List<PairDataResponse>.toPresentation() = map {
        it.toPresentation()
    }

    private fun PairDataResponse.toPresentation() = SelectorData(
        name = name.orEmpty(),
        code = key.orEmpty()
    )
}