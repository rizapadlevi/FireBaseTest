package com.example.firebasetest.data.datasource

import com.example.firebasetest.data.Room
import com.example.firebasetest.utils.ResultState
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.UUID

class FirebaseRoomDataSource(private val firestore: FirebaseFirestore) {

    private val db = FirebaseFirestore.getInstance()
    private val roomsCollection = db.collection("rooms")

    suspend fun createRoom(name: String): ResultState<Unit> = try {
        val roomId = UUID.randomUUID().toString() // Generate a UUID
        val room = Room(id = roomId, name = name) // Use the generated ID
        roomsCollection.document(roomId).set(room).await() // Set the document with the generated ID
        ResultState.Success(Unit)
    } catch (e: Exception) {
        ResultState.Error(e)
    }

    suspend fun getRooms(): ResultState<List<Room>> = try {
        val snapshot = firestore.collection("rooms").get().await()
        val rooms = snapshot.documents.mapNotNull{ doc ->
            val room = doc.toObject(Room::class.java)
            room?.copy(id = doc.id)
        }
        ResultState.Success(rooms)
    }   catch (e:Exception){
        ResultState.Error(e)
    }

   /*
    suspend fun getMessages(roomId: String): List<Message> {
        return try {
            val snapshot = Firebase.firestore
                .collection("rooms")
                .document(roomId)
                .collection("messages")
                .orderBy("timestamp") // assuming you sort by timestamp
                .get()
                .await()

            snapshot.documents.mapNotNull { it.toObject(Message::class.java) }
        } catch (e: Exception) {
            emptyList()
        }
    }
    *
    */
}