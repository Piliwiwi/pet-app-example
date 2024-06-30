package com.example.app.features.pet.add.di


object ServiceProviderFactory {
    fun getInstanceService(): IServiceProvider {
        return ListServiceProvider()
    }
}