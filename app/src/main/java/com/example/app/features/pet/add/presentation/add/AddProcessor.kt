package com.example.app.features.pet.add.presentation.add

import com.example.app.common.config.Messages
import com.example.app.features.pet.add.data.AddDataRepository
import com.example.app.features.pet.add.presentation.add.event.AddAction
import com.example.app.features.pet.add.presentation.add.event.AddAction.AddPetAction
import com.example.app.features.pet.add.presentation.add.event.AddAction.LoadBreedListAction
import com.example.app.features.pet.add.presentation.add.event.AddAction.LoadSpeciesListAction
import com.example.app.features.pet.add.presentation.add.event.AddResult
import com.example.app.features.pet.add.presentation.add.event.AddResult.AddPetResult
import com.example.app.features.pet.add.presentation.add.event.AddResult.LoadBreedListResult
import com.example.app.features.pet.add.presentation.add.event.AddResult.LoadSpeciesListResult
import com.example.app.features.pet.add.presentation.add.mapper.PetRegisterMapper
import com.example.app.features.pet.add.presentation.add.mapper.SpeciesMapper
import com.example.app.features.pet.add.presentation.add.model.PetRegister
import com.example.mvi.execution.ExecutionThread
import com.example.network.utils.NetworkErrorHandler
import java.io.File
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class AddProcessor(
    private val repository: AddDataRepository,
    private val mapper: PetRegisterMapper,
    private val specieMapper: SpeciesMapper,
    private val errorHandler: NetworkErrorHandler,
    private val executionThread: ExecutionThread
) {
    fun actionProcessor(action: AddAction): Flow<AddResult> =
        when (action) {
            is LoadSpeciesListAction -> loadSpeciesProcessor()
            is LoadBreedListAction -> loadBreedsProcessor(action.specieCode)
            is AddPetAction -> registerPetProcessor(action.data, action.photo)
        }

    private fun loadSpeciesProcessor() = flow<LoadSpeciesListResult> {
        repository.getSpecies().collect {
            val specie = with(specieMapper) { it.toPresentation() }
            emit(LoadSpeciesListResult.Success(specie))
        }
    }.onStart {
        emit(LoadSpeciesListResult.InProgress)
    }.catch {
        emit(LoadSpeciesListResult.NetworkError)
    }.flowOn(executionThread.ioThread())

    private fun loadBreedsProcessor(specieCode: String) = flow<LoadBreedListResult> {
        repository.getBreeds(specieCode).collect {
            val specie = with(specieMapper) { it.toPresentation() }
            emit(LoadBreedListResult.Success(specie))
        }
    }.onStart {
        emit(LoadBreedListResult.InProgress)
    }.catch {
        emit(LoadBreedListResult.NetworkError)
    }.flowOn(executionThread.ioThread())

    private fun registerPetProcessor(pet: PetRegister, photo: File?) = flow<AddPetResult> {
        val request = with(mapper) { pet.toRemote() }
        repository.addPet(request, photo).collect {
            emit(AddPetResult.Success)
        }
    }.onStart {
        emit(AddPetResult.InProgress)
    }.catch {cause ->
        emit(AddPetResult.NetworkError(with(errorHandler) { cause.toNetworkError(Messages.GENERIC_NETWORK_ERROR_MESSAGE) }))
    }.flowOn(executionThread.ioThread())
}