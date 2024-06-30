package com.example.app.features.pet.profile.example.presentation.events

import com.example.mvi.events.MviUserIntent

sealed class ProfileUserIntents : MviUserIntent {
    data class InitialUserIntent(val petId: String): ProfileUserIntents()
}
