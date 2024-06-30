package com.example.uicomponents.ui.loadscreen

import android.content.Context
import android.os.Parcelable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.Keep
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.uicomponents.databinding.UiTemplateLoadScreenBinding
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class AttrsLoadScreen(
    @ColorRes val backgroundColor: Int,
    @DrawableRes val icon: Int,
    val animationDuration: Long? = null,
    val onLoadEnd: () -> Unit
) : Parcelable

class LoadScreenTemplate @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttrs: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttrs) {
    private var binding: UiTemplateLoadScreenBinding? = null

    var activityEvent: () -> Unit = {}

    init {
        if (binding == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiTemplateLoadScreenBinding.inflate(inflater, this)
        }
    }

    fun setAttributes(attrs: AttrsLoadScreen) = binding?.apply {
        layout.setBackgroundColor(resources.getColor(attrs.backgroundColor))
        icon.setImageResource(attrs.icon)
        setIconAnimation(attrs)
    }

    private fun setIconAnimation(attrs: AttrsLoadScreen) = binding?.apply {
        icon.alpha = 0f
        icon
            .animate()
            .setDuration(attrs.animationDuration ?: APPEARANCE_DURATION)
            .alpha(1f)
            .withEndAction {
                attrs.onLoadEnd()
                activityEvent()
            }
    }

    companion object {
        const val APPEARANCE_DURATION = 200L
    }
}