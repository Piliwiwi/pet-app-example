package com.example.app.common.factory

import android.content.Context
import com.example.app.BuildConfig
import com.example.network.config.NetworkDependencies

object NetworkDependenciesFactory {
    fun makeNetworkDependencies(context: Context?): NetworkDependencies =
        NetworkDependencies(
            environment = BuildConfig.FLAVOR,
            context = context
        )
}