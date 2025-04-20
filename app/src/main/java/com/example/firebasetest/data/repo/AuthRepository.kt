package com.example.firebasetest.data.repo

import com.example.firebasetest.data.User
import com.example.firebasetest.utils.ResultState
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class AuthRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun login(email: String, password: String): ResultState<User> {
        return try {
            val result = auth.signInWithEmailAndPassword(email,password).await()
            val uid = result.user?.uid ?: throw Exception("User ID Not Found")
            val snapshot = firestore.collection("users").document(uid).get().await()
            val user = snapshot.toObject(User::class.java) ?: throw Exception("User Not Found")
            ResultState.Success(user)

        } catch (e: Exception) {
            ResultState.Error(e)
        }
    }

    suspend fun signUp(email: String, password: String, firstName: String, lastName: String): ResultState<User> {
        return try {
            val result = auth.createUserWithEmailAndPassword(email, password).await()
            val uid = result.user?.uid ?: throw Exception("User ID not found")
            val newUser = User(uid, email, password, firstName, lastName) // Removed password storage
            firestore.collection("users").document(uid).set(newUser).await()
            ResultState.Success(newUser)
        } catch (e: Exception) {
            ResultState.Error(e)
        }
    }

        fun getCurrentUserId(): String? {
            return auth.currentUser?.uid
        }

        fun logout(){
            auth.signOut()
        }

        fun isLoggedIn(): Boolean {
            return auth.currentUser != null
        }
}