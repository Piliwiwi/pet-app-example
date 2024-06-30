package com.example.app.features.pet.profile.example.di

object ServiceProviderFactory {
    fun getInstanceProvider(): IServicesProvider {
        return PetPrivateProfileServiceProvider()
    }
}
