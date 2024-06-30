package com.example.app.features.home.data.remote

import com.example.app.features.home.data.remote.retrofit.HomeApi
import com.example.app.features.home.factory.Factory.makeRemotePetCardResponseList
import com.example.uicomponents.data.PetCardResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeRemoteImplTest {
    private val api = mockk<HomeApi>()
    private val remote = HomeRemoteImpl(api)

    @Test
    fun `when getPetList, then return PetCardResponse List`() = runBlocking {
        val response = makeRemotePetCardResponseList(3)

        stubApi(response)

        val result = remote.getPetList()

        assertEquals(response, result)
    }

    private fun stubApi(response: List<PetCardResponse>) {
        coEvery { api.getPetList() } returns response
    }
}
