package com.example.myapplication.data

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.myapplication.data.local.entity.EventsEntity
import com.example.myapplication.data.local.room.EventsDao
import com.example.myapplication.data.local.room.EventsDatabase
import com.example.myapplication.data.response.ListEventsItem
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


//    companion object {
//        @Volatile
//        private var instance: EventsRepository? = null
//        fun getInstance(
//            newsDao: EventsDao,
//        ): EventsRepository =
//            instance ?: synchronized(this) {
//                instance ?: EventsRepository(application = newsDao)
//            }.also { instance = it }
//    }

}