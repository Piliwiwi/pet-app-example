package com.example.uicomponents.ui.dialogs.selector.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.databinding.UiOptionItemBinding
import com.example.uicomponents.ui.component.AttrsOptionComponent

class DialogSelectorViewHolder(
    private val binding: UiOptionItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(attrs: AttrsOptionComponent, onSelectItem: (String, Any) -> Unit) = binding.apply {
        optionComponent.setAttribute(
            attrs = attrs
        )
        setListeners(attrs = attrs, onSelectItem = onSelectItem)
    }

    private fun setListeners(attrs: AttrsOptionComponent, onSelectItem: (String, Any) -> Unit) {
        binding.optionComponent.setOnClickListener {
            onSelectItem.invoke(attrs.text, attrs.data)
        }
    }
}
