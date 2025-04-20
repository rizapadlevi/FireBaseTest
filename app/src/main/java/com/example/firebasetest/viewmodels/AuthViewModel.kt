package com.example.firebasetest.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasetest.data.User
import com.example.firebasetest.data.repo.AuthRepository
import com.example.firebasetest.utils.ResultState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val authRepository: AuthRepository) : ViewModel() {

    private val _authState = MutableStateFlow<ResultState<User>?>(null)
    val authState: StateFlow<ResultState<User>?> = _authState.asStateFlow()

    fun login(email:String, password: String){
        viewModelScope.launch {
            _authState.value = ResultState.Loading
            val result = authRepository.login(email, password)
            _authState.value=result
        }
    }
    fun signUp(email: String, password: String, firstName: String, lastName: String) {
        viewModelScope.launch {
            _authState.value = ResultState.Loading
            val result = authRepository.signUp(email, password, firstName, lastName)
            _authState.value = result
        }
    }

    fun resetAuthState() {
        viewModelScope.launch {
            _authState.value = null
        }
    }

    fun signOut() {
        authRepository.logout()
        resetAuthState()
    }

}