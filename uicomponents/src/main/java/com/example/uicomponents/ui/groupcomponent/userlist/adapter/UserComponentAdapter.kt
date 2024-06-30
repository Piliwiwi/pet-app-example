package com.example.uicomponents.ui.groupcomponent.userlist.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.uicomponents.databinding.UiComponentUserItemBinding
import com.example.uicomponents.ui.groupcomponent.userlist.AttrsUserListComponent

class UserComponentAdapter(private var attrs: AttrsUserListComponent) :
    RecyclerView.Adapter<UserComponentViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = UserComponentViewHolder(
        UiComponentUserItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: UserComponentViewHolder, position: Int) {
        holder.bind(attrs, position)
    }

    override fun getItemCount() = attrs.users.size

}