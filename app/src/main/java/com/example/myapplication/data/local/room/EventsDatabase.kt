package com.example.myapplication.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.data.local.entity.EventsEntity
import com.example.myapplication.data.retrofit.ApiService

@Database(entities = [EventsEntity::class], version = 1, exportSchema = false)
abstract class EventsDatabase : RoomDatabase() {
    abstract fun eventsDao(): EventsDao

    companion object {
        @Volatile
        private var INSTANCE: EventsDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): EventsDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EventsDatabase::class.java,
                    "events_database"  // Perbaikan nama database
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}