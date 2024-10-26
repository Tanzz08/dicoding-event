package com.example.myapplication.ui.favorite

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.EventsRepository

class FavoriteViewModel(application: Application) : ViewModel()  {

    private val mEventsRepository: EventsRepository = EventsRepository(application)

    fun getAllEvents() = mEventsRepository.getAllEvents()

    fun isEventFavorite(eventId: String): LiveData<Boolean> {
        return mEventsRepository.isEventFavorite(eventId)
    }

}