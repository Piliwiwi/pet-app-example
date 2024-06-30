package com.example.app.features.auth.login.presentation.login.mapper

import com.example.app.features.auth.login.data.remote.model.RemoteUserCredentialsParams
import com.example.app.features.auth.login.presentation.login.model.UserCredentials
import javax.inject.Inject

class CredentialsMapper @Inject constructor() {
    fun UserCredentials.toRemote() =
        RemoteUserCredentialsParams(
            email = email,
            password = password
        )
}