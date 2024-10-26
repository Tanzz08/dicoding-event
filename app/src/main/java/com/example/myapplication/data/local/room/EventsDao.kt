package com.example.myapplication.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.local.entity.EventsEntity
import com.example.myapplication.data.response.ListEventsItem

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(event: EventsEntity)

    @Update
    fun update(event: EventsEntity)

    @Delete
    fun delete(event: EventsEntity)

    @Query("SELECT * FROM events WHERE id = :id")
    fun getAllEvent(id: String): LiveData<EventsEntity>

    @Query("SELECT * FROM events")
    fun getAllEvents(): LiveData<List<EventsEntity>>

    @Query("SELECT * FROM events")
    fun getFavoriteEvents(): LiveData<List<EventsEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM events WHERE id = :eventId)")
    fun isFavorite(eventId: String): LiveData<Boolean>


}