package com.example.uicomponents.ui.groupcomponent.buttoncarousel

import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.databinding.UiComponentCategoryItemBinding

class ButtonCarouselComponentViewHolder(private val binding: UiComponentCategoryItemBinding) :
    RecyclerView.ViewHolder(binding.root) {
    fun bind(attrs: AttrsButtonCarouselComponent, position: Int) =
        binding.apply {
            val item = attrs.buttonList[position]
            categoryItem.setAttributes(item)
        }
}
