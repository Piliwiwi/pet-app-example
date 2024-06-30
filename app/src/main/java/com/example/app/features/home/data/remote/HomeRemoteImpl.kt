package com.example.app.features.home.data.remote

import com.example.app.features.home.data.remote.retrofit.HomeApi
import com.example.app.features.home.data.source.HomeRemote
import com.example.uicomponents.data.PetCardResponse


class HomeRemoteImpl(private val api: HomeApi): HomeRemote {
    override suspend fun getPetList(): List<PetCardResponse> = api.getPetList()
}