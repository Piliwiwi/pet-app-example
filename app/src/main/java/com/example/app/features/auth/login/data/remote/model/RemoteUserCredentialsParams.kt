package com.example.app.features.auth.login.data.remote.model

import com.example.app.features.auth.login.data.remote.config.Constants.EMAIL
import com.example.app.features.auth.login.data.remote.config.Constants.PASSWORD
import com.google.gson.annotations.SerializedName

data class RemoteUserCredentialsParams(
    @SerializedName(EMAIL) val email: String,
    @SerializedName(PASSWORD) val password: String
)
