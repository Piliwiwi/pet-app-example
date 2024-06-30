package com.example.uicomponents.ui.component

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.DrawableRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.uicomponents.databinding.UiComponentPetProfileResumeBinding
import com.squareup.picasso.Picasso

data class AttrsResumePetProfileComponent(
    val photo: Uri,
    val animalType: String,
    val name: String,
    val gender: String,
    val onClick: (() -> Unit)? = null,
    @DrawableRes val icon: Int? = null
)

class ResumePetProfileComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: UiComponentPetProfileResumeBinding? = null

    init {
        if (binding == null) {
            val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiComponentPetProfileResumeBinding.inflate(inflater, this)
        }
    }

    fun setAttributes(attrs: AttrsResumePetProfileComponent) {
        setTexts(attrs)
        setImage(attrs.photo)
        setListener(attrs.onClick)
        setIcon(attrs)
    }

    fun updateResume(attrs: AttrsResumePetProfileComponent) {
        setImage(attrs.photo)
        setTexts(attrs)
    }

    private fun setTexts(attrs: AttrsResumePetProfileComponent) = binding?.apply {
        resumeComponentAnimalTypeText.text = attrs.animalType
        resumeComponentPetNameText.text = attrs.name
        resumeComponentGenderText.text = attrs.gender
    }

    private fun setImage(image: Uri) = binding?.apply {
        Picasso.get()
            .load(image)
            .into(resumeComponentImageview)
    }

    private fun setListener(action: (() -> Unit)?) = binding?.apply {
        action?.let {
            resumeComponentImageview.setOnClickListener { it() }
            resumeComponentView.setOnClickListener { it() }
        }
    }

    private fun setIcon(attrs: AttrsResumePetProfileComponent) = binding?.apply {
        if (attrs.icon != null) {
            resumeComponentIcon.setImageResource(attrs.icon)
        }
    }
}
