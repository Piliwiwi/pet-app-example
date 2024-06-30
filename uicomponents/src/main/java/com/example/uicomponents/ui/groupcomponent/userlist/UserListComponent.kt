package com.example.uicomponents.ui.groupcomponent.userlist

import android.content.Context
import android.content.Context.LAYOUT_INFLATER_SERVICE
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.uicomponents.databinding.UiGroupComponentUserListBinding
import com.example.uicomponents.ui.component.AttrsUserComponent
import com.example.uicomponents.ui.groupcomponent.userlist.adapter.UserComponentAdapter

data class AttrsUserListComponent(
    val users: List<AttrsUserComponent>
)

class UserListComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {
    private var binding: UiGroupComponentUserListBinding? = null

    init {
        if (binding == null) {
            val inflater = context.getSystemService(LAYOUT_INFLATER_SERVICE) as LayoutInflater
            binding = UiGroupComponentUserListBinding.inflate(inflater, this)
        }
    }

    fun setAttributes(attrs: AttrsUserListComponent) {
        setAdapter(attrs)
    }

    private fun setAdapter(attrs: AttrsUserListComponent) = binding?.apply {
        userGroupComponentRecylerView.adapter = UserComponentAdapter(attrs)
    }
}
