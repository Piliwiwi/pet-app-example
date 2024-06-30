package com.example.app.features.home.presentation.list

import com.example.app.features.home.factory.Factory.makeRemotePetCardResponseList
import com.example.app.features.home.presentation.list.event.ListResult.LoadPetListResult.Error
import com.example.app.features.home.presentation.list.event.ListResult.LoadPetListResult.InProgress
import com.example.app.features.home.presentation.list.event.ListResult.LoadPetListResult.Success
import com.example.app.features.home.presentation.list.event.ListUiState.DefaultUiState
import com.example.app.features.home.presentation.list.event.ListUiState.ErrorUiState
import com.example.app.features.home.presentation.list.event.ListUiState.LoadingUiState
import com.example.app.features.home.presentation.list.event.ListUiState.ShowContentUiState
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertTrue
import org.junit.Test

class ListReducerTest {
    private val reducer = ListReducer()

    @Test
    fun `given DefaultUiState, when reduceWith InProgress-Result, then emit LoadingUiState`() =
        runBlocking {
            val initialState = DefaultUiState
            val result = InProgress

            val newState = reducer.run { initialState reduceWith result }

            assertTrue(newState is LoadingUiState)
        }

    @Test
    fun `given LoadingUiState, when reduceWith Error-Result, then emit ErrorUiState`() =
        runBlocking {
            val initialState = LoadingUiState
            val error = Error(Throwable())

            val newState = reducer.run { initialState reduceWith error }

            assertTrue(newState is ErrorUiState)
        }

    @Test
    fun `given LoadingUiState, when reduceWith Success-Result, then emit ShowContentUiState`() =
        runBlocking {
            val list = makeRemotePetCardResponseList(3)
            val initialState = LoadingUiState
            val result = Success(list)

            val newState = reducer.run { initialState reduceWith result }

            assertTrue(newState is ShowContentUiState)
        }

    @Test
    fun `given ErrorUiState, when reduceWith InProgress-Result, then emit LoadingUiState`() =
        runBlocking {
            val initialState = ErrorUiState(Throwable())
            val result = InProgress

            val newState = reducer.run { initialState reduceWith result }

            assertTrue(newState is LoadingUiState)
        }

    @Test
    fun `given ShowContentUiState, when reduceWith InProgress-Result, then emit LoadingUiState`() =
        runBlocking {
            val list = makeRemotePetCardResponseList(3)
            val initialState = ShowContentUiState(list)
            val result = InProgress

            val newState = reducer.run { initialState reduceWith result }

            assertTrue(newState is LoadingUiState)
        }
}