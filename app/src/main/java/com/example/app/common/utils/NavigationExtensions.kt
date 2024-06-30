package com.example.app.common.utils

import android.view.View
import androidx.navigation.NavDirections
import androidx.navigation.findNavController

fun safeNavigation(view: View, direction: NavDirections) {
    view.findNavController().currentDestination?.getAction(direction.actionId)
        ?.let {
            view.findNavController().navigate(direction)
        }
}