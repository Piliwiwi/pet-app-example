package com.example.app.features.auth.login.presentation.login

import com.example.app.common.config.Messages.GENERIC_NETWORK_ERROR_MESSAGE
import com.example.app.features.auth.login.presentation.login.events.LoginAction
import com.example.app.features.auth.login.presentation.login.events.LoginAction.GoToForgotPasswordAction
import com.example.app.features.auth.login.presentation.login.events.LoginAction.HandleLoginAction
import com.example.app.features.auth.login.presentation.login.events.LoginResult
import com.example.app.features.auth.login.presentation.login.events.LoginResult.GoToForgotPasswordResult
import com.example.app.features.auth.login.presentation.login.events.LoginResult.HandLeLoginResult.APIError
import com.example.app.features.auth.login.presentation.login.events.LoginResult.HandLeLoginResult.InProgress
import com.example.app.features.auth.login.presentation.login.events.LoginResult.HandLeLoginResult.Success
import com.example.app.features.auth.login.presentation.login.mapper.CredentialsMapper
import com.example.app.features.auth.login.presentation.login.model.UserCredentials
import com.example.mvi.execution.ExecutionThread
import com.example.network.utils.NetworkErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart

class LoginProcessor(
    private val repository: com.example.app.features.auth.login.data.LoginDataRepository,
    private val mapper: CredentialsMapper,
    private val errorHandler: NetworkErrorHandler,
    private val executionThread: ExecutionThread
) {
    fun actionProcessor(action: LoginAction): Flow<LoginResult> =
        when (action) {
            is HandleLoginAction -> handleLogin(action.credentials)
            GoToForgotPasswordAction -> goToForgotPassword()
        }

    private fun handleLogin(credentials: UserCredentials) = flow<LoginResult> {
        val remoteCredentials = with(mapper) { credentials.toRemote() }
        repository.login(remoteCredentials).collect { response ->
            emit(Success(response.token))
        }
    }.onStart {
        emit(InProgress)
    }.catch { cause ->
        emit(APIError(with(errorHandler) { cause.toNetworkError(GENERIC_NETWORK_ERROR_MESSAGE) }))
    }.flowOn(executionThread.ioThread())

    private fun goToForgotPassword() = flow<LoginResult> {
        emit(GoToForgotPasswordResult)
    }
}