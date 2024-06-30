package com.example.utils.extension

import java.util.Calendar

fun Long.translateMillis(): String {
    val currentDate = Calendar.getInstance()
    val givenDate = Calendar.getInstance()
    givenDate.timeInMillis = this

    val ages = currentDate.get(Calendar.YEAR) - givenDate.get(Calendar.YEAR)
    val months = currentDate.get(Calendar.MONTH) - givenDate.get(Calendar.MONTH)

    val monthsLeft = if (months < 0) 12 + months else months

    return when {
        ages > 1 -> "$ages años"
        ages == 1 -> "1 año"
        monthsLeft > 1 -> "$monthsLeft meses"
        monthsLeft == 1 -> "1 mes"
        else -> "Recién nacido"
    }
}