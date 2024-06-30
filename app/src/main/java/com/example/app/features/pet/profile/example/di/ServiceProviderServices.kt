package com.example.app.features.pet.profile.example.di

import android.content.Context
import androidx.fragment.app.Fragment
import com.example.app.common.di.WebServiceProviderFactory.getWebServiceProvider
import com.example.app.common.factory.NetworkDependenciesFactory.makeNetworkDependencies
import com.example.app.features.pet.profile.example.data.ProfileRepository
import com.example.app.features.pet.profile.example.data.remote.ProfileRemoteImpl
import com.example.app.features.pet.profile.example.data.remote.retofit.ProfileWebService
import com.example.app.features.pet.profile.example.data.source.ProfileRemote
import com.example.app.features.pet.profile.example.presentation.ProfileViewModel
import com.example.app.features.pet.profile.example.presentation.mapper.ProfileMapper
import com.example.mvi.execution.AppExecutionThread
import com.example.mvi.execution.ExecutionThread

interface IServicesProvider {
    fun getApi(context: Context?): ProfileWebService
    fun getRemote(context: Context?): ProfileRemote
    fun getRepository(context: Context?): ProfileRepository
    fun getExecutionThread(): ExecutionThread
    fun getViewModel(fragment: Fragment): ProfileViewModel
    fun getMapper(): ProfileMapper
}

open class PetPrivateProfileServiceProvider : IServicesProvider {
    override fun getApi(context: Context?) = getWebServiceProvider().getWebFactory(
        tClass = ProfileWebService::class.java,
        dependencies = makeNetworkDependencies(context)
    ).createWebServiceInstance()

    override fun getRemote(context: Context?) = ProfileRemoteImpl(getApi(context))
    override fun getRepository(context: Context?) = ProfileRepository(getRemote(context))
    override fun getExecutionThread() = AppExecutionThread()

    override fun getMapper() = ProfileMapper()
    override fun getViewModel(fragment: Fragment): ProfileViewModel =
        petPrivateProfileViewModelFactory(fragment, getRepository(fragment.context), getMapper())
}
