package com.example.myapplication.ui

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.repository.EventsRepository
import com.example.myapplication.data.local.entity.EventsEntity
import kotlinx.coroutines.launch

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
