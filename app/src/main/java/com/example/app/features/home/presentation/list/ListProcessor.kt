package com.example.app.features.home.presentation.list

import com.example.app.features.home.data.HomeDataRepository
import com.example.app.features.home.presentation.list.event.ListAction
import com.example.app.features.home.presentation.list.event.ListAction.LoadPetListAction
import com.example.app.features.home.presentation.list.event.ListResult
import com.example.app.features.home.presentation.list.event.ListResult.LoadPetListResult
import com.example.mvi.execution.ExecutionThread
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class ListProcessor(
    private val repository: HomeDataRepository,
    private val executionThread: ExecutionThread
) {
    fun actionProcessor(action: ListAction): Flow<ListResult> =
        when (action) {
            is LoadPetListAction -> loadPetListProcessor()
        }

    private fun loadPetListProcessor() = flow<ListResult> {
        repository.getPetList().collect {
            emit(LoadPetListResult.Success(it))
        }
    }.onStart {
        emit(LoadPetListResult.InProgress)
    }.catch {
        emit(LoadPetListResult.Error(it))
    }.flowOn(executionThread.ioThread())
}