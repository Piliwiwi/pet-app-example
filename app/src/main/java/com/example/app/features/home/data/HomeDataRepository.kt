package com.example.app.features.home.data

import com.example.app.features.home.data.source.HomeRemote
import kotlinx.coroutines.flow.flow

class HomeDataRepository(private val remote: HomeRemote) {
    fun getPetList() = flow {
        val list = remote.getPetList()
        emit(list)
    }
}