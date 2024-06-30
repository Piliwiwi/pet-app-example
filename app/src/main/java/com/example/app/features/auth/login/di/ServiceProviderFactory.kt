package com.example.app.features.auth.login.di


object ServiceProviderFactory {
    fun getInstanceService(): IServiceProvider {
        return LoginServiceProvider()
    }
}