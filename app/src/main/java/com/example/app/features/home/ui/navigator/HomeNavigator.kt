package com.example.app.features.home.ui.navigator

import android.content.Context
import com.example.app.features.pet.profile.PetProfileActivity

class HomeNavigator {

    fun goToPetPrivateProfile(context: Context?, petId: String) = context?.apply {
        val intent = PetProfileActivity.makeIntent(this, petId)
        startActivity(intent)
    }
}