package com.example.myapplication.ui

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.EventsRepository
import com.example.myapplication.data.local.entity.EventsEntity
import com.example.myapplication.data.response.DetailResponse
import com.example.myapplication.data.response.Event
import com.example.myapplication.data.response.ListEventsItem
import com.example.myapplication.data.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application): ViewModel() {


    private val mEventsRepository: EventsRepository = EventsRepository(application)

    fun insert(event: EventsEntity) {
        viewModelScope.launch {
            mEventsRepository.insert(event)
        }
    }

    fun delete(event: EventsEntity) {
        viewModelScope.launch {
            mEventsRepository.delete(event)
        }
    }

    fun isEventFavorite(eventId: String): LiveData<Boolean>  {
        return mEventsRepository.isEventFavorite(eventId)
    }
}
