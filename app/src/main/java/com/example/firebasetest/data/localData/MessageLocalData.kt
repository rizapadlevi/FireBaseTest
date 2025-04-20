package com.example.firebasetest.data.localData

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName="messages")
data class MessageLocalData(
    @PrimaryKey val id:String,
    val roomId:String,
    val text: String = "",
    val senderFirstName: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val senderId: String = "",
    val isSentByCurrentUser: Boolean = false,
    val sent: Boolean = true
)
