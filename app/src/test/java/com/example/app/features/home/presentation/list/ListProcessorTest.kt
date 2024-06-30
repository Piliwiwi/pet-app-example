package com.example.app.features.home.presentation.list

import com.example.app.features.home.data.HomeDataRepository
import com.example.app.features.home.factory.Factory.makeRemotePetCardResponseList
import com.example.app.features.home.presentation.list.event.ListAction
import com.example.app.features.home.presentation.list.event.ListResult.LoadPetListResult.Error
import com.example.app.features.home.presentation.list.event.ListResult.LoadPetListResult.InProgress
import com.example.app.features.home.presentation.list.event.ListResult.LoadPetListResult.Success
import com.example.mvi.execution.AppExecutionThread
import com.example.uicomponents.data.PetCardResponse
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class ListProcessorTest {
    private val repository = mockk<HomeDataRepository>()
    private val executionThread = AppExecutionThread()
    private val processor = ListProcessor(repository, executionThread)

    @Test
    fun `given LoadPetListAction, when actionProcessor, then emit result Success`() = runBlocking {
        val list = makeRemotePetCardResponseList(3)
        val action = ListAction.LoadPetListAction
        val result = Success(list)

        stubRepository(list)

        val actual = processor.actionProcessor(action).toList().take(2).last()

        assertEquals(result, actual)
    }

    @Test
    fun `given LoadPetListAction, when actionProcessor, then emit result InProgress`() =
        runBlocking {
            val list = makeRemotePetCardResponseList(3)
            val action = ListAction.LoadPetListAction
            val result = InProgress

            stubRepository(list)

            val actual = processor.actionProcessor(action).toList().take(2).first()

            assertEquals(result, actual)
        }

    @Test
    fun `given LoadPetListAction, when actionProcessor, then emit result Error`() =
        runBlocking {
            val action = ListAction.LoadPetListAction
            val throwable = Throwable()
            val result = Error(throwable)

            stubErrorRepository(throwable)

            val actual = processor.actionProcessor(action).toList().take(2).last()

            assertEquals(result, actual)
        }

    private fun stubRepository(list: List<PetCardResponse>) {
        coEvery { repository.getPetList() } returns flow { emit(list) }
    }

    private fun stubErrorRepository(error: Throwable) {
        coEvery { repository.getPetList() } throws error
    }
}
