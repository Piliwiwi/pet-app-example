package com.example.cache.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.example.cache.sharedpreferences.config.Constants.MODE
import com.example.cache.sharedpreferences.config.Constants.NAME

object DPSharedPreferencesFactory {

    fun makeSharedPreferences(context: Context?): SharedPreferences? =
        context?.getSharedPreferences(NAME, MODE)
}