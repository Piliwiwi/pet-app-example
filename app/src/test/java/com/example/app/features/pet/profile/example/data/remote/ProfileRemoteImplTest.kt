package com.example.app.features.pet.profile.example.data.remote

import com.example.app.features.pet.profile.example.data.remote.model.RemoteProfile
import com.example.app.features.pet.profile.example.data.remote.retofit.ProfileWebService
import com.example.app.features.pet.profile.factory.Factory.makeRemoteProfile
import com.example.utils.testingtools.RandomFactory
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ProfileRemoteImplTest {
    private val api = mockk<ProfileWebService>()
    private val remote = ProfileRemoteImpl(api)

    @Test
    fun `given petId, when getProfile, then RemoteProfile`() = runBlocking {
        val petId = RandomFactory.generateString()
        val remoteProfile = makeRemoteProfile()

        stubApi(petId, remoteProfile)

        val result = remote.getProfile(petId)

        assertEquals(remoteProfile, result)
    }

    private fun stubApi(petId: String, response: RemoteProfile) {
        coEvery { api.getProfile(petId) } returns response
    }
}