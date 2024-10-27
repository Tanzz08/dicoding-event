package com.example.myapplication.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


val Context.datastore: DataStore<Preferences> by preferencesDataStore(name = "Settings")

class SettingPreferences private constructor(private val dataStore: DataStore<Preferences>){

    private val theme = booleanPreferencesKey("theme_key")

    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preference ->
            preference[theme] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkModeActive: Boolean){
        dataStore.edit { preference ->
            preference[theme] = isDarkModeActive
        }
    }

    companion object{
        @Volatile
        private var INSTANCE: SettingPreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): SettingPreferences{
            return INSTANCE ?: synchronized(this){
                val instance = SettingPreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }
}