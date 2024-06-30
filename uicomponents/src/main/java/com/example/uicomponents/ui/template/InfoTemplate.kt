package com.example.uicomponents.ui.template

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.airbnb.lottie.LottieDrawable.INFINITE
import com.example.uicomponents.R
import com.example.uicomponents.databinding.UiTemplateInfoBinding
import com.example.uicomponents.ui.component.buttons.AttrsTitledButton
import com.example.utils.extension.onJsonAnimationEnd

data class AttrsInfoTemplate(
    val headerText: String,
    val descriptionText: String,
    val loop: Boolean = false,
    val buttonAction: () -> Unit,
    val buttonText: String,
    val image: Int? = null
)

class InfoTemplate @JvmOverloads constructor(
    context: Context,
    private val attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: UiTemplateInfoBinding? = null

    init {
        if (binding == null) {
            val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiTemplateInfoBinding.inflate(inflater, this)
        }

        initAttributes()
    }

    private fun initAttributes() {
        val styledAttributes = context.obtainStyledAttributes(attrs, R.styleable.InfoTemplate)
        val loader = styledAttributes.getInt(R.styleable.InfoTemplate_ui_lottie_type, WARNING_RED)

        try {
            binding?.infoTemplateIcon?.apply {
                setFailureListener { Log.e("ANIMATION ERROR", it.toString()) }
                setAnimation(
                    "animations/${
                        when (loader) {
                            WARNING_RED -> "warning_red.json"
                            EMPTY_SEARCH -> "empty_search.json"
                            SUCCESS_GREEN -> "success_animation.json"
                            else -> "warning_red.json"
                        }
                    }"
                )
            }
        } finally {
            styledAttributes.recycle()
        }
    }

    fun setAttributes(attrs: AttrsInfoTemplate) {
        reproduceAnimation(attrs)
        setTexts(attrs)
        setImage(attrs)
    }

    private fun reproduceAnimation(attrs: AttrsInfoTemplate) = binding?.infoTemplateIcon?.apply {
        playAnimation()
        onJsonAnimationEnd {
            if (attrs.loop) {
                setMinFrame(MIN_FRAME_LOOP)
                repeatCount = INFINITE
                playAnimation()
            }
        }
    }

    private fun setTexts(attrs: AttrsInfoTemplate) = binding?.apply {
        infoTemplateTitle.text = attrs.headerText
        infoTemplateDescription.text = attrs.descriptionText
        infoTemplateButton.setAttributes(
            attrs = AttrsTitledButton(
                buttonText = attrs.buttonText,
                onClick = attrs.buttonAction
            )
        )
    }

    private fun setImage(attrs: AttrsInfoTemplate) = binding?.apply {
        attrs.image?.let {
            infoTemplateImage.isVisible = true
            infoTemplateIcon.isVisible = false
            infoTemplateImage.setImageResource(it)
        }
    }

    companion object {
        const val WARNING_RED = 1
        const val EMPTY_SEARCH = 2
        const val SUCCESS_GREEN = 3
        const val MIN_FRAME_LOOP = 150
    }
}
