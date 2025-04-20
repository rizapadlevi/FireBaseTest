package com.example.firebasetest.data.repo


import com.example.firebasetest.data.Message
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import com.example.firebasetest.utils.ResultState
import com.google.firebase.Timestamp
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.Date

class MessageRepository(private val fireStore:FirebaseFirestore) {


    suspend fun sendMessage(roomId:String, message: Message): ResultState<Unit> = try {
        val messageData = hashMapOf(
            "text" to message.text,
            "senderFirstName" to message.senderFirstName,
            "timestamp" to Timestamp(Date(message.timestamp)),
            "senderId" to message.senderId,
        )
        fireStore.collection("rooms").document(roomId)
            .collection("messages").add(messageData).await()
        ResultState.Success(Unit)
    } catch (e:Exception){
        ResultState.Error(e)
    }

    fun getChatMessage(roomId: String): Flow<List<Message>> = callbackFlow {
        val subscription = fireStore.collection("rooms").document(roomId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { querySnapshot, _ ->
                querySnapshot?.let { snapshot ->
                    val messages = snapshot.documents.mapNotNull { doc ->
                        try {
                            val data = doc.data ?: return@mapNotNull null
                            Message(
                                id = doc.id,
                                text = data["text"] as? String ?: "",
                                senderFirstName = data["senderFirstName"] as? String ?: "",
                                timestamp = (data["timestamp"] as? Timestamp)?.toDate()?.time
                                    ?: System.currentTimeMillis(),
                                senderId = data["senderId"] as? String ?: "",
                            )
                        } catch (e: Exception) {
                            null
                        }
                    }
                    trySend(messages).isSuccess
                    /*
                    launch {
                        try {
                            // Update local database, avoiding duplicates
                            val existingMessages = localDao.getMessages(roomId)
                            val existingIds = existingMessages.map { it.id }.toSet()
                            val newOrUpdatedMessages = messages.map { it.toLocal(roomId) }
                                .map { localMessage ->
                                    if (existingIds.contains(localMessage.id)) {
                                        // Update existing message
                                        localMessage
                                    } else {
                                        // Insert new message
                                        localMessage
                                    }
                                }
                            localDao.insertMessages(newOrUpdatedMessages)
                        } catch (e: Exception) {
                            println("Failed to cache messages locally: ${e.message}")
                        }
                    }
                     */
                }
            }
        awaitClose { subscription.remove() }
    }



    /*

    fun getLocalChatMessages(roomId: String): Flow<List<Message>> =
        localDao.getMessagesForRoom(roomId).map { localMessages ->
            localMessages.map { it.toMessage() }
        }

    suspend fun saveLocalMessage(roomId: String, message: Message) {
        localDao.insertMessage(message.copy(id = com.android.identity.util.UUID.randomUUID().toString(), sent = false).toLocal(roomId))
    }

    suspend fun updateLocalMessageSentStatus(roomId: String, messageId: String, sent: Boolean) {
        localDao.updateMessageSentStatus(roomId, messageId, sent)
    }

    suspend fun getRoomIdsWithUnsentMessages(): List<String> {
        return localDao.getRoomIdsWithUnsentMessages()
    }

    suspend fun getUnsentMessages(roomId: String): List<Message> {
        return localDao.getUnsentMessages(roomId).map { it.toMessage() }
    }

    suspend fun updateLocalMessageWithFirebaseId(roomId: String, tempMessageId: String, firebaseId: String, sent: Boolean) {
        localDao.updateMessageWithFirebaseId(roomId, tempMessageId, firebaseId, sent)
    }

    suspend fun sendMessageToFirebase(roomId: String, message: Message): String {
        return try {
            val messageData = hashMapOf(
                "text" to message.text,
                "senderFirstName" to message.senderFirstName,
                "timestamp" to Timestamp(Date(message.timestamp)),
                "senderId" to message.senderId,
                "isSentByCurrentUser" to message.isSentByCurrentUser
            )
            val documentReference = fireStore.collection("rooms").document(roomId)
                .collection("messages").add(messageData).await()
            documentReference.id // Return the Firebase-generated ID
        } catch (e: Exception) {
            throw e // Re-throw the exception to be handled in the ViewModel
        }
    }

     */

}
