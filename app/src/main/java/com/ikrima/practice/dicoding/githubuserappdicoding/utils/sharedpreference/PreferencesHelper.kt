package com.ikrima.practice.dicoding.githubuserappdicoding.utils.sharedpreference

import android.content.Context
import android.content.SharedPreferences


class PreferencesHelper(context: Context) {

    private val sharedPreferenceName = "sharedpreferencegithubuserdicoding"
    private val sharedPref : SharedPreferences = context.getSharedPreferences(sharedPreferenceName, Context.MODE_PRIVATE)
    private val editor : SharedPreferences.Editor = sharedPref.edit()

    fun putValueString(key: String, value: String) {
        editor.putString(key, value).apply()
    }

    fun getValueString(key: String) : String? {
        return sharedPref.getString(key, null)
    }

}