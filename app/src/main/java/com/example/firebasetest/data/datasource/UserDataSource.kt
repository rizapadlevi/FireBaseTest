package com.example.firebasetest.data.datasource

import com.example.firebasetest.data.User
import com.example.firebasetest.utils.ResultState

interface UserDataSource{
    suspend fun getCurrentUser(): ResultState<User>
    suspend fun createUser(user: User): ResultState<Unit>
}