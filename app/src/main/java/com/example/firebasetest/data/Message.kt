package com.example.firebasetest.data

import com.google.firebase.firestore.Exclude

data class Message(
    val id:String = "",
    val text: String = "",
    val senderFirstName: String = "",
    val timestamp: Long = System.currentTimeMillis(),
    val senderId: String = "",
    //val isSentByCurrentUser: Boolean = false,
    //@get:Exclude var sent: Boolean = true // Transient property, not stored in Firestore
){
   // constructor() : this("", "", "", System.currentTimeMillis(), "", false)
}

/*

fun Message.toLocal(roomId: String): MessageLocalData {
    return MessageLocalData(
        id = this.id,
        roomId = roomId,
        text = this.text,
        senderFirstName = this.senderFirstName,
        timestamp = this.timestamp,
        senderId = this.senderId,
        isSentByCurrentUser = this.isSentByCurrentUser,
        sent = this.sent
    )
}

fun MessageLocalData.toMessage(): Message {
    return Message(
        id = this.id,
        text = this.text,
        senderFirstName = this.senderFirstName,
        timestamp = this.timestamp,
        senderId = this.senderId,
        isSentByCurrentUser = this.isSentByCurrentUser,
        sent = this.sent
    )
}

 */
