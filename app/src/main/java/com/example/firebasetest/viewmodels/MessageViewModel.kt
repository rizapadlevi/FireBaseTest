package com.example.firebasetest.viewmodels

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.firebasetest.data.Message
import com.example.firebasetest.data.User
import com.example.firebasetest.data.repo.MessageRepository
import com.example.firebasetest.data.repo.UserRepository
import com.example.firebasetest.utils.Injection
import com.example.firebasetest.utils.ResultState
import kotlinx.coroutines.launch

class MessageViewModel(private val roomId: String, private val context: Context) : ViewModel() {
    private val _messages = MutableLiveData<List<Message>>()
    val messages: LiveData<List<Message>> get() = _messages

    //private val _roomId = MutableLiveData<String>()
    private val _currentUser = MutableLiveData<User>()
    val currentUser: LiveData<User> get() = _currentUser

    private val messageRepository: MessageRepository
    private val userRepository: UserRepository


    init{
        messageRepository= Injection.provideMessageRepository(context)
        userRepository=UserRepository(Injection.provideUserDataStore())
        //_roomId.value = roomId
        loadCurrentUser()
        loadMessages()
    }
    private fun loadCurrentUser() {
        viewModelScope.launch {
            when (val result = userRepository.getCurrentUser()) {
                is ResultState.Success -> _currentUser.value = result.data
                is ResultState.Error -> {
                }
                is ResultState.Loading -> {
                }
            }
        }
    }
    fun loadMessages() {
        viewModelScope.launch {
            messageRepository.getChatMessage(roomId).collect { messages ->
                _messages.value = messages
            }
        }
    }
    fun sendMessage(text: String) {
        viewModelScope.launch {
            val currentUser = currentUser.value ?: return@launch
            val roomId = roomId ?: return@launch
            val message = Message(
                id = "", // Firebase will generate the ID
                text = text,
                senderFirstName = currentUser.firstName,
                senderId = currentUser.email,
                timestamp = System.currentTimeMillis(),
            )
            try {
                messageRepository.sendMessage(roomId, message)
            } catch (e: Exception) {
                Log.e("SendMessage", "Error sending message: ${e.message}")
                // Optionally, display an error to the user
            }
        }
    }

    /*
    fun setRoomId(roomId: String) {
        _roomId.value = roomId
        loadMessages()
    }

     */

}

class MessageViewModelFactory(private val roomId: String, private val context: Context) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MessageViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MessageViewModel(roomId, context) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

