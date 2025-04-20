package com.example.firebasetest

/*

import android.app.Application
import androidx.work.Constraints

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.firebasetest.utils.MessageSyncWorker
import java.util.concurrent.TimeUnit

class MessageSync : Application() {
    override fun onCreate() {
        super.onCreate()
        scheduleMessageSync()
    }

    private fun scheduleMessageSync() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<MessageSyncWorker>(
            repeatInterval = 15, // Run every 15 minutes (minimum allowed)
            repeatIntervalTimeUnit = TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .build()

        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork(
            "MessageSync",
            ExistingPeriodicWorkPolicy.KEEP, // Keep existing work if it exists
            syncRequest
        )
    }
}

 */