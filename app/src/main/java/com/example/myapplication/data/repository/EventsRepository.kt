package com.example.myapplication.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myapplication.data.local.entity.EventsEntity
import com.example.myapplication.data.local.room.EventsDao
import com.example.myapplication.data.local.room.EventsDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class EventsRepository(application: Application){

    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()
    private val eventsDao: EventsDao

    init {
        val db = EventsDatabase.getDatabase(application)
        eventsDao = db.eventsDao()

    }

    fun insert(event: EventsEntity) {
        executorService.execute { eventsDao.insert(event) }
    }

    fun delete(event: EventsEntity) {
        executorService.execute { eventsDao.delete(event) }
    }


    fun getAllEvents(): LiveData<List<EventsEntity>> = eventsDao.getAllEvents()

    fun isEventFavorite(eventId: String): LiveData<Boolean> {
        return eventsDao.isFavorite(eventId)
    }


}