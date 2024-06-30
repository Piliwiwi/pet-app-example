package com.example.app.features.pet.add.ui.add

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.app.R
import com.example.app.databinding.FragmentPetAddBinding
import com.example.app.features.pet.add.di.ServiceProviderFactory.getInstanceService
import com.example.app.features.pet.add.presentation.AddViewModel
import com.example.app.features.pet.add.presentation.add.event.AddUIntent
import com.example.app.features.pet.add.presentation.add.event.AddUiEffect
import com.example.app.features.pet.add.presentation.add.event.AddUiState
import com.example.app.features.pet.add.presentation.add.event.AddUiState.ShowContentUiState
import com.example.app.features.pet.add.presentation.add.model.AnimalPair
import com.example.app.features.pet.add.presentation.add.model.DatePair
import com.example.app.features.pet.add.presentation.add.model.SelectorData
import com.example.app.features.pet.add.ui.navitgation.PetNavigator
import com.example.mvi.MviUi
import com.example.mvi.MviUiEffect
import com.example.uicomponents.ui.component.AttrsOptionComponent
import com.example.uicomponents.ui.component.buttons.AttrsCircleButtonComponent
import com.example.uicomponents.ui.component.buttons.AttrsTitledButton
import com.example.uicomponents.ui.component.inputs.AttrsInputText
import com.example.uicomponents.ui.component.inputs.AttrsPickerInputTextComponent
import com.example.uicomponents.ui.component.inputs.AttrsPickerInputTextData
import com.example.uicomponents.ui.component.inputs.PickerType.DATE
import com.example.uicomponents.ui.component.inputs.PickerType.SELECTOR
import com.example.uicomponents.ui.dialogs.selector.AttrsDialogSelector
import com.example.uicomponents.ui.groupcomponent.selectorbuttonlist.AttrsSelectorButtonListComponent
import java.util.Calendar
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach

