package com.example.myapplication.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.datastore.SettingPreferences
import com.example.myapplication.ui.profile.ProfileViewModel


class ProfileViewModelFactory(private val pref: SettingPreferences):
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            return ProfileViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}