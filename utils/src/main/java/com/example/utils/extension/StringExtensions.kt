package com.example.utils.extension

import java.text.Normalizer

fun String.removeAccents(): String {
    val normalizedText = Normalizer.normalize(this, Normalizer.Form.NFD)
    return normalizedText.replace("\\p{InCombiningDiacriticalMarks}+".toRegex(), "")
}