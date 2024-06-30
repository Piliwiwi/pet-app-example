package com.example.uicomponents.ui.component.gradientcard

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.uicomponents.databinding.UiComponentGradientCardBinding
import com.squareup.picasso.Picasso

data class AttrsGradientCardComponent(
    val id: String? = null,
    val photo: Uri,
    val petName: String? = null,
    val onClick: (String) -> Unit = {}
)

class GradientCardComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: UiComponentGradientCardBinding? = null

    init {
        if (binding == null) {
            val inflate = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiComponentGradientCardBinding.inflate(inflate, this)
        }
    }

    fun setAttributes(attr: AttrsGradientCardComponent) {
        setText(attr)
        setPhoto(attr)
        setListener(attr)
    }

    private fun setText(attr: AttrsGradientCardComponent) = binding?.apply {
        gradientCardComponentPetName.isVisible = attr.petName != null
        gradientCardComponentPetName.text = attr.petName
    }

    private fun setPhoto(attr: AttrsGradientCardComponent) = binding?.apply {
        try {
            Picasso.get()
                .load(attr.photo)
                .into(gradientCardComponentImageView)
        } catch (e: Exception) {
            Log.e("GradientCardComponent", "Error al configurar la imagen: ${e.message}", e)
        }
    }

    private fun setListener(attr: AttrsGradientCardComponent) = binding?.apply {
        attr.id?.let {
            gradientCardComponentImageView.setOnClickListener { attr.onClick.invoke(attr.id) }
        }
    }
}