class AddFragment : Fragment(), MviUi<AddUIntent, AddUiState> {
    private var binding: FragmentPetAddBinding? = null
    private var viewModel: AddViewModel? = null
    private var navigator: PetNavigator? = null
    private val userIntents: MutableSharedFlow<AddUIntent> = MutableSharedFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDependencies()
        subscribeToProcessAndObserveStates()
        subscribeStatesProcessIntents()
    }

    private fun subscribeToProcessAndObserveStates() {
        viewModel?.run {
            uiStates().onEach { renderUiStates(it) }.launchIn(lifecycleScope)
        }
    }

    private fun setupDependencies() {
        getInstanceService().apply {
            viewModel = getAddViewModel(this@AddFragment)
            navigator = getNavigator()
        }
    }

    private fun subscribeStatesProcessIntents() {
        viewModel?.processUserIntents(userIntents())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) binding = FragmentPetAddBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        (viewModel?.uiStates()?.value as? ShowContentUiState)?.let {
            setupView(it.data)
        }
    }

    override fun userIntents(): Flow<AddUIntent> = merge(
        initialUserIntent(),
        userIntents.asSharedFlow()
    )

    private fun initialUserIntent() = flow<AddUIntent> {
        emit(AddUIntent.InitialUIntent)
    }

    override fun renderUiStates(uiStates: AddUiState) {
        hideAll()
        when (uiStates) {
            AddUiState.DefaultUiState -> {}
            AddUiState.ErrorUiState -> {}
            AddUiState.LoadingUiState -> showLoading()
            is ShowContentUiState -> setupView(uiStates.data)
        }
    }

    private fun hideAll() = binding?.apply {
        addPetFragmentScroll.isVisible = false
        addPetFragmentPhotoPicker.isVisible = false
        addPetFragmentLoader.isVisible = false
    }

    private fun showLoading() = binding?.apply {
        addPetFragmentLoader.isVisible = true
    }

    private fun setupView(species: List<SelectorData>) = binding?.apply {
        setContentVisibility()
        setInputs(species)
        setSelectorButtons()
        setButton()
        recoverData()
    }

    private fun setContentVisibility() = binding?.apply {
        addPetFragmentScroll.isVisible = true
        addPetFragmentPhotoPicker.isVisible = true
    }

    private fun setInputs(species: List<SelectorData>) = binding?.apply {
        addPetFragmentNameInput.setAttributes(
            attrs = AttrsInputText(
                hint = getString(R.string.name)
            )
        )
        addPetFragmentSpecieInput.setAttributes(
            attrs = AttrsPickerInputTextComponent(
                type = SELECTOR,
                data = AttrsDialogSelector(
                    buttonAction = {},
                    title = getString(R.string.choose_a_specie),
                    buttonText = getString(R.string.cancel),
                    inputLabelText = getString(R.string.specie),
                    itemList = species.map {
                        AttrsOptionComponent(
                            text = it.name,
                            data = it.code
                        )
                    },
                    onSelectItem = { specie, _ ->
                        addPetFragmentSpecieInput.setText(specie)
                    }
                )
            )
        )
        addPetFragmentDatePicker.setAttributes(
            attrs = AttrsPickerInputTextComponent(
                type = DATE,
            )
        )
        addPetFragmentNickName.setAttributes(
            attrs = AttrsInputText(
                hint = getString(R.string.nick_name),
                helperText = getString(R.string.optional_field)
            )
        )
    }

    private fun setSelectorButtons() = binding?.apply {
        addPetFragmentSelectorButtons.setAttributes(
            attrs = AttrsSelectorButtonListComponent(
                buttons = listOf(
                    AttrsCircleButtonComponent(
                        icon = com.example.uicomponents.R.drawable.ui_ic_male26dp,
                        isChecked = false,
                        key = "M",
                    ),
                    AttrsCircleButtonComponent(
                        icon = com.example.uicomponents.R.drawable.ui_ic_female_29dp,
                        isChecked = false,
                        key = "F"
                    )
                ),
            )
        )
    }

    private fun setButton() = binding?.apply {
        addPetFragmentPrimaryButton.setAttributes(
            AttrsTitledButton(
                buttonText = getString(R.string.next),
                onClick = {
                    if (areInputsValid()) {
                        saveDataOnViewModel()
                        goToAddPetDescription()
                    }
                }
            )
        )
    }

    private fun areInputsValid(): Boolean {
        if (binding?.addPetFragmentNameInput?.getText().isNullOrEmpty()) {
            binding?.addPetFragmentNameInput?.setError(getString(R.string.your_pet_should_have_a_name))
            return false
        }
        if (binding?.addPetFragmentSpecieInput?.getData()?.name.isNullOrEmpty()) {
            binding?.addPetFragmentSpecieInput?.setError(getString(R.string.we_must_known_your_pet_specie))
            return false
        }
        if (binding?.addPetFragmentSelectorButtons?.getSelectedOption() == null) {
            Toast.makeText(context, getString(R.string.we_need_your_pet_gender), Toast.LENGTH_SHORT)
                .show()
            return false
        }
        if (((binding?.addPetFragmentDatePicker?.getData()?.data as? Long)
                ?: 0L) > Calendar.getInstance().timeInMillis
        ) {
            Toast.makeText(
                context,
                getString(R.string.your_pet_not_birth_in_the_future),
                Toast.LENGTH_SHORT
            ).show()
            return false
        }
        return true
    }

    private fun saveDataOnViewModel() = binding?.apply {
        viewModel?.petData?.let {
            it.name = addPetFragmentNameInput.getText()
            it.photo = addPetFragmentPhotoPicker.getSelectedImage()?.toString()
            it.specie = AnimalPair(
                name = addPetFragmentSpecieInput.getData()?.name.orEmpty(),
                code = (addPetFragmentSpecieInput.getData()?.data as? String).orEmpty()
            )
            it.genre = addPetFragmentSelectorButtons.getSelectedOption()?.key
            it.birthDate = DatePair(
                name = addPetFragmentDatePicker.getData()?.name.orEmpty(),
                millis = (addPetFragmentDatePicker.getData()?.data as? Long) ?: 0L
            )
            it.nickName = addPetFragmentNickName.getText()
        }
    }

    private fun goToAddPetDescription() = binding?.apply {
        navigator?.goToAddPetDescription(
            view = view,
            petName = addPetFragmentNameInput.getText(),
            nickName = addPetFragmentNickName.getText(),
            photoUri = addPetFragmentPhotoPicker.getSelectedImage()?.toString(),
            specie = (addPetFragmentSpecieInput.getData()?.data as? String).orEmpty(),
            gender = addPetFragmentSelectorButtons.getSelectedOption()?.key,
            birthDate = (addPetFragmentDatePicker.getData()?.data as? Long) ?: 0L,
            specieName = addPetFragmentSpecieInput.getData()?.name.orEmpty()
        )
    }

    private fun recoverData() = binding?.apply {
        viewModel?.petData?.apply {
            photo?.let { addPetFragmentPhotoPicker.setSelectedImage(it.toUri()) }
            if (name.isNullOrEmpty()) addPetFragmentNameInput.setHint(getString(R.string.name))
            else addPetFragmentNameInput.setText(name.orEmpty())
            genre?.let { addPetFragmentSelectorButtons.setSelectedOption(it) }
            if (specie?.name.isNullOrEmpty().not() && specie?.code.isNullOrEmpty().not()) {
                addPetFragmentSpecieInput.setData(
                    AttrsPickerInputTextData(
                        name = specie?.name.orEmpty(),
                        data = specie?.code.orEmpty()
                    )
                )
            } else addPetFragmentSpecieInput.setText(getString(R.string.specie))
            if (birthDate?.name.isNullOrEmpty().not() && birthDate?.millis != null) {
                addPetFragmentDatePicker.setData(
                    AttrsPickerInputTextData(
                        name = birthDate?.name.orEmpty(),
                        data = birthDate?.millis ?: 0L
                    )
                )
            } else addPetFragmentDatePicker.setText(getString(R.string.birth_date))
            if (nickName.isNullOrEmpty()) {
                addPetFragmentNickName.setHint(getString(R.string.nick_name))
                addPetFragmentNickName.setText(null)
            } else addPetFragmentNickName.setText(nickName.orEmpty())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.addPetFragmentNameInput?.removeAllViews()
        binding?.addPetFragmentNickName?.removeAllViews()
        binding?.root?.removeAllViews()
        binding = null
    }
}
