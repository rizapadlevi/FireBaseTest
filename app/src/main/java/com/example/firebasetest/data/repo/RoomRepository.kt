package com.example.firebasetest.data.repo

import com.example.firebasetest.data.datasource.FirebaseRoomDataSource
import com.example.firebasetest.utils.ResultState
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class RoomRepository(private val dataSource: FirebaseRoomDataSource, private val fireStore: FirebaseFirestore) {
    suspend fun createRoom(name:String)=dataSource.createRoom(name)
    suspend fun getRooms()=dataSource.getRooms()
    suspend fun deleteRoom(roomId: String): ResultState<Unit> = try {
        fireStore.collection("rooms").document(roomId).delete().await()
        ResultState.Success(Unit)
    } catch (e: Exception) {
        ResultState.Error(e)
    }
    suspend fun updateRoomName(roomId: String, newName: String): ResultState<Unit> = try {
        val roomRef = fireStore.collection("rooms").document(roomId)
        val updateData = hashMapOf(
            "name" to newName,
            "lastUpdated" to FieldValue.serverTimestamp()
        )
        roomRef.update(updateData).await()
        ResultState.Success(Unit)
    } catch (e: Exception) {
        ResultState.Error(e)
    }
}