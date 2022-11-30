package com.ikrima.practice.dicoding.githubuserappdicoding.utils.preferencesdatastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.map

class SettingPreferencesDataStore private constructor(private val dataStore : DataStore<Preferences>){

    private val THEME_KEY = booleanPreferencesKey("theme_setting")

    fun getThemeSetting() : kotlinx.coroutines.flow.Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive : Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    companion object {
        @Volatile
        private var INSTANCE : SettingPreferencesDataStore? = null

        fun getInstance(dataStore : DataStore<Preferences>) : SettingPreferencesDataStore {
            return INSTANCE ?: synchronized(this) {
                val instance = SettingPreferencesDataStore(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}