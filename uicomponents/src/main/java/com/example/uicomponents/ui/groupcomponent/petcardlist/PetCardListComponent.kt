package com.example.uicomponents.ui.groupcomponent.petcardlist

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.uicomponents.data.PetCardResponse
import com.example.uicomponents.databinding.UiGroupcomponentPetCardBinding
import com.example.uicomponents.ui.groupcomponent.petcardlist.adapter.PetCardListAdapter

data class AttrsPetCardListComponent(
    val pets: List<PetCardResponse>,
    val onClick: (String) -> Unit = {}
)

class PetCardListComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private var binding: UiGroupcomponentPetCardBinding? = null

    init {
        if (binding == null) {
            val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiGroupcomponentPetCardBinding.inflate(inflater, this)
        }
    }

    fun setAttributes(attrs: AttrsPetCardListComponent) {
        setAdapter(attrs)
    }

    private fun setAdapter(attrs: AttrsPetCardListComponent) = binding?.apply {
        petCardListRecyclerView.adapter = PetCardListAdapter(attrs.pets, attrs.onClick)
    }
}