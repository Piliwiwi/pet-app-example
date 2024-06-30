package com.example.app.features.pet.add.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app.features.pet.add.presentation.add.AddProcessor
import com.example.app.features.pet.add.presentation.add.AddReducer

class AddViewModelFactory(
    private val processor: AddProcessor,
    private val reducer: AddReducer
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            AddProcessor::class.java,
            AddReducer::class.java
        ).newInstance(processor, reducer)
    }
}

inline fun <reified T : ViewModel> addViewModelFactory(
    fragment: Fragment,
    processor: AddProcessor,
    reducer: AddReducer
): T {
    return ViewModelProvider(
        fragment,
        AddViewModelFactory(processor, reducer)
    )[T::class.java]
}