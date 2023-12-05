package com.mmrbd.barikoitask.utils

import android.content.SharedPreferences

class SharePrefUtil(
    private val sharedPref: SharedPreferences
) {
    //To Store String data
    fun save(key: String, text: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(key, text)
        editor.apply()
    }

    //To Store Int data
    fun save(key: String, value: Int) {
        AppLogger.log("sharePrefUtil:: save Int")
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    //To Store Floating data
    fun save(key: String, value: Float) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putFloat(key, value)
        editor.apply()
    }

    //To Store Boolean data
    fun save(key: String, status: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(key, status)
        editor.apply()
    }

    //To Retrieve Int
    fun getValueInt(key: String): Int {
        return sharedPref.getInt(key, 0)
    }

    // To Retrieve Boolean
    fun getValueBoolean(key: String, defaultValue: Boolean): Boolean {
        return sharedPref.getBoolean(key, defaultValue)
    }

    //To Retrieve String
    fun getValueString(key: String): String? {
        return sharedPref.getString(key, "")
    }

    fun getValueFloat(key: String): Float {
        return sharedPref.getFloat(key, 0.0f)
    }

    // To clear all data
    fun clearSharedPreference() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.clear()
        editor.apply()
    }

    // To remove a specific data
    fun removeValue(key: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(key)
        editor.apply()
    }
}