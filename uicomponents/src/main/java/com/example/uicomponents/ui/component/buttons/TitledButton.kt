package com.example.uicomponents.ui.component.buttons

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.uicomponents.R
import com.example.uicomponents.databinding.UiComponentTitledButtonBinding

data class AttrsTitledButton(
    val title: String = "",
    val buttonText: String,
    val onClick: () -> Unit
)

class TitledButton @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: UiComponentTitledButtonBinding? = null

    init {
        if (binding == null) {
            val inflater =
                context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiComponentTitledButtonBinding.inflate(inflater, this)
        }

        initAttrs()
    }

    private fun initAttrs() {
        val styledAttrs = context.obtainStyledAttributes(attrs, R.styleable.TitledButton)

        try {
            when (styledAttrs.getInt(R.styleable.TitledButton_ui_type, PRIMARY)) {
                PRIMARY -> setPrimaryButton()
                SECONDARY -> setSecondaryButton()
                else -> setPrimaryButton()
            }
        } finally {
            styledAttrs.recycle()
        }
    }

    private fun setPrimaryButton() = binding?.apply {
        button.setBackgroundColor(resources.getColor(R.color.ui_brand_primary))
    }

    private fun setSecondaryButton() = binding?.apply {
        button.setBackgroundColor(Color.WHITE)
        button.setTextColor(resources.getColor(R.color.ui_brand_primary))
        button.setStrokeColorResource(R.color.ui_brand_primary)
        button.setStrokeWidthResource(R.dimen.ui_size_1)
        button.typeface = Typeface.DEFAULT
    }

    fun setAttributes(attrs: AttrsTitledButton) {
        setTitle(attrs.title)
        setButton(attrs)
    }

    private fun setTitle(text: String) = binding?.apply {
        if (text.isNotEmpty()) {
            title.isVisible = true
            title.text = text
        } else title.isVisible = false
    }

    private fun setButton(attrs: AttrsTitledButton) = binding?.apply {
        button.text = attrs.buttonText
        button.setOnClickListener { attrs.onClick() }
    }

    companion object {
        const val PRIMARY = 1
        const val SECONDARY = 2
    }
}