package com.example.app.features.pet.add.ui.add

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.example.app.R
import com.example.app.databinding.FragmentPetDescriptionBinding
import com.example.app.features.pet.add.di.ServiceProviderFactory
import com.example.app.features.pet.add.presentation.AddViewModel
import com.example.app.features.pet.add.presentation.add.event.AddUIntent
import com.example.app.features.pet.add.presentation.add.event.AddUIntent.RegisterPetUIntent
import com.example.app.features.pet.add.presentation.add.event.AddUiEffect
import com.example.app.features.pet.add.presentation.add.event.AddUiState
import com.example.app.features.pet.add.presentation.add.event.AddUiState.DefaultUiState
import com.example.app.features.pet.add.presentation.add.event.AddUiState.ErrorUiState
import com.example.app.features.pet.add.presentation.add.event.AddUiState.LoadingUiState
import com.example.app.features.pet.add.presentation.add.event.AddUiState.ShowContentUiState
import com.example.app.features.pet.add.presentation.add.model.AnimalPair
import com.example.app.features.pet.add.presentation.add.model.DatePair
import com.example.app.features.pet.add.presentation.add.model.PetRegister
import com.example.app.features.pet.add.presentation.add.model.SelectorData
import com.example.app.features.pet.add.ui.navitgation.PetNavigator
import com.example.mvi.MviUi
import com.example.mvi.MviUiEffect
import com.example.uicomponents.ui.component.AttrsOptionComponent
import com.example.uicomponents.ui.component.AttrsResumePetProfileComponent
import com.example.uicomponents.ui.component.buttons.AttrsTitledButton
import com.example.uicomponents.ui.component.inputs.AttrsInputText
import com.example.uicomponents.ui.component.inputs.AttrsPickerInputTextComponent
import com.example.uicomponents.ui.component.inputs.PickerType.SELECTOR
import com.example.uicomponents.ui.dialogs.selector.AttrsDialogSelector
import com.example.utils.extension.translateMillis
import java.io.File
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@FlowPreview
class PetDescriptionFragment : Fragment(), MviUi<AddUIntent, AddUiState>,
    MviUiEffect<AddUiEffect> {
    private var binding: FragmentPetDescriptionBinding? = null
    private var viewModel: AddViewModel? = null
    private var navigator: PetNavigator? = null
    private val userIntents: MutableSharedFlow<AddUIntent> = MutableSharedFlow()
    private val args: PetDescriptionFragmentArgs by navArgs()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDependencies()
        subscribeToProcessAndObserveStates()
        subscribeStatesProcessIntents()
    }

    private fun setupDependencies() {
        ServiceProviderFactory.getInstanceService().apply {
            viewModel = getAddViewModel(this@PetDescriptionFragment)
            navigator = getNavigator()
        }
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

    override fun userIntents(): Flow<AddUIntent> = merge(
        initialUserIntent(),
        userIntents.asSharedFlow()
    )

    private fun initialUserIntent() = flow<AddUIntent> {
        emit(AddUIntent.BreedInitialUIntent(args.specie))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) binding =
            FragmentPetDescriptionBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun renderUiStates(uiStates: AddUiState) {
        hideAll()
        when (uiStates) {
            is DefaultUiState -> {}
            is ErrorUiState -> {}
            is LoadingUiState -> showLoading()
            is ShowContentUiState -> setupView(uiStates.data)
        }
    }

    override fun handleEffect(uiEffect: AddUiEffect) {
        when (uiEffect) {
            is AddUiEffect.NavToSuccessUiEffect -> navigator?.goToSuccess(view)

            is AddUiEffect.ShowRegistrationErrorUiEffect -> Toast.makeText(
                context,
                uiEffect.error.message,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun hideAll() = binding?.apply {
        petDescriptionFragmentAgeInput.isVisible = false
        petDescriptionFragmentBreedInput.isVisible = false
        petDescriptionFragmentDescriptionInput.isVisible = false
        petDescriptionFragmentResume.isVisible = false
        petDescriptionFragmentText.isVisible = false
        petDescriptionFragmentPrimaryButton.isVisible = false
        petDescriptionFragmentLoader.isVisible = false
    }

    private fun showLoading() = binding?.apply {
        petDescriptionFragmentLoader.isVisible = true
    }

    private fun setupView(data: List<SelectorData>) = binding?.apply {
        showContent()
        setInputs(data)
        setButton()
        setResume()
    }

    private fun showContent() = binding?.apply {
        petDescriptionFragmentAgeInput.isVisible = true
        petDescriptionFragmentBreedInput.isVisible = true
        petDescriptionFragmentDescriptionInput.isVisible = true
        petDescriptionFragmentResume.isVisible = true
        petDescriptionFragmentText.isVisible = true
        petDescriptionFragmentPrimaryButton.isVisible = true
    }

    private fun setInputs(data: List<SelectorData>) = binding?.apply {
        petDescriptionFragmentBreedInput.setLabelText(text = getString(R.string.breed))
        petDescriptionFragmentBreedInput.setAttributes(
            AttrsPickerInputTextComponent(
                type = SELECTOR,
                data = AttrsDialogSelector(
                    buttonAction = {},
                    title = getString(R.string.choose_a_breed),
                    buttonText = getString(R.string.cancel),
                    inputLabelText = getString(R.string.breed),
                    itemList = data.map {
                        AttrsOptionComponent(
                            text = it.name,
                            data = it.code
                        )
                    },
                    onSelectItem = { breed, _ ->
                        petDescriptionFragmentBreedInput.setText(breed)
                    }
                )
            )
        )
        if (args.birthDate > 0L) {
            val age = args.birthDate.translateMillis()
            petDescriptionFragmentAgeInput.setText(age)
            petDescriptionFragmentAgeInput.blockInput()
        } else petDescriptionFragmentAgeInput.isVisible = false
        petDescriptionFragmentDescriptionInput.setAttributes(
            attrs = AttrsInputText(
                hint = getString(
                    R.string.description
                )
            )
        )
    }

    private fun setButton() = binding?.apply {
        petDescriptionFragmentPrimaryButton.setAttributes(
            attrs = AttrsTitledButton(
                buttonText = getString(
                    R.string.finish
                ),
                onClick = {
                    emit(
                        RegisterPetUIntent(
                            data = PetRegister(
                                name = args.petName,
                                breedCode = petDescriptionFragmentBreedInput.getData()?.data as? String,
                                specie = AnimalPair(name = args.specieName, code = args.specie),
                                genre = args.gender.orEmpty(),
                                description = petDescriptionFragmentDescriptionInput.getText(),
                                birthDate = if (args.birthDate > 0L) DatePair(
                                    millis = args.birthDate
                                ) else null,
                                nickName = args.nickName
                            ),
                            photo = args.photoUri?.toUri()?.path?.let { File(it) }
                        )
                    )
                }
            )
        )
    }

    private fun setResume() = binding?.apply {
        val photo = args.photoUri?.toUri() ?: Uri.EMPTY
        petDescriptionFragmentResume.setAttributes(
            AttrsResumePetProfileComponent(
                photo = photo,
                animalType = args.specieName,
                gender = if (args.gender == "M") getString(R.string.male)
                else getString(R.string.female),
                name = args.petName,
                onClick = { activity?.onBackPressed() },
                icon = com.example.uicomponents.R.drawable.ui_ic_chevron_43dp
            )
        )
    }

    private fun emit(intent: AddUIntent) {
        viewLifecycleOwner.lifecycleScope.launch {
            userIntents.emit(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}