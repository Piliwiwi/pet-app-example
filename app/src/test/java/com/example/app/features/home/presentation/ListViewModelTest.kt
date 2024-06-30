package com.example.app.features.home.presentation

import com.example.app.features.home.factory.Factory
import com.example.app.features.home.presentation.list.ListProcessor
import com.example.app.features.home.presentation.list.ListReducer
import com.example.app.features.home.presentation.list.event.ListAction
import com.example.app.features.home.presentation.list.event.ListAction.LoadPetListAction
import com.example.app.features.home.presentation.list.event.ListResult
import com.example.app.features.home.presentation.list.event.ListResult.LoadPetListResult.Error
import com.example.app.features.home.presentation.list.event.ListResult.LoadPetListResult.InProgress
import com.example.app.features.home.presentation.list.event.ListResult.LoadPetListResult.Success
import com.example.app.features.home.presentation.list.event.ListUIntent.InitialUIntent
import com.example.app.features.home.presentation.list.event.ListUIntent.ReloadUIntent
import com.example.app.features.home.presentation.list.event.ListUiState
import com.example.app.features.home.presentation.list.event.ListUiState.DefaultUiState
import com.example.app.features.home.presentation.list.event.ListUiState.ErrorUiState
import com.example.app.features.home.presentation.list.event.ListUiState.LoadingUiState
import com.example.app.features.home.presentation.list.event.ListUiState.ShowContentUiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
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


class ListViewModelTest {
    private val processor = mockk<ListProcessor>()
    private val reducer = mockk<ListReducer>()
    private val testDispatcher = StandardTestDispatcher()
    private var viewModel = ListViewModel(processor, reducer)
    private val testScope = TestScope(testDispatcher)

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        viewModel = ListViewModel(processor, reducer)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given InitialUIntent, when processUserIntents, then emit ShowContentUiState`() =
        testScope.runTest {
            val userIntent = InitialUIntent
            val action = LoadPetListAction
            val list = Factory.makeRemotePetCardResponseList(3)
            val result = Success(list)
            val previousState = DefaultUiState
            val newState = ShowContentUiState(list)

            stubProcessor(action, result)
            stubReducer(previousState, result, newState)

            viewModel.processUserIntents(flowOf(userIntent))

            val state = viewModel.uiStates().take(2).toList().last()

            assertTrue(state is ShowContentUiState)
        }

    @Test
    fun `given InitialUIntent, when processUserIntents, then emit LoadingUiState`() =
        testScope.runTest {
            val userIntent = InitialUIntent
            val action = LoadPetListAction
            val result = InProgress
            val previousState = DefaultUiState
            val newState = LoadingUiState

            stubProcessor(action, result)
            stubReducer(previousState, result, newState)

            viewModel.processUserIntents(flowOf(userIntent))

            val state = viewModel.uiStates().take(2).toList().last()

            assertTrue(state is LoadingUiState)
        }

    @Test
    fun `given InitialUIntent, when processUserIntents, then emit ErrorUiState`() =
        testScope.runTest {
            val userIntent = InitialUIntent
            val action = LoadPetListAction
            val result = Error(Throwable())
            val previousState = DefaultUiState
            val newState = ErrorUiState(result.error)

            stubProcessor(action, result)
            stubReducer(previousState, result, newState)

            viewModel.processUserIntents(flowOf(userIntent))

            val state = viewModel.uiStates().take(2).toList().last()

            assertTrue(state is ErrorUiState)
        }

    @Test
    fun `given ReloadUIntent, when processUserIntents, then emit ShowContentUiState`() =
        testScope.runTest {
            val userIntent = ReloadUIntent
            val action = LoadPetListAction
            val list = Factory.makeRemotePetCardResponseList(3)
            val result = Success(list)
            val previousState = DefaultUiState
            val newState = ShowContentUiState(list)

            stubProcessor(action, result)
            stubReducer(previousState, result, newState)

            viewModel.processUserIntents(flowOf(userIntent))

            val state = viewModel.uiStates().take(2).toList().last()

            assertTrue(state is ShowContentUiState)
        }


    @Test
    fun `given ReloadUIntent, when processUserIntents, then emit LoadingUiState`() =
        testScope.runTest {
            val userIntent = ReloadUIntent
            val action = LoadPetListAction
            val result = InProgress
            val previousState = DefaultUiState
            val newState = LoadingUiState

            stubProcessor(action, result)
            stubReducer(previousState, result, newState)

            viewModel.processUserIntents(flowOf(userIntent))

            val state = viewModel.uiStates().take(2).toList().last()

            assertTrue(state is LoadingUiState)
        }

    @Test
    fun `given ReloadUIntent, when processUserIntents, then emit ErrorUiState`() =
        testScope.runTest {
            val userIntent = ReloadUIntent
            val action = LoadPetListAction
            val result = Error(Throwable())
            val previousState = DefaultUiState
            val newState = ErrorUiState(result.error)

            stubProcessor(action, result)
            stubReducer(previousState, result, newState)

            viewModel.processUserIntents(flowOf(userIntent))

            val state = viewModel.uiStates().take(2).toList().last()

            assertTrue(state is ErrorUiState)
        }


    private fun stubProcessor(action: ListAction, result: ListResult) {
        coEvery { processor.actionProcessor(action) } returns flow { emit(result) }
    }

    private fun stubReducer(previousState: ListUiState, result: ListResult, newState: ListUiState) {
        coEvery { reducer.run { previousState reduceWith result } } returns newState
    }
}