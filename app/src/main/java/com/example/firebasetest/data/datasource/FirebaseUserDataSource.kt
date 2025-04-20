package com.example.firebasetest.data.datasource

import com.example.firebasetest.data.User
import com.example.firebasetest.utils.ResultState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await


class FirebaseUserDataSource(private val auth: FirebaseAuth,
                             private val firestore: FirebaseFirestore)
                            : UserDataSource
{
    override suspend fun getCurrentUser(): ResultState<User> {
        return try {
            val uid = auth.currentUser?.uid ?: return ResultState.Error(Exception("User not logged in"))
            val snapshot = firestore.collection("users").document(uid).get().await()
            val user = snapshot.toObject(User::class.java) ?: throw Exception("User not found")
            ResultState.Success(user)
        } catch (e:Exception){
            ResultState.Error(e)
        }
    }

    override suspend fun createUser(user: User): ResultState<Unit> = try {
        firestore.collection("users").document(user.userId).set(user).await()
        ResultState.Success(Unit)
    } catch (e: Exception) {
        ResultState.Error(e)
    }

}