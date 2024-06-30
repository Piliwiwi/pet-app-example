package com.example.app.features.home.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app.features.home.presentation.list.ListProcessor
import com.example.app.features.home.presentation.list.ListReducer

class ListViewModelFactory(
    private val processor: ListProcessor,
    private val reducer: ListReducer
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            ListProcessor::class.java,
            ListReducer::class.java
        ).newInstance(processor, reducer)
    }
}

inline fun <reified T : ViewModel> viewModelFactory(
    fragment: Fragment,
    processor: ListProcessor,
    reducer: ListReducer
): T {
    return ViewModelProvider(
        fragment,
        ListViewModelFactory(processor, reducer)
    )[T::class.java]
}