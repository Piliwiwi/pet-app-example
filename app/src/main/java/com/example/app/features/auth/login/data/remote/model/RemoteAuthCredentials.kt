package com.example.app.features.auth.login.data.remote.model

import com.example.app.features.auth.login.data.remote.config.Constants.TOKEN
import com.google.gson.annotations.SerializedName

data class RemoteAuthCredentials(
    @SerializedName(TOKEN) val token: String
)
