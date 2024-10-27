package com.example.myapplication.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.myapplication.datastore.SettingPreferences
import kotlinx.coroutines.launch

class ProfileViewModel(private val pref: SettingPreferences) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean>{
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean){
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
}