package com.example.app.features.auth.login.di

import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.app.features.auth.login.presentation.login.LoginProcessor
import com.example.app.features.auth.login.presentation.login.LoginReducer

class LoginViewModelFactory(
    private val processor: LoginProcessor,
    private val reducer: LoginReducer
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(
            LoginProcessor::class.java,
            LoginReducer::class.java
        ).newInstance(processor, reducer)
    }
}

inline fun <reified T : ViewModel> getViewModel(
    fragment: Fragment,
    processor: LoginProcessor,
    reducer: LoginReducer
): T {
    return ViewModelProvider(
        fragment,
        com.example.app.features.auth.login.di.LoginViewModelFactory(processor, reducer)
    ).get(T::class.java)
}