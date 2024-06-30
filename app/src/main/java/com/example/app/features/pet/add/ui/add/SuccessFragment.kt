package com.example.app.features.pet.add.ui.add

import android.app.Activity.RESULT_OK
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.app.R
import com.example.app.databinding.FragmentPetAddSuccessBinding
import com.example.uicomponents.ui.template.AttrsInfoTemplate

class SuccessFragment: Fragment() {
    private var binding: FragmentPetAddSuccessBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if (binding == null) binding =
            FragmentPetAddSuccessBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
    }

    private fun setupView() {
        binding?.apply {
            addFragmentSuccessTemplate.setAttributes(
                AttrsInfoTemplate(
                    headerText = getString(R.string.congratulations),
                    descriptionText = getString(R.string.your_pet_has_been_added),
                    buttonText = getString(R.string.finish),
                    buttonAction = {
                        context?.let {
                            activity?.setResult(RESULT_OK)
                            activity?.finish()
                        }
                    }
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}