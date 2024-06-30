package com.example.uicomponents.ui.groupcomponent.buttoncarousel

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.uicomponents.databinding.UiGroupComponentButtonCarouselBinding
import com.example.uicomponents.ui.component.buttons.AttrsCategoryButton
import com.example.uicomponents.ui.groupcomponent.buttoncarousel.adapter.ButtonCarouselComponentAdapter

data class AttrsButtonCarouselComponent(
    val buttonList: List<AttrsCategoryButton>
)

class ButtonCarouselComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: UiGroupComponentButtonCarouselBinding? = null

    init {
        if (binding == null) {
            val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiGroupComponentButtonCarouselBinding.inflate(inflater, this)
        }
    }

    fun setAttributes(attrs: AttrsButtonCarouselComponent) {
        setAdapter(attrs)
    }

    private fun setAdapter(attrs: AttrsButtonCarouselComponent) = binding?.apply {
        buttonCarouselGroupComponentRecyclerView.adapter = ButtonCarouselComponentAdapter(attrs)
    }
}
