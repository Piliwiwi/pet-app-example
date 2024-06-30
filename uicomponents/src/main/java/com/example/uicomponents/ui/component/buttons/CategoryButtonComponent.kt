package com.example.uicomponents.ui.component.buttons

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.uicomponents.databinding.UiComponentCategoryBinding

data class AttrsCategoryButton(
    val text: String,
    val icon: Int,
    val buttonAction: () -> Unit
)

class CategoryButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: UiComponentCategoryBinding? = null

    init {
        if (binding == null) {
            val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiComponentCategoryBinding.inflate(inflater, this)
        }
    }

    fun setAttributes(attrs: AttrsCategoryButton) {
        setIcon(attrs)
        setText(attrs)
        setListener(attrs)
    }

    private fun setIcon(attrs: AttrsCategoryButton) = binding?.apply {
        categoryComponentIcon.setImageResource(attrs.icon)
    }

    private fun setText(attrs: AttrsCategoryButton) = binding?.apply {
        categoryComponentItemText.text = attrs.text
    }

    private fun setListener(attrs: AttrsCategoryButton) = binding?.apply {
        categoryComponentView.setOnClickListener {
            attrs.buttonAction.invoke()
        }
    }
}
