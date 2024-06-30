package com.example.cache.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.example.cache.sharedpreferences.DPSharedPreferencesFactory.makeSharedPreferences

class DPSharedPreferences(
    private val context: Context?,
    val sharedPreferences: SharedPreferences? = makeSharedPreferences(context)
) {
    fun putData(key: String, data: Boolean) {
        sharedPreferences?.edit()?.putBoolean(key, data)?.apply()
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean? =
        sharedPreferences?.getBoolean(key, defaultValue)
}