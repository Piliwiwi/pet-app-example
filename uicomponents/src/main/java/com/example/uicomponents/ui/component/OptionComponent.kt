package com.example.uicomponents.ui.component

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import androidx.core.view.isVisible
import com.example.uicomponents.databinding.UiComponentOptionBinding

data class AttrsOptionComponent(
    val text: String,
    val data: Any,
    val onClick: () -> Unit = {},
    @DrawableRes val icon: Int? = null,
    val isActive: Boolean = true
)

class OptionComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var binding: UiComponentOptionBinding? = null

    init {
        if (binding == null) {
            val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiComponentOptionBinding.inflate(inflater, this, true)
        }
    }

    fun setAttribute(attrs: AttrsOptionComponent) {
        setTexts(attrs)
        setIcon(attrs)
        setActive(attrs)
    }

    private fun setActive(attrs: AttrsOptionComponent) = binding?.apply {
        if (!attrs.isActive) {
            componentOptionIcon.isVisible = false
            componentOptionTitle.isVisible = false
            componentOptionDivisor.isVisible = false
        }
    }

    private fun setTexts(attrs: AttrsOptionComponent) {
        binding?.apply {
            componentOptionTitle.text = attrs.text
        }
    }

    private fun setIcon(attrs: AttrsOptionComponent) = binding?.apply {
        if (attrs.icon != null) {
            componentOptionIcon.isVisible = true
            componentOptionIcon.setImageResource(attrs.icon)
        }
    }
}
