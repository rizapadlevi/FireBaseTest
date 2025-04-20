package com.example.firebasetest.utils

import android.content.Context
import android.util.Log

/*
class MessageSyncWorker(appContext: Context, workerParams: androidx.work.WorkerParameters) :
    androidx.work.CoroutineWorker(appContext, workerParams) {

    override suspend fun doWork(): Result {
        val repository = Injection.provideMessageRepository(applicationContext)
        val roomIds = repository.getRoomIdsWithUnsentMessages()
        return try {
            roomIds.forEach { roomId ->
                val unsentMessages = repository.getUnsentMessages(roomId)
                unsentMessages.forEach { message ->
                    try {
                        repository.sendMessageToFirebase(roomId, message)
                        repository.updateLocalMessageSentStatus(roomId, message.id, true)
                    } catch (e: Exception) {
                        Log.e("MessageSyncWorker", "Error sending message: ${e.message}")
                        // Consider more sophisticated retry logic here
                    }
                }
            }
            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }
}

 */