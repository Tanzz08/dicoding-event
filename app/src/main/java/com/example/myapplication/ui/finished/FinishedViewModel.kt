package com.example.myapplication.ui.finished

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

class FinishedViewModel : ViewModel() {

    private val _listEvent = MutableLiveData<List<ListEventsItem>>()
    val listevent: LiveData<List<ListEventsItem>> = _listEvent

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    companion object{
        private const val TAG = "FinishedViewModel"
    }

    init {
        findEventsFinished()
    }

    private fun findEventsFinished() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getListEvent(active = 0)
        client.enqueue(object : Callback<EventResponse>{
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _listEvent.value = response.body()?.listEvents
                }else{
                    val errorMessage = "Error: ${response.message()} (code ${response.code()})"
                    _errorMessage.value = errorMessage
                    Log.e(TAG, errorMessage)
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                _isLoading.value = false
                val errorMessage = "Network Failure: ${t.message.toString()}"
                _errorMessage.value = errorMessage
                Log.e(TAG, errorMessage)
            }

        })
    }
}