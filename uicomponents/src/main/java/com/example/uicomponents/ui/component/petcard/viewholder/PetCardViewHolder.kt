package com.example.uicomponents.ui.component.petcard.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.data.PetCardResponse
import com.example.uicomponents.databinding.UiItemPetCardBinding
import com.example.uicomponents.ui.component.petcard.AttrsPetCardComponent

class PetCardViewHolder(
    private val binding: UiItemPetCardBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(petCard: PetCardResponse, onClick: (String) -> Unit) = binding.apply {
        itemPetCard.setAttributes(AttrsPetCardComponent(petCard))
        petCard.id?.let { setListeners(it, onClick) }
    }

    private fun setListeners(petId: String, onClick: (String) -> Unit) = binding.apply {
        itemPetCard.setOnClickListener {
            onClick.invoke(petId)
        }
    }
}