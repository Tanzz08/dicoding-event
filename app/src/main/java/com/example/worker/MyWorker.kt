package com.example.worker

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myapplication.R
import com.example.myapplication.data.response.EventResponse
import com.example.myapplication.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyWorker(context: Context, workerPrams: WorkerParameters) : Worker(context, workerPrams) {

    companion object{

        const val NOTIFICATION_ID = 1
        const val CHANNEL_ID = "channel_01"
        const val CHANNEL_NAME = "dicoding channel"
    }

    private var resulStatus : Result? = null

    override fun doWork(): Result {
        return getClosestEvent()
    }

    private fun getClosestEvent(): Result{
        val client = ApiConfig.getApiService().getclosestEvent()
        client.enqueue(object : Callback<EventResponse>{
            override fun onResponse(call: Call<EventResponse>, response: Response<EventResponse>) {
                try {
                    val responseBody = response.body()
                    val title = "Upcoming Event : ${responseBody?.listEvents?.get(0)?.name}"
                    val message = "Catat Tanggalnya !!! : ${responseBody?.listEvents?.get(0)?.beginTime}"
                    showNotification(applicationContext, title, message)
                } catch (e: Exception){
                    showNotification(applicationContext, "Exception", e.message.toString())
                    Log.d("Exception", e.message.toString())
                    resulStatus = Result.failure()
                }
            }

            override fun onFailure(call: Call<EventResponse>, t: Throwable) {
                showNotification(applicationContext, "Gagal mendapatkan Event", t.message.toString())
                Log.d("Failure", t.message.toString())
                resulStatus = Result.failure()
            }

        })
        return resulStatus as Result
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun showNotification(applicationContext: Context, title: String, message: String?) {
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notification: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, CHANNEL_ID)
            .setSmallIcon(R.drawable.baseline_notifications_active_24)
            .setContentTitle(title)
            .setContentText(message)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            notification.setChannelId(CHANNEL_ID)
            notificationManager.createNotificationChannel(channel)
        }
        notificationManager.notify(NOTIFICATION_ID, notification.build())
    }

}