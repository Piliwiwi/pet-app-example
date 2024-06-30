package com.example.app.features.pet.profile.example.presentation

import com.example.app.features.pet.profile.example.data.ProfileRepository
import com.example.app.features.pet.profile.example.data.remote.model.RemoteProfile
import com.example.app.features.pet.profile.example.presentation.events.ProfileUiStates.DisplayProfileUiState
import com.example.app.features.pet.profile.example.presentation.events.ProfileUserIntents.InitialUserIntent
import com.example.app.features.pet.profile.example.presentation.mapper.ProfileMapper
import com.example.app.features.pet.profile.example.presentation.model.PetProfile
import com.example.app.features.pet.profile.factory.Factory.makeProfile
import com.example.app.features.pet.profile.factory.Factory.makeRemoteProfile
import com.example.utils.testingtools.RandomFactory
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ProfileViewModelTest {
    private val repository = mockk<ProfileRepository>()
    private val testDispatcher = StandardTestDispatcher()
    private val mapper = mockk<ProfileMapper>()

    private var viewModel = ProfileViewModel(repository, mapper, testDispatcher)
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ProfileViewModel(repository, mapper, testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given InitialUIntent, when processUserIntents, then emit DisplayProfileUiState`() =
        testScope.runTest {
            val petId = RandomFactory.generateString()
            val intent = InitialUserIntent(petId)
            val remoteProfile = makeRemoteProfile()
            val profile = makeProfile()

            stubRepository(petId, remoteProfile)
            stubMapper(remoteProfile, profile)

            viewModel.processUserIntents(userIntents = flow { emit(intent) })

            val result = viewModel.uiStates().take(2).toList().last()

            assertTrue(result is DisplayProfileUiState)
        }

    private fun stubMapper(remoteProfile: RemoteProfile, profile: PetProfile) {
        every { with(mapper) { remoteProfile.toPresentation() } } returns profile
    }

    private fun stubRepository(petId: String, remoteProfile: RemoteProfile) {
        coEvery { repository.getProfile(petId) } returns flow { emit(remoteProfile) }
    }
}