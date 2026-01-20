package com.cryptosignals.app.worker

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.cryptosignals.app.R
import com.cryptosignals.app.data.ApiService

class NotificationWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        return try {
            val api = ApiService.create()
            val response = api.getSignals(limit = 1)
            
            if (response.signals.isNotEmpty()) {
                val signal = response.signals[0]
                // Notify if high score
                if (signal.score >= 80) {
                    val disclaimer = applicationContext.getString(R.string.notification_disclaimer)
                    val contentText = "${signal.rating} - Score: ${signal.score}\n$disclaimer"
                    
                    showNotification(
                        "Strong Signal: ${signal.symbol}", 
                        contentText
                    )
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    private fun showNotification(title: String, message: String) {
        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "crypto_signals_channel"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = applicationContext.getString(R.string.notification_channel_name)
            val channelDesc = applicationContext.getString(R.string.notification_channel_description)
            val channel = NotificationChannel(
                channelId,
                channelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = channelDesc
            }
            manager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setStyle(NotificationCompat.BigTextStyle().bigText(message)) // Support multiline text
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()

        manager.notify(System.currentTimeMillis().toInt(), notification)
    }
}
