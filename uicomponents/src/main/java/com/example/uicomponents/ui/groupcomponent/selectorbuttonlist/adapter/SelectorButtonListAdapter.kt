package com.example.uicomponents.ui.groupcomponent.selectorbuttonlist.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.databinding.UiItemSelectorButtonBinding
import com.example.uicomponents.ui.component.buttons.AttrsCircleButtonComponent
import com.example.uicomponents.ui.groupcomponent.petcardlist.adapter.SelectorButtonViewHolder
import com.example.uicomponents.ui.groupcomponent.selectorbuttonlist.AttrsSelectorButtonListComponent

class SelectorButtonListAdapter(private var attrs: AttrsSelectorButtonListComponent) :
    RecyclerView.Adapter<SelectorButtonViewHolder>() {
    private var selectedOption: AttrsCircleButtonComponent? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SelectorButtonViewHolder(
        UiItemSelectorButtonBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun getItemCount() = attrs.buttons.size

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: SelectorButtonViewHolder, position: Int) {
        holder.bind(attrs, position) { key ->
            attrs.buttons.forEach { item ->
                item.isChecked = item.key == key
                if (item.key == key) {
                    selectedOption = item
                }
            }
            notifyDataSetChanged()
        }
    }
    fun getSelectedItem() = selectedOption

    fun setSelectedOption(key: String) {
        attrs.buttons.forEach { item ->
            item.isChecked = item.key == key
            if (item.key == key) {
                selectedOption = item
            }
        }
    }
}
