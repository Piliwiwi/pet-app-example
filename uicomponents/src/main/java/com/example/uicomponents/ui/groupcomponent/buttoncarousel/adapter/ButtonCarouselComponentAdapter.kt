package com.example.uicomponents.ui.groupcomponent.buttoncarousel.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.databinding.UiComponentCategoryItemBinding
import com.example.uicomponents.ui.groupcomponent.buttoncarousel.AttrsButtonCarouselComponent
import com.example.uicomponents.ui.groupcomponent.buttoncarousel.ButtonCarouselComponentViewHolder

class ButtonCarouselComponentAdapter(
    private val attrs: AttrsButtonCarouselComponent
) : RecyclerView.Adapter<ButtonCarouselComponentViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ButtonCarouselComponentViewHolder = ButtonCarouselComponentViewHolder(
        UiComponentCategoryItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ButtonCarouselComponentViewHolder, position: Int) {
        holder.bind(attrs, position = position)
    }

    override fun getItemCount(): Int = attrs.buttonList.size
}
