package com.example.featureflags

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfig

// You will need google-services.json
// Create your Firebase project to get that file
object FirebaseRemoteConfig {
    private const val PROD_INTERVAL = 1200L

    fun initRemoteConfig(
        defaults: Map<String, Any>,
        isDebug: Boolean = true,
        fetchInterval: Long = 60L
    ) {
        Firebase.remoteConfig.setDefaultsAsync(defaults)
        fetchConfig(isDebug, fetchInterval)
    }

    private fun fetchConfig(isDebug: Boolean, fetchInterval: Long) {
        val settings = FirebaseRemoteConfigSettings.Builder()
        settings.minimumFetchIntervalInSeconds = if (isDebug) fetchInterval else PROD_INTERVAL
        Firebase.remoteConfig.setConfigSettingsAsync(settings.build())
        Firebase.remoteConfig.fetchAndActivate()
    }
}