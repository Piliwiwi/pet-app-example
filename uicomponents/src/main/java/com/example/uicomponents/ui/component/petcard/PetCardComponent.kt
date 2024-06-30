package com.example.uicomponents.ui.component.petcard

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.uicomponents.R
import com.example.uicomponents.data.PetCardResponse
import com.example.uicomponents.databinding.UiComponentPetCardBinding
import com.squareup.picasso.Picasso

data class AttrsPetCardComponent(
    val component: PetCardResponse?
)

class PetCardComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: UiComponentPetCardBinding? = null

    init {
        if (binding == null) {
            val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiComponentPetCardBinding.inflate(inflater, this, true)
        }
    }

    fun setAttributes(attrs: AttrsPetCardComponent) {
        setTexts(attrs)
        setImage(attrs)
        setIcon(attrs)
    }

    private fun setTexts(attrs: AttrsPetCardComponent) = binding?.apply {
        petCardName.text = attrs.component?.name
        petCardAge.text = attrs.component?.age
        petCardAnimal.text = attrs.component?.animal?.name
        petCardDescription.text = attrs.component?.description
        petCardBreed.text = attrs.component?.breed?.name
    }

    private fun setImage(attrs: AttrsPetCardComponent) = binding?.apply {
        Picasso.get()
            .load(attrs.component?.image)
            .into(petCardPhoto)
    }

    private fun setIcon(attrs: AttrsPetCardComponent) = binding?.apply {
        val icon = when (attrs.component?.genre) {
            "M" -> R.drawable.ic_male
            "F" -> R.drawable.ic_female
            else -> R.drawable.ic_male
        }

        petCardIcGenre.setImageResource(icon)
    }
}