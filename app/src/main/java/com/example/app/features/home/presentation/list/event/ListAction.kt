package com.example.app.features.home.presentation.list.event

import com.example.mvi.events.MviAction

sealed class ListAction : MviAction {
    object LoadPetListAction : ListAction()
}