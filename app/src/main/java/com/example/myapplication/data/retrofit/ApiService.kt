package com.example.myapplication.data.retrofit

import com.example.myapplication.data.response.EventResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/events")
    fun getListEvent(@Query("active") active: Int) : Call<EventResponse>

    @GET("/events")
    fun getclosestEvent(@Query("active") active: Int = -1, @Query ("limit") limit: Int = 1) : Call<EventResponse>


}