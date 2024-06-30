package com.example.app.features.pet.profile.example.ui.exampleprofile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.app.R
import com.example.app.databinding.FragmentPetPrivateProfileBinding
import com.example.app.features.pet.profile.example.di.ServiceProviderFactory.getInstanceProvider
import com.example.app.features.pet.profile.example.presentation.ProfileViewModel
import com.example.app.features.pet.profile.example.presentation.events.ProfileUiStates
import com.example.app.features.pet.profile.example.presentation.events.ProfileUiStates.DefaultUiState
import com.example.app.features.pet.profile.example.presentation.events.ProfileUiStates.DisplayProfileUiState
import com.example.app.features.pet.profile.example.presentation.events.ProfileUiStates.ErrorUiState
import com.example.app.features.pet.profile.example.presentation.events.ProfileUiStates.LoadingUiState
import com.example.app.features.pet.profile.example.presentation.events.ProfileUserIntents
import com.example.app.features.pet.profile.example.presentation.model.PetProfile
import com.example.mvi.MviUi
import com.example.uicomponents.ui.component.datacard.AttrsDataCardComponent
import com.example.uicomponents.ui.component.datacard.CardStyle.BLUE
import com.example.uicomponents.ui.component.gradientcard.AttrsGradientCardComponent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach

class PetPrivateProfileFragment : Fragment(),
    MviUi<ProfileUserIntents, ProfileUiStates> {
    private var binding: FragmentPetPrivateProfileBinding? = null
    private var viewModel: ProfileViewModel? = null
    private val userIntents: MutableSharedFlow<ProfileUserIntents> = MutableSharedFlow()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupDependencies()
        subscribeToProcessAndObserveStates()
        subscribeStatesProcessIntents()
    }

    private fun setupDependencies() {
        getInstanceProvider().apply {
            viewModel = getViewModel(this@PetPrivateProfileFragment)
        }
    }

    private fun subscribeToProcessAndObserveStates() {
        viewModel?.run {
            uiStates().onEach { renderUiStates(it) }.launchIn(lifecycleScope)
        }
    }

    private fun subscribeStatesProcessIntents() {
        viewModel?.processUserIntents(userIntents())
    }

    override fun userIntents(): Flow<ProfileUserIntents> = merge(
        initialUserIntent(),
        userIntents.asSharedFlow()
    )

    private fun initialUserIntent() = flow<ProfileUserIntents> {
        getPetId()?.let { emit(ProfileUserIntents.InitialUserIntent(it)) }
    }

    private fun getPetId() = activity?.intent?.extras?.getString("petId")

    override fun renderUiStates(uiState: ProfileUiStates) {
        hideAll()
        when (uiState) {
            DefaultUiState -> {}
            is ErrorUiState -> {}
            LoadingUiState -> showLoading()
            is DisplayProfileUiState -> setupView(uiState.profile)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) binding =
            FragmentPetPrivateProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    private fun hideAll() = binding?.apply {
        petPrivateProfileFragmentLoader.isVisible = false
        petPrivateProfileFragmentPetPhoto.isVisible = false
        petPrivateProfileFragmentNickName.isVisible = false
        petPrivateProfileFragmentNameSticky.isVisible = false
        petPrivateProfileFragmentLinearLayout.isVisible = false
        petPrivateProfileFragmentAgeView.isVisible = false
        petPrivateProfileFragmentBreedView.isVisible = false
        petPrivateProfileFragmentGenderView.isVisible = false
        petPrivateProfileFragmentDescription.isVisible = false
        petPrivateProfileFragmentSeeMore.isVisible = false
        petPrivateProfileFragmentHealthHeader.isVisible = false
    }

    private fun showContent(profile: PetProfile) = binding?.apply {
        petPrivateProfileFragmentPetPhoto.isVisible = true
        petPrivateProfileFragmentNickName.isVisible = profile.nickName.isNotEmpty()
        petPrivateProfileFragmentLinearLayout.isVisible = true
        petPrivateProfileFragmentAgeView.isVisible = true
        petPrivateProfileFragmentBreedView.isVisible = true
        petPrivateProfileFragmentGenderView.isVisible = true
        petPrivateProfileFragmentDescription.isVisible = true
        petPrivateProfileFragmentHealthHeader.isVisible = true
        petPrivateProfileFragmentScrollView.isVisible = true
    }

    private fun setupView(profile: PetProfile) {
        setProfileCardImage(profile)
        setTexts(profile)
        setDataCards(profile)
        setSeeMoreListener()
        setStickyBehaviour()
        showContent(profile)
    }

    private fun setProfileCardImage(profile: PetProfile) =
        binding?.petPrivateProfileFragmentPetPhoto?.apply {
            this.setAttributes(
                attr = AttrsGradientCardComponent(
                    photo = profile.profileImage.toUri()
                )
            )
        }

    private fun setTexts(profile: PetProfile) = binding?.apply {
        petPrivateProfileFragmentNickName.text = profile.nickName
        petPrivateProfileFragmentName.text = profile.name
        petPrivateProfileFragmentNameSticky.text = profile.name
        petPrivateProfileFragmentBirthDate.text =
            profile.birthDate ?: "Día de nacimiento sin especificar"
        petPrivateProfileFragmentDescription.text = profile.description
    }

    private fun setSeeMoreListener() = binding?.apply {
        if (petPrivateProfileFragmentDescription.text.length <= MAX_TEXT_CHARS) {
            petPrivateProfileFragmentSeeMore.isVisible = false
        } else {
            petPrivateProfileFragmentSeeMore.isVisible = true
            petPrivateProfileFragmentDescription.setOnClickListener {
                togLeTextView()
            }
            petPrivateProfileFragmentSeeMore.setOnClickListener {
                togLeTextView()
            }
        }
    }

    private var descriptionMinimized = false
    private fun togLeTextView() = binding?.apply {
        if (descriptionMinimized) {
            petPrivateProfileFragmentDescription.maxLines = MINIMIZED_MAX_LINES
            petPrivateProfileFragmentDescription.ellipsize = android.text.TextUtils.TruncateAt.END
            descriptionMinimized = false
            petPrivateProfileFragmentSeeMore.text = context?.getString(R.string.see_more)
        } else {
            petPrivateProfileFragmentDescription.maxLines = MAX_EXPANDED_LINES
            petPrivateProfileFragmentDescription.ellipsize = null
            descriptionMinimized = true
            petPrivateProfileFragmentSeeMore.text = context?.getString(R.string.see_less)
        }
    }

    private fun setDataCards(profile: PetProfile) = binding?.apply {
        petPrivateProfileFragmentAgeView.setAttributes(
            attrs = AttrsDataCardComponent(
                headerText = "Edad",
                description = profile.age ?: "S/E",
                cardStyle = BLUE
            )
        )
        petPrivateProfileFragmentBreedView.setAttributes(
            attrs = AttrsDataCardComponent(
                headerText = "Raza",
                description = profile.breed ?: "S/E",
                cardStyle = BLUE
            )
        )
        petPrivateProfileFragmentGenderView.setAttributes(
            attrs = AttrsDataCardComponent(
                headerText = profile.animal,
                description = profile.gender,
                cardStyle = BLUE
            )
        )
    }

    private fun setStickyBehaviour() = binding?.apply {
        petPrivateProfileFragmentNameSticky.visibility = View.INVISIBLE
        petPrivateProfileFragmentScrollView.setOnScrollChangeListener { _, _, _, _, _ ->
            val location = IntArray(SCROLL_ARRAY_SIZE)
            petPrivateProfileFragmentName.getLocationInWindow(location)
            val feedComponentY = location.last()

            val nestedScrollViewLocation = IntArray(SCROLL_ARRAY_SIZE)
            petPrivateProfileFragmentScrollView.getLocationInWindow(nestedScrollViewLocation)
            val nestedScrollViewY = nestedScrollViewLocation.last()

            val layoutParams =
                petPrivateProfileFragmentName.layoutParams as? ViewGroup.MarginLayoutParams

            if (feedComponentY <= nestedScrollViewY && viewModel?.uiStates()?.value is DisplayProfileUiState) {
                petPrivateProfileFragmentNameSticky.visibility = VISIBLE
            } else {
                petPrivateProfileFragmentNameSticky.visibility = INVISIBLE
            }

            layoutParams?.let { petPrivateProfileFragmentName.layoutParams = it }
        }
    }

    private fun showLoading() = binding?.apply {
        petPrivateProfileFragmentLoader.isVisible = true
        petPrivateProfileFragmentNameSticky.isVisible = false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding?.root?.removeAllViews()
        binding = null
    }

    companion object {
        private const val SCROLL_ARRAY_SIZE = 2
        const val MINIMIZED_MAX_LINES = 3
        const val MAX_EXPANDED_LINES = 100
        const val MAX_TEXT_CHARS = 120
    }
}