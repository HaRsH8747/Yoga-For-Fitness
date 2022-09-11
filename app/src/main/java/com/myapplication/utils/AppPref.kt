package com.myapplication.utils

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences

class AppPref(context: Context) {
    fun getInt(key: String?): Int {
        return appSharedPref.getInt(key, 0)
    }

    fun setInt(key: String?, value: Int) {
        prefEditor.putInt(key, value).apply()
    }

    fun clearInt(key: String?) {
        setInt(key, 0)
    }

    fun getString(key: String?): String? {
        return appSharedPref.getString(key, "")
    }

    fun setString(key: String?, value: String?) {
        prefEditor.putString(key, value).apply()
    }

    fun getBoolean(key: String): Boolean {
        return appSharedPref.getBoolean(key, false)
    }

    fun setBoolean(key: String?, value: Boolean) {
        prefEditor.putBoolean(key, value).apply()
    }

    fun clearString(key: String?) {
        setString(key, "")
    }

    companion object {
        const val YOGA_FOR_FITNESS_DATA = "YOGA_FOR_FITNESS_DATA"
        lateinit var appSharedPref: SharedPreferences
        lateinit var prefEditor: SharedPreferences.Editor
        const val FAVOURITE_YOGA = "FAVOURITE_YOGA"
    }

    init {
        appSharedPref = context.getSharedPreferences(YOGA_FOR_FITNESS_DATA, Activity.MODE_PRIVATE)
        prefEditor = appSharedPref.edit()
    }
}
