package com.example.app.features.pet.add.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.app.features.pet.add.presentation.add.AddProcessor
import com.example.app.features.pet.add.presentation.add.AddReducer
import com.example.app.features.pet.add.presentation.add.event.AddAction
import com.example.app.features.pet.add.presentation.add.event.AddResult
import com.example.app.features.pet.add.presentation.add.event.AddUIntent
import com.example.app.features.pet.add.presentation.add.event.AddUiEffect
import com.example.app.features.pet.add.presentation.add.event.AddUiState
import com.example.app.features.pet.add.presentation.add.model.PetRegister
import com.example.mvi.MviPresentation
import com.example.mvi.MviPresentationEffect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.scan


class AddViewModel(
    private val processor: AddProcessor,
    private val reducer: AddReducer
) : ViewModel(),
    MviPresentation<AddUIntent, AddUiState>,
    MviPresentationEffect<AddUiEffect> {
    private val defaultUiState: AddUiState = AddUiState.DefaultUiState
    private val uiState: MutableStateFlow<AddUiState> = MutableStateFlow(defaultUiState)
    private val uiEffect: MutableSharedFlow<AddUiEffect> = MutableSharedFlow()
    val petData = PetRegister()

    fun processUserIntents(
        userIntents: Flow<AddUIntent>,
        scope: CoroutineScope = viewModelScope
    ) {
        userIntents
            .buffer()
            .flatMapMerge { userIntent ->
                processor.actionProcessor(userIntent.toAction())
            }
            .handleEffect()
            .scan(defaultUiState) { currentState, result ->
                with(reducer) { currentState reduceWith result }
            }
            .onEach { uiState.value = it }
            .launchIn(scope)
    }

    private fun AddUIntent.toAction() =
        when (this) {
            is AddUIntent.InitialUIntent -> AddAction.LoadSpeciesListAction
            is AddUIntent.RegisterPetUIntent -> AddAction.AddPetAction(data, photo)
            is AddUIntent.BreedInitialUIntent -> AddAction.LoadBreedListAction(specieCode)
        }

    private fun Flow<AddResult>.handleEffect(): Flow<AddResult> =
        onEach { change ->
            val event = when (change) {
                is AddResult.AddPetResult.Success -> AddUiEffect.NavToSuccessUiEffect
                is AddResult.AddPetResult.NetworkError -> AddUiEffect.ShowRegistrationErrorUiEffect(change.error)
                else -> return@onEach
            }
            uiEffect.emit(event)
        }

    override fun uiStates() = uiState
    override fun uiEffect() = uiEffect
}