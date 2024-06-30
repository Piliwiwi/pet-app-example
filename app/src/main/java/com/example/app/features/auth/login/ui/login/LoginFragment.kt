package com.example.app.features.auth.login.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.app.R
import com.example.app.databinding.FragmentLoginBinding
import com.example.app.features.auth.login.di.ServiceProviderFactory.getInstanceService
import com.example.app.features.auth.login.presentation.LoginViewModel
import com.example.app.features.auth.login.presentation.login.events.LoginUIntent
import com.example.app.features.auth.login.presentation.login.events.LoginUIntent.LoggingUIntent
import com.example.app.features.auth.login.presentation.login.events.LoginUiEffect
import com.example.app.features.auth.login.presentation.login.events.LoginUiEffect.NetworkErrorUiEffect
import com.example.app.features.auth.login.presentation.login.events.LoginUiState
import com.example.app.features.auth.login.presentation.login.events.LoginUiState.LoadingUiState
import com.example.app.features.auth.login.presentation.login.events.LoginUiState.ShowLoginScreenUiState
import com.example.app.features.auth.login.presentation.login.model.UserCredentials
import com.example.app.features.home.HomeActivity
import com.example.mvi.MviUi
import com.example.mvi.MviUiEffect
import com.example.network.utils.NetworkError
import com.example.uicomponents.ui.component.buttons.AttrsTitledButton
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
class LoginFragment : Fragment(), MviUi<LoginUIntent, LoginUiState>, MviUiEffect<LoginUiEffect> {
    private var binding: FragmentLoginBinding? = null

    private var viewModel: LoginViewModel? = null
    private val userIntents: MutableSharedFlow<LoginUIntent> = MutableSharedFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDependencies()
        subscribeToProcessAndObserveStates()
        subscribeStatesProcessIntents()
    }

    private fun setupDependencies() {
        getInstanceService().apply {
            viewModel = getLoginViewModel(this@LoginFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (binding == null)
            binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLoginScreen()
    }

    private fun subscribeStatesProcessIntents() {
        viewModel?.processUserIntents(userIntents())
    }

    private fun subscribeToProcessAndObserveStates() {
        viewModel?.run {
            uiStates().onEach { renderUiStates(it) }.launchIn(lifecycleScope)
            uiEffect().onEach { handleEffect(it) }.launchIn(lifecycleScope)
        }
    }

    override fun userIntents(): Flow<LoginUIntent> = userIntents.asSharedFlow()

    override fun renderUiStates(uiStates: LoginUiState) {
        hideLoader()
        when (uiStates) {
            ShowLoginScreenUiState -> goToHome()
            LoadingUiState -> showLoading()
            else -> {}
        }
    }

    override fun handleEffect(uiEffect: LoginUiEffect) {
        when (uiEffect) {
            is NetworkErrorUiEffect -> showNetworkErrorSnackBar(uiEffect.error)
        }
    }

    private fun showLoginScreen() = binding?.apply {
        showContent()

        loginFragmentEnterButton.setAttributes(
            AttrsTitledButton(
                buttonText = getString(R.string.login),
                onClick = {

                    val email = loginFragmentEmailInput.getText()
                    val password = loginFragmentPasswordInput.getText()

                    val hasValidateError = validateEmail(email)
                        .or(validatePassword(password))

                    if (!hasValidateError) {
                        emit(
                            LoggingUIntent(
                                UserCredentials(
                                    loginFragmentEmailInput.getText(),
                                    loginFragmentPasswordInput.getText()
                                )
                            )
                        )
                    }
                }
            )
        )
        loginFragmentRegisterButton.setAttributes(
            AttrsTitledButton(
                buttonText = getString(R.string.create_account),
                onClick = {
                    context?.let {
                        Toast.makeText(it, "Hata aquí llega la demo", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        )
        loginFragmentGoogleRegisterButton.setAttributes(
            AttrsTitledButton(
                buttonText = getString(R.string.register_with_google),
                onClick = {}
            )
        )
    }

    private fun validateEmail(email: String): Boolean {
        return if (email.isEmpty()) {
            if (email.isEmpty()) {
                binding?.loginFragmentEmailInput?.setError(
                    message = getString(R.string.you_must_enter_an_email)
                )
            }
            true
        } else {
            false
        }
    }

    private fun validatePassword(password: String): Boolean {
        return if (password.isEmpty()) {
            if (password.isEmpty()) {
                binding?.loginFragmentPasswordInput?.setError(
                    message = getString(R.string.you_must_enter_a_password)
                )
            }
            true
        } else {
            false
        }
    }

    private fun hideLoader() = binding?.apply {

    }

    private fun goToHome() = activity?.apply {
        val intent = HomeActivity.makeIntent(this)
        startActivity(intent)
        finish()
    }

    private fun showContent() = binding?.apply {
        loginFragmentTitle.isVisible = true
        loginFragmentSubtitle.isVisible = true
        loginFragmentEmailInput.isVisible = true
        loginFragmentPasswordInput.isVisible = true
        loginFragmentEnterButton.isVisible = true
        loginFragmentRegisterButton.isVisible = true
        loginFragmentGoogleRegisterButton.isVisible = false
        loginOr.isVisible = true
    }

    private fun emit(intent: LoginUIntent) {
        viewLifecycleOwner.lifecycleScope.launch {
            userIntents.emit(intent)
        }
    }

    private fun showLoading() = binding?.apply {
        //Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
    }

    private fun showNetworkErrorSnackBar(error: NetworkError) = view?.apply {
        Snackbar.make(this, error.message, Snackbar.LENGTH_SHORT).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}