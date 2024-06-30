package com.example.network.event

import kotlinx.coroutines.flow.MutableStateFlow

object ExpiredTokenEvent {
    val isForbiddenResponse = MutableStateFlow(false)
}