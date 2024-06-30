package com.example.uicomponents.ui.groupcomponent.userlist.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.databinding.UiComponentUserItemBinding
import com.example.uicomponents.ui.groupcomponent.userlist.AttrsUserListComponent

class UserComponentViewHolder(private val binding: UiComponentUserItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(attrs: AttrsUserListComponent, position: Int) =
        binding.apply {
            val item = attrs.users[position]
            userComponentItem.setAttributes(item)
        }
}
