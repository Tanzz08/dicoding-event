package com.example.myapplication.data.di

import android.content.Context
import com.example.myapplication.data.EventsRepository
import com.example.myapplication.data.local.room.EventsDatabase
import com.example.myapplication.data.retrofit.ApiConfig
import com.example.myapplication.utils.AppExecutors

object Injection {
//    fun provideRepository(context: Context): EventsRepository{
//        val apiService = ApiConfig.getApiService()
//        val database = EventsDatabase.getDatabase(context)
//        val dao = database.eventsDao()
//        val appExecutors = AppExecutors()
//        return EventsRepository.getInstance(apiService, dao, appExecutors)
//    }
}