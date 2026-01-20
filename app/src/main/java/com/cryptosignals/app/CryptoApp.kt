package com.cryptosignals.app

import android.app.Application
import com.cryptosignals.app.worker.NotificationWorker
import java.util.concurrent.TimeUnit
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.ExistingPeriodicWorkPolicy

class CryptoApp : Application() {
    override fun onCreate() {
        super.onCreate()
        setupWorker()
    }

    private fun setupWorker() {
        // Run every 15 minutes
        val workRequest = PeriodicWorkRequestBuilder<NotificationWorker>(15, TimeUnit.MINUTES)
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "SignalCheck",
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}
