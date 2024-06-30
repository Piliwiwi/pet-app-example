package com.example.utils.extension

import android.content.Context
import android.net.Uri
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody

fun Uri.uriToMultipartBodyPart(context: Context): MultipartBody.Part? {
    return try {
        val file = File(path.orEmpty())
        val requestFile = file.asRequestBody(context.contentResolver.getType(this)?.toMediaTypeOrNull())
        MultipartBody.Part.createFormData("archivo", file.name, requestFile)
    } catch (_: Exception) {
        null
    }
}