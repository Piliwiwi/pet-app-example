package com.example.app.features.pet.profile.example.data

import com.example.app.features.pet.profile.example.data.remote.ProfileRemoteImpl
import com.example.app.features.pet.profile.example.data.remote.model.RemoteProfile
import com.example.app.features.pet.profile.factory.Factory.makeRemoteProfile
import com.example.utils.testingtools.RandomFactory
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ProfileRepositoryTest {
    private val remote = mockk<ProfileRemoteImpl>()
    private val repository = ProfileRepository(remote)

    @Test
    fun `given petId, when getProfile, then emit RemoteProfile`() = runBlocking {
        val petId = RandomFactory.generateString()
        val remoteProfile = makeRemoteProfile()

        stubRemote(petId, remoteProfile)

        val result = repository.getProfile(petId).take(2).first()

        assertEquals(remoteProfile, result)
    }

    private fun stubRemote(petId: String, response: RemoteProfile) =
        coEvery { remote.getProfile(petId) } returns response
}