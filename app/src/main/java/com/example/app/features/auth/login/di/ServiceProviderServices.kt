package com.example.app.features.auth.login.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.app.common.di.WebServiceProviderFactory
import com.example.app.common.factory.NetworkDependenciesFactory.makeNetworkDependencies
import com.example.app.features.auth.login.presentation.LoginViewModel
import com.example.app.features.auth.login.presentation.login.LoginProcessor
import com.example.app.features.auth.login.presentation.login.LoginReducer
import com.example.app.features.auth.login.presentation.login.mapper.CredentialsMapper
import com.example.mvi.execution.AppExecutionThread
import com.example.mvi.execution.ExecutionThread
import com.example.network.security.PassportTokenManager
import com.example.network.utils.NetworkErrorHandler

interface IServiceProvider {
    fun getSharedPreferences(context: Context?): com.example.cache.sharedpreferences.DPSharedPreferences?
    fun getTokenManager(context: Context?): PassportTokenManager
    fun getLoginCache(context: Context?): com.example.app.features.auth.login.data.source.LoginCache
    fun getLoginApi(context: Context?): com.example.app.features.auth.login.data.remote.retrofit.LoginWebServiceAPI
    fun getLoginRemote(context: Context?): com.example.app.features.auth.login.data.source.LoginRemote
    fun getLoginRepository(context: Context?): com.example.app.features.auth.login.data.LoginDataRepository
    fun getExecutionThread(): ExecutionThread
    fun getProcessor(context: Context?): LoginProcessor
    fun getCredentialsMapper(): CredentialsMapper
    fun getNetworkErrorHandler(): NetworkErrorHandler
    fun getReducer(): LoginReducer
    fun getLoginViewModel(fragment: Fragment): LoginViewModel
}

open class LoginServiceProvider : IServiceProvider {
    override fun getSharedPreferences(context: Context?) =
        com.example.cache.sharedpreferences.DPSharedPreferences(context)

    override fun getTokenManager(context: Context?) = PassportTokenManager(
        getSharedPreferences(context).sharedPreferences
    )

    override fun getLoginCache(context: Context?) =
        com.example.app.features.auth.login.data.cache.LoginCacheImpl(getTokenManager(context))

    override fun getLoginApi(context: Context?) =
        WebServiceProviderFactory.getWebServiceProvider().getWebFactory(
            tClass = com.example.app.features.auth.login.data.remote.retrofit.LoginWebServiceAPI::class.java,
            dependencies = makeNetworkDependencies(context)
        ).createWebServiceInstance()

    override fun getLoginRemote(context: Context?) =
        com.example.app.features.auth.login.data.remote.LoginRemoteImpl(getLoginApi(context))

    override fun getLoginRepository(context: Context?) =
        com.example.app.features.auth.login.data.LoginDataRepository(
            remote = getLoginRemote(context),
            cache = getLoginCache(context)
        )

    override fun getExecutionThread(): ExecutionThread = AppExecutionThread()

    override fun getProcessor(context: Context?) = LoginProcessor(
        repository = getLoginRepository(context),
        mapper = getCredentialsMapper(),
        executionThread = getExecutionThread(),
        errorHandler = getNetworkErrorHandler()
    )

    override fun getCredentialsMapper() = CredentialsMapper()
    override fun getNetworkErrorHandler() = NetworkErrorHandler()

    override fun getReducer() = LoginReducer()

    override fun getLoginViewModel(fragment: Fragment): LoginViewModel =
        com.example.app.features.auth.login.di.getViewModel(
            fragment = fragment,
            processor = getProcessor(fragment.context),
            reducer = getReducer()
        )
}