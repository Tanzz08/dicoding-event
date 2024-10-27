package com.example.myapplication.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.myapplication.data.local.entity.EventsEntity

@Dao
interface EventsDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(event: EventsEntity)

    @Update
    fun update(event: EventsEntity)

    @Delete
    fun delete(event: EventsEntity)

    @Query("SELECT * FROM events_table WHERE id = :id")
    fun getAllEvent(id: String): LiveData<EventsEntity>

    @Query("SELECT * FROM events_table")
    fun getAllEvents(): LiveData<List<EventsEntity>>

    @Query("SELECT * FROM events_table")
    fun getFavoriteEvents(): LiveData<List<EventsEntity>>

    @Query("SELECT EXISTS(SELECT 1 FROM events_table WHERE id = :eventId)")
    fun isFavorite(eventId: String): LiveData<Boolean>


}