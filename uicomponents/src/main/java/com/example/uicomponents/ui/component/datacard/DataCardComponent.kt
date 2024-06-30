package com.example.uicomponents.ui.component.datacard

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.uicomponents.R
import com.example.uicomponents.databinding.UiComponentDataCardBinding
import com.example.uicomponents.ui.component.datacard.CardStyle.BLUE
import com.example.uicomponents.ui.component.datacard.CardStyle.GREEN
import com.example.uicomponents.ui.component.datacard.CardStyle.RED
import com.example.uicomponents.ui.component.datacard.CardStyle.YELLOW

data class AttrsDataCardComponent(
    val headerText: String,
    val description: String,
    val cardStyle: CardStyle
)

enum class CardStyle {
    RED, YELLOW, GREEN, BLUE
}

class DataCardComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: UiComponentDataCardBinding? = null

    init {
        if (binding == null) {
            val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiComponentDataCardBinding.inflate(inflater, this)
        }
    }

    fun setAttributes(attrs: AttrsDataCardComponent) {
        setTexts(attrs)
        setCardColor(attrs)
    }

    private fun setTexts(attrs: AttrsDataCardComponent) = binding?.apply {
        dataCardComponentHeader.text = attrs.headerText
        petPrivateProfileFragmentAgeTextDescription.text = attrs.description
    }

    private fun setCardColor(attrs: AttrsDataCardComponent) =
        binding?.dataCardComponentCardView?.apply {
            when (attrs.cardStyle) {
                RED -> this.setBackgroundResource(R.drawable.ui_rounded_corners_rectangle_red)
                GREEN -> this.setBackgroundResource(R.drawable.ui_rounded_corners_rectangle_green)
                YELLOW -> this.setBackgroundResource(R.drawable.ui_rounded_corners_rectangle_yellow)
                BLUE -> this.setBackgroundResource(R.drawable.ui_rounded_corners_rectangle)
            }
        }
}
