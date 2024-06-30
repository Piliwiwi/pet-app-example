package com.example.app.common.di

import android.content.Context
import android.net.ConnectivityManager
import com.example.network.config.NetworkDependencies
import com.example.network.retrofit.RemoteWebService
import com.example.network.retrofit.WebServiceFactory
import com.example.network.utils.NetworkConnection

open class WebServiceProvider {

    fun <T> getWebFactory(tClass: Class<T>, dependencies: NetworkDependencies) =
        WebServiceFactory(
            tClass = tClass,
            dependencies
        )

    open fun <TRetrofitWebService> getWebService(): RemoteWebService<TRetrofitWebService> {
        return RemoteWebService()
    }

    private fun getConnectivityManager(context: Context?) = context?.getSystemService(
        Context.CONNECTIVITY_SERVICE
    ) as ConnectivityManager

    private fun getNetworkConnection(context: Context?) =
        NetworkConnection(getConnectivityManager(context))
}