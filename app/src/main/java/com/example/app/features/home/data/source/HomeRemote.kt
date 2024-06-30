package com.example.app.features.home.data.source

import com.example.uicomponents.data.PetCardResponse

interface HomeRemote {
    suspend fun getPetList(): List<PetCardResponse>
}