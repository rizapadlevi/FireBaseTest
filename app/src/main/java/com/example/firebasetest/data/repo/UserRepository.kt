package com.example.firebasetest.data.repo

import com.example.firebasetest.data.User
import com.example.firebasetest.data.datasource.FirebaseUserDataSource
import com.example.firebasetest.utils.ResultState

class UserRepository(private val dataSource: FirebaseUserDataSource) {
    suspend fun getCurrentUser(): ResultState<User> = dataSource.getCurrentUser()
    suspend fun createUser(user: User): ResultState<Unit> = dataSource.createUser(user)
}