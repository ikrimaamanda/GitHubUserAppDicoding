package com.ikrima.practice.dicoding.githubuserappdicoding.utils.sharedpreference

import android.content.Context
import android.content.SharedPreferences


class PreferencesHelper(context: Context) {

    private val SharedPreferenceName = "sharedpreferencegithubuserdicoding"
    private val sharedPref : SharedPreferences = context.getSharedPreferences(SharedPreferenceName, Context.MODE_PRIVATE)
    private val editor : SharedPreferences.Editor = sharedPref.edit()

    fun putValueBoolean(key : String, value : Boolean) {
        editor.putBoolean(key, value).apply()
    }

    fun getValueBoolean(key: String) : Boolean {
        return sharedPref.getBoolean(key, true)
    }

    fun putValueString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun getValueString(key: String) : String? {
        return sharedPref.getString(key, null)
    }

    fun putValueInt(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    fun getValueInt(key: String) : Int {
        return sharedPref.getInt(key, 0)
    }

    fun clear() {
        editor.clear().apply()
    }
}