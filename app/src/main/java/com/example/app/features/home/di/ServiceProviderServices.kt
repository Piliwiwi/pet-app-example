package com.example.app.features.home.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.app.common.di.WebServiceProviderFactory
import com.example.app.common.factory.NetworkDependenciesFactory
import com.example.app.features.home.data.HomeDataRepository
import com.example.app.features.home.data.remote.HomeRemoteImpl
import com.example.app.features.home.data.remote.retrofit.HomeApi
import com.example.app.features.home.data.source.HomeRemote
import com.example.app.features.home.presentation.ListViewModel
import com.example.app.features.home.presentation.list.ListProcessor
import com.example.app.features.home.presentation.list.ListReducer
import com.example.app.features.home.ui.navigator.HomeNavigator
import com.example.mvi.execution.AppExecutionThread
import com.example.mvi.execution.ExecutionThread

interface IServiceProvider {
    fun getApi(context: Context?): HomeApi
    fun getRemote(context: Context?): HomeRemote
    fun getRepository(context: Context?): HomeDataRepository
    fun getExecutionThread(): ExecutionThread
    fun getProcessor(context: Context?): ListProcessor
    fun getReducer(): ListReducer
    fun getViewModel(fragment: Fragment): ListViewModel
    fun getNavigator(): HomeNavigator
}

open class HomeServiceProvider : IServiceProvider {
    override fun getApi(context: Context?) =
        WebServiceProviderFactory.getWebServiceProvider().getWebFactory(
            tClass = HomeApi::class.java,
            dependencies = NetworkDependenciesFactory.makeNetworkDependencies(context)
        ).createWebServiceInstance()

    override fun getRemote(context: Context?) = HomeRemoteImpl(getApi(context))

    override fun getRepository(context: Context?) = HomeDataRepository(getRemote(context))

    override fun getExecutionThread() = AppExecutionThread()

    override fun getProcessor(context: Context?) =
        ListProcessor(getRepository(context), getExecutionThread())

    override fun getReducer() = ListReducer()

    override fun getViewModel(fragment: Fragment): ListViewModel =
        viewModelFactory(fragment, getProcessor(fragment.context), getReducer())

    override fun getNavigator() = HomeNavigator()
}