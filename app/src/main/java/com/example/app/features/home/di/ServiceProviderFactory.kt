package com.example.app.features.home.di

object ServiceProviderFactory {
    fun getInstanceService(): IServiceProvider {
        return HomeServiceProvider()
    }
}