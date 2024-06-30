package com.example.mvi.execution

import com.example.mvi.execution.ExecutionThreadEnvironment.APPLICATION
import com.example.mvi.execution.ExecutionThreadEnvironment.TESTING

object ExecutionThreadFactory {
    fun makeExecutionThread(environment: ExecutionThreadEnvironment): ExecutionThread =
        when (environment) {
            APPLICATION -> AppExecutionThread()
            TESTING -> AppExecutionThread()
        }
}