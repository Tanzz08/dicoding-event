package com.example.myapplication.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myapplication.data.response.EventResponse
import com.example.myapplication.data.response.ListEventsItem
import com.example.myapplication.data.retrofit.ApiConfig

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class finishedViewModel : ViewModel() {

    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
    val listevent: LiveData<List<ListEventsItem>> = _listEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object{
        private const val TAG = "MainViewModel"
    }

    init {
        findFinishedEvent()
    }

    private fun findFinishedEvent() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListEvent(active = 0)
        client.enqueue(object : Callback<EventResponse> {
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listEvent.value = response.body()?.listEvents
                }else{
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }

        })
    }
}