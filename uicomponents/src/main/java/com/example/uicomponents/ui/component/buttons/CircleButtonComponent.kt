package com.example.uicomponents.ui.component.buttons

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import com.example.uicomponents.R
import com.example.uicomponents.databinding.UiComponentCircleButtonBinding

data class AttrsCircleButtonComponent(
    val icon: Int,
    var isChecked: Boolean,
    val key: String
)

class CircleButtonComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {
    private var binding: UiComponentCircleButtonBinding? = null

    init {
        if (binding == null) {
            val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiComponentCircleButtonBinding.inflate(inflater, this, true)
        }
    }

    fun setAttributes(attrs: AttrsCircleButtonComponent) {
        setIcon(attrs)
        setBehavior(attrs)
    }

    fun setButtonListener(event: () -> Unit) = binding?.circleButtonComponentRadioButton?.setOnClickListener {
        event()
    }

    private fun setIcon(attrs: AttrsCircleButtonComponent) = binding?.apply {
        circleButtonComponentIcon.setImageResource(attrs.icon)
    }

    private fun setBehavior(attrs: AttrsCircleButtonComponent) = binding?.apply {
        circleButtonComponentRadioButton.isChecked = attrs.isChecked
        if (attrs.isChecked) setIconColor(color = R.color.ui_white)
        else setIconColor(color = R.color.ui_brand_primary)
    }

    private fun setIconColor(color: Int) = binding?.apply {
        val iconColor = ContextCompat.getColor(context, color)
        ImageViewCompat.setImageTintList(
            circleButtonComponentIcon,
            ColorStateList.valueOf(iconColor)
        )
    }
}
