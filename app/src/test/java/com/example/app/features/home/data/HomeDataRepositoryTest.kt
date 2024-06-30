package com.example.app.features.home.data

import com.example.app.features.home.data.remote.HomeRemoteImpl
import com.example.app.features.home.factory.Factory.makeRemotePetCardResponseList
import com.example.uicomponents.data.PetCardResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeDataRepositoryTest {
    private val remote = mockk<HomeRemoteImpl>()
    private val repository = HomeDataRepository(remote)

    @Test
    fun `when getPetList, then emit PetCardResponse List`() = runBlocking {
        val response = makeRemotePetCardResponseList(3)

        stubRemote(response)

        val result = repository.getPetList().take(2).first()

        assertEquals(response, result)
    }

    private fun stubRemote(response: List<PetCardResponse>) {
        coEvery { remote.getPetList() } returns response
    }
}