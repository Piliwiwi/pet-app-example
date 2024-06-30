package com.example.featureflags

import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.ktx.remoteConfig

object FeatureFlags {

    enum class BooleanValues(val key: String, val defaultValue: Boolean) {
        EXAMPLE_BOOLEAN("ff_boolean_example", false)
    }

    enum class StringValues(val key: String, val defaultValue: String) {
        EXAMPLE_STRING("ff_string_example", "Default String")
    }

    fun getDefaults(): Map<String, Any> {
        val booleanMap = BooleanValues.values().map {
            Pair(it.key, it.defaultValue)
        }.toMap()

        val stringMap = StringValues.values().map {
            Pair(it.key, it.defaultValue)
        }.toMap()

        return booleanMap + stringMap
    }

    fun getFlag(config: BooleanValues) = Firebase.remoteConfig.getBoolean(config.key)
    fun getFlag(config: StringValues) = Firebase.remoteConfig.getString(config.key)
}