package com.example.uicomponents.ui.component.categoryheader

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.uicomponents.databinding.UiComponentCategoryHeaderBinding

data class AttrsCategoryHeaderComponent(
    val header: String
)

class CategoryHeaderComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: UiComponentCategoryHeaderBinding? = null

    init {
        if (binding == null) {
            val inflater =
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiComponentCategoryHeaderBinding.inflate(inflater, this)
        }
    }

    fun setAttributes(attrs: AttrsCategoryHeaderComponent) {
        setText(attrs)
    }

    private fun setText(attrs: AttrsCategoryHeaderComponent) =
        binding?.categoryHeaderComponentTitle?.apply {
            this.text = attrs.header
        }
}
