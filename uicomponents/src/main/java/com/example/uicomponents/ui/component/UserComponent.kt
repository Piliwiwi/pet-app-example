package com.example.uicomponents.ui.component

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.net.Uri
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.uicomponents.databinding.UiComponentUserBinding
import com.squareup.picasso.Picasso

data class AttrsUserComponent(
    val name: String,
    val location: String,
    val photo: Uri,
    val deleteAction: () -> Unit
)

class UserComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: UiComponentUserBinding? = null

    init {
        if (binding == null) {
            val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiComponentUserBinding.inflate(inflater, this)
        }
    }

    fun setAttributes(attrs: AttrsUserComponent) {
        setTexts(attrs)
        setImage(attrs)
    }

    private fun setTexts(attrs: AttrsUserComponent) = binding?.apply {
        userComponentUserName.text = attrs.name
        userComponentLocation.text = attrs.location
    }

    private fun setImage(attrs: AttrsUserComponent) = binding?.apply {
        try {
            Picasso.get()
                .load(attrs.photo)
                .into(userComponentImageView)
        } catch (e: Exception) {
            Log.e("UserComponent", "Error al configurar la imagen: ${e.message}", e)
        }
    }
}
