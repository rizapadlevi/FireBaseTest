package com.example.firebasetest.data.localData

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageLocalDao {
    @Query("SELECT * FROM messages WHERE roomId = :roomId ORDER BY timestamp ASC")
    suspend fun getMessages(roomId: String): List<MessageLocalData>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessages(message: List<MessageLocalData>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: MessageLocalData)

    @Query("DELETE FROM messages WHERE roomId = :roomId")
    suspend fun deleteMessagesByRoom(roomId: String)

    @Query("SELECT * FROM messages WHERE roomId = :roomId AND sent = 0 ORDER BY timestamp")
    suspend fun getUnsentMessages(roomId: String): List<MessageLocalData>

    @Query("SELECT DISTINCT roomId FROM messages WHERE sent = 0")
    suspend fun getRoomIdsWithUnsentMessages(): List<String>

    @Query("UPDATE messages SET sent = :sent WHERE roomId = :roomId AND id = :messageId")
    suspend fun updateMessageSentStatus(roomId: String, messageId: String, sent: Boolean)

    @Query("SELECT * FROM messages WHERE roomId = :roomId ORDER BY timestamp")
    fun getMessagesForRoom(roomId: String): Flow<List<MessageLocalData>>

    @Query("UPDATE messages SET id = :firebaseId, sent = :sent WHERE roomId = :roomId AND id = :tempMessageId")
    suspend fun updateMessageWithFirebaseId(roomId: String, tempMessageId: String, firebaseId: String, sent: Boolean)
}