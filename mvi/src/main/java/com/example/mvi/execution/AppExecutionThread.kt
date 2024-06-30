package com.example.mvi.execution

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class AppExecutionThread : ExecutionThread {
    override fun uiThread(): CoroutineDispatcher = Dispatchers.Main
    override fun ioThread(): CoroutineDispatcher = Dispatchers.IO
}