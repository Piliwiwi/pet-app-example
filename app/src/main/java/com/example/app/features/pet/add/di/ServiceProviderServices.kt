package com.example.app.features.pet.add.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.app.common.di.WebServiceProviderFactory.getWebServiceProvider
import com.example.app.common.factory.NetworkDependenciesFactory.makeNetworkDependencies
import com.example.app.features.pet.add.data.AddDataRepository
import com.example.app.features.pet.add.data.remote.AddRemoteImpl
import com.example.app.features.pet.add.data.remote.retrofit.PetApi
import com.example.app.features.pet.add.data.source.AddRemote
import com.example.app.features.pet.add.presentation.AddViewModel
import com.example.app.features.pet.add.presentation.add.AddProcessor
import com.example.app.features.pet.add.presentation.add.AddReducer
import com.example.app.features.pet.add.presentation.add.mapper.PetRegisterMapper
import com.example.app.features.pet.add.presentation.add.mapper.SpeciesMapper
import com.example.app.features.pet.add.ui.navitgation.PetNavigator
import com.example.mvi.execution.AppExecutionThread
import com.example.mvi.execution.ExecutionThread
import com.example.network.utils.NetworkErrorHandler

interface IServiceProvider {
    fun getApi(context: Context?): PetApi
    fun getRemote(context: Context?): AddRemote
    fun getRepository(context: Context?): AddDataRepository
    fun getExecutionThread(): ExecutionThread
    fun getAddProcessor(context: Context?): AddProcessor
    fun getAddReducer(): AddReducer
    fun getAddViewModel(fragment: Fragment): AddViewModel
    fun getNavigator(): PetNavigator
}

open class ListServiceProvider : IServiceProvider {
    override fun getApi(context: Context?) =
        getWebServiceProvider().getWebFactory(
            tClass = PetApi::class.java,
            dependencies = makeNetworkDependencies(context)
        ).createWebServiceInstance()

    override fun getRemote(context: Context?) = AddRemoteImpl(getApi(context))

    override fun getRepository(context: Context?) = AddDataRepository(getRemote(context))

    override fun getExecutionThread() = AppExecutionThread()

    override fun getAddProcessor(context: Context?): AddProcessor =
        AddProcessor(
            getRepository(context),
            PetRegisterMapper(),
            SpeciesMapper(),
            NetworkErrorHandler(),
            getExecutionThread()
        )

    override fun getAddReducer() = AddReducer()

    override fun getAddViewModel(fragment: Fragment): AddViewModel =
        addViewModelFactory(fragment, getAddProcessor(fragment.context), getAddReducer())

    override fun getNavigator() = PetNavigator()
}