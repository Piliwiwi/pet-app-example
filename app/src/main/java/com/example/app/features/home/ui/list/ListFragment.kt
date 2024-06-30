package com.example.app.features.home.ui.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.app.databinding.FragmentPetListBinding
import com.example.app.features.home.di.ServiceProviderFactory.getInstanceService
import com.example.app.features.home.presentation.ListViewModel
import com.example.app.features.home.presentation.list.event.ListUIntent
import com.example.app.features.home.presentation.list.event.ListUIntent.InitialUIntent
import com.example.app.features.home.presentation.list.event.ListUiState
import com.example.app.features.home.presentation.list.event.ListUiState.DefaultUiState
import com.example.app.features.home.presentation.list.event.ListUiState.ErrorUiState
import com.example.app.features.home.presentation.list.event.ListUiState.LoadingUiState
import com.example.app.features.home.presentation.list.event.ListUiState.ShowContentUiState
import com.example.app.features.home.ui.navigator.HomeNavigator
import com.example.app.features.pet.add.AddPetActivity
import com.example.mvi.MviUi
import com.example.uicomponents.data.PetCardResponse
import com.example.uicomponents.ui.groupcomponent.petcardlist.AttrsPetCardListComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class ListFragment : Fragment(), MviUi<ListUIntent, ListUiState> {
    private var binding: FragmentPetListBinding? = null

    private var viewModel: ListViewModel? = null
    private var navigator: HomeNavigator? = null
    private val userIntents: MutableSharedFlow<ListUIntent> = MutableSharedFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDependencies()
        setupObservers()
    }

    private fun setupDependencies() {
        getInstanceService().apply {
            viewModel = getViewModel(this@ListFragment)
            navigator = getNavigator()
        }
    }

    private fun setupObservers() {
        viewModel?.run {
            uiStates().onEach { renderUiStates(it) }.launchIn(lifecycleScope)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        if (binding == null)
            binding = FragmentPetListBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeStatesProcessIntents()
    }

    private fun subscribeStatesProcessIntents() {
        viewModel?.processUserIntents(userIntents())
    }

    override fun userIntents(): Flow<ListUIntent> = merge(
        initialUserIntent(),
        userIntents.asSharedFlow()
    )

    private fun initialUserIntent() = flow<ListUIntent> {
        emit(InitialUIntent)
    }

    override fun renderUiStates(uiStates: ListUiState) {
        hideAll()
        when (uiStates) {
            DefaultUiState -> {}
            LoadingUiState -> showLoading()
            is ErrorUiState -> showError(uiStates.error)
            is ShowContentUiState -> setupView(uiStates.pets)
        }
    }

    private fun hideAll() = binding?.apply {
        loader.isVisible = false
        petList.isVisible = false
    }

    private fun showLoading() = binding?.apply {
        loader.isVisible = true
    }

    private fun showError(error: Throwable) {
        print(error.message)
    }

    private fun setupView(pets: List<PetCardResponse>) {
        showPetList(pets)
        setFloatingActionButtonListener()
    }

    private fun showPetList(pets: List<PetCardResponse>) = binding?.apply {
        petList.isVisible = true
        petList.setAttributes(AttrsPetCardListComponent(pets) { petId ->
            navigator?.goToPetPrivateProfile(context, petId)
        })
        addPetButton.setOnClickListener {
        }
    }

    private fun setFloatingActionButtonListener() = binding?.apply {
        addPetButton.setOnClickListener {
            context?.let {
                val intent = AddPetActivity.makeIntent(it)
                startActivity(intent)
            }
        }
    }

    private fun emit(intent: ListUIntent) {
        viewLifecycleOwner.lifecycleScope.launch {
            userIntents.emit(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
