package com.example.app.features.pet.profile.example.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app.features.pet.profile.example.data.ProfileRepository
import com.example.app.features.pet.profile.example.presentation.mapper.ProfileMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class PetPrivateProfileViewModelFactory(
    private val repository: ProfileRepository,
    private val mapper: ProfileMapper,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            ProfileRepository::class.java,
            ProfileMapper::class.java,
            CoroutineDispatcher::class.java,
        ).newInstance(repository, mapper, dispatcher)
    }
}

inline fun <reified T : ViewModel> petPrivateProfileViewModelFactory(
    fragment: Fragment,
    repository: ProfileRepository,
    mapper: ProfileMapper
): T {
    return ViewModelProvider(
        fragment,
        PetPrivateProfileViewModelFactory(repository, mapper)
    )[T::class.java]
}
