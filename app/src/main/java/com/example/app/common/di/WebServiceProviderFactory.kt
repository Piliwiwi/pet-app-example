package com.example.app.common.di

object WebServiceProviderFactory {
    private var instance = WebServiceProvider()

    fun getWebServiceProvider(): WebServiceProvider {
        return instance
    }
}