package com.example.uicomponents.ui.groupcomponent.petcardlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.data.PetCardResponse
import com.example.uicomponents.databinding.UiItemPetCardBinding
import com.example.uicomponents.ui.component.petcard.viewholder.PetCardViewHolder

class PetCardListAdapter(private val pets: List<PetCardResponse>, private val onClick: (String) -> Unit = {} ) :
    RecyclerView.Adapter<PetCardViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PetCardViewHolder(
        UiItemPetCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: PetCardViewHolder, position: Int) {
        holder.bind(pets[position], onClick)
    }

    override fun getItemCount() = pets.size
}