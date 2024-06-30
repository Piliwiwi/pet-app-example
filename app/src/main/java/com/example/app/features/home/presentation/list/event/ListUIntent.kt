package com.example.app.features.home.presentation.list.event

import com.example.mvi.events.MviUserIntent

sealed class ListUIntent : MviUserIntent {
    object InitialUIntent : ListUIntent()
    object ReloadUIntent : ListUIntent()
}