package com.example.uicomponents.ui.groupcomponent.selectorbuttonlist

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.uicomponents.databinding.UiGroupComponentSelectorButtonListBinding
import com.example.uicomponents.ui.component.buttons.AttrsCircleButtonComponent
import com.example.uicomponents.ui.groupcomponent.selectorbuttonlist.adapter.SelectorButtonListAdapter

data class AttrsSelectorButtonListComponent(
    val buttons: List<AttrsCircleButtonComponent>,
    val onSelectEvent: (String) -> Unit = {}
)

class SelectorButtonListComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var binding: UiGroupComponentSelectorButtonListBinding? = null

    init {
        if (binding == null) {
            val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiGroupComponentSelectorButtonListBinding.inflate(inflater, this)
        }
    }

    fun setAttributes(attrs: AttrsSelectorButtonListComponent) {
        setAdapter(attrs)
    }

    fun setSelectedOption(key: String) {
        (binding?.selectorButtonListComponentRecyclerview?.adapter as? SelectorButtonListAdapter)
            ?.setSelectedOption(key)
    }

    fun getSelectedOption(): AttrsCircleButtonComponent? =
        (binding?.selectorButtonListComponentRecyclerview?.adapter as? SelectorButtonListAdapter)
            ?.getSelectedItem()

    private fun setAdapter(attrs: AttrsSelectorButtonListComponent) = binding?.apply {
        selectorButtonListComponentRecyclerview.adapter = SelectorButtonListAdapter(attrs)
    }
}
