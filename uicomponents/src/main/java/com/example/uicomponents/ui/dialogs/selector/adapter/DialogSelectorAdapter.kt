package com.example.uicomponents.ui.dialogs.selector.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.databinding.UiOptionItemBinding
import com.example.uicomponents.ui.component.AttrsOptionComponent
import com.example.utils.extension.removeAccents

class DialogSelectorAdapter(
    private var listItems: List<AttrsOptionComponent>,
    private val onSelectItem: (String, Any) -> Unit
) : RecyclerView.Adapter<DialogSelectorViewHolder>() {
    private val fullList = listItems
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = DialogSelectorViewHolder(
        UiOptionItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: DialogSelectorViewHolder, position: Int) {
        holder.bind(listItems[position], onSelectItem)
    }

    override fun getItemCount(): Int = listItems.size

    @SuppressLint("NotifyDataSetChanged")
    fun filterList(filter: String) {
        listItems = fullList.filter { it.text.lowercase().removeAccents().contains(filter.lowercase()) }
        notifyDataSetChanged()
    }
}
