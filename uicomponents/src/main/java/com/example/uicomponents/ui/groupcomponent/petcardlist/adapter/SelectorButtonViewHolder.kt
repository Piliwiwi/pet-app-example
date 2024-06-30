package com.example.uicomponents.ui.groupcomponent.petcardlist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.databinding.UiItemSelectorButtonBinding
import com.example.uicomponents.ui.component.buttons.AttrsCircleButtonComponent
import com.example.uicomponents.ui.groupcomponent.selectorbuttonlist.AttrsSelectorButtonListComponent

class SelectorButtonViewHolder(private val binding: UiItemSelectorButtonBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(attrs: AttrsSelectorButtonListComponent, position: Int, onClick: (String) -> Unit) =
        binding.apply {
            val item = attrs.buttons[position]
            itemSelectorButton.setAttributes(item)
            setEvents(attrs, item, onClick)
        }

    private fun setEvents(
        attrs: AttrsSelectorButtonListComponent,
        button: AttrsCircleButtonComponent,
        onClick: (String) -> Unit
    ) = binding.apply {
        itemSelectorButton.setButtonListener {
            attrs.onSelectEvent(button.key)
            onClick(button.key)
        }
    }
}
