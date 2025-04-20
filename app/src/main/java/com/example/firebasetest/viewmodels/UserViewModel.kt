package com.example.firebasetest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasetest.data.User
import com.example.firebasetest.utils.Injection.provideUserRepository
import com.example.firebasetest.utils.ResultState
import kotlinx.coroutines.launch

class UserViewModel:ViewModel() {
    private val userRepository = provideUserRepository()

    fun createUser(user: User) {
        viewModelScope.launch {
            when (val result = userRepository.createUser(user)) {
                is ResultState.Success -> { /* success logic */ }
                is ResultState.Error -> { /* handle error */ }
                is ResultState.Loading -> {}
            }
        }
    }
}