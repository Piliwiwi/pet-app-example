package com.example.network.retrofit

import com.example.network.config.Environment
import com.example.network.config.NetworkDependencies
import com.example.network.config.WebServiceConfig.Url

class WebServiceFactory<TWebService> constructor(
    private val tClass: Class<TWebService>,
    private val dependencies: NetworkDependencies,
) {

    fun createWebServiceInstance(): TWebService {
        return when (dependencies.environment) {
            Environment.Dummy.name -> createLocalWebServiceConfig()
            Environment.Dev.name -> createRemoteWebServiceConfig(
                baseUrl = Environment.Dev.url,
            )
            Environment.Staging.name -> createRemoteWebServiceConfig(
                baseUrl = Environment.Staging.url,
            )
            Environment.Prod.name -> createRemoteWebServiceConfig(
                baseUrl = Environment.Prod.url,
            )
            else -> createLocalWebServiceConfig()
        }
    }

    private fun createLocalWebServiceConfig(): TWebService =
        LocalWebService<TWebService>().create(
            context = dependencies.context,
            tClass = tClass,
            hostUrl = Url.LOCAL_HOST
        )

    private fun createRemoteWebServiceConfig(
        baseUrl: String,
    ): TWebService {
        return RemoteWebService<TWebService>().create(
            context = dependencies.context,
            tClass = tClass,
            baseUrl = baseUrl
        )
    }
}