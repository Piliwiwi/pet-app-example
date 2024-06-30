package com.example.app.features.pet.profile.example.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.features.pet.profile.example.data.ProfileRepository
import com.example.app.features.pet.profile.example.presentation.events.ProfileUiStates
import com.example.app.features.pet.profile.example.presentation.events.ProfileUiStates.DefaultUiState
import com.example.app.features.pet.profile.example.presentation.events.ProfileUiStates.DisplayProfileUiState
import com.example.app.features.pet.profile.example.presentation.events.ProfileUiStates.ErrorUiState
import com.example.app.features.pet.profile.example.presentation.events.ProfileUiStates.LoadingUiState
import com.example.app.features.pet.profile.example.presentation.events.ProfileUserIntents
import com.example.app.features.pet.profile.example.presentation.events.ProfileUserIntents.InitialUserIntent
import com.example.app.features.pet.profile.example.presentation.mapper.ProfileMapper
import com.example.mvi.MviPresentation
import com.example.mvi.events.MviEvent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val repository: ProfileRepository,
    private val mapper: ProfileMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModel(), MviPresentation<ProfileUserIntents, ProfileUiStates> {
    private val defaultUiState: ProfileUiStates = DefaultUiState
    private val uiState: MutableStateFlow<ProfileUiStates> =
        MutableStateFlow(defaultUiState)
    private var job: Job? = null

    fun processUserIntents(
        userIntents: Flow<ProfileUserIntents>,
        scope: CoroutineScope = viewModelScope
    ) {
        userIntents.buffer()
            .flatMapMerge { userIntent ->
                userIntent.handleUserIntent()
            }.processEvent(scope)
    }

    private fun ProfileUserIntents.handleUserIntent(): Flow<MviEvent> =
        when (this) {
            is InitialUserIntent -> getProfile(petId)
        }

    private fun Flow<MviEvent>.processEvent(scope: CoroutineScope) {
        job?.cancel()
        job = scope.launch {
            this@processEvent.cancellable().collect { event ->
                coroutineContext.ensureActive()
                when (event) {
                    is ProfileUiStates -> uiState.value = event
                }
            }
        }
    }

    private fun getProfile(petId: String) = flow<MviEvent> {
        repository.getProfile(petId).collect {
            val profile = with(mapper) {
                it.toPresentation()
            }
            emit(DisplayProfileUiState(profile))
        }
    }.onStart {
        emit(LoadingUiState)
    }.catch { error ->
        emit(ErrorUiState(error))
    }.flowOn(dispatcher)

    override fun uiStates(): StateFlow<ProfileUiStates> = uiState.asStateFlow()
}
