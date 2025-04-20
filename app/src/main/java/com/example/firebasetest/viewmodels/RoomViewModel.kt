package com.example.firebasetest.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.firebasetest.data.Room
import com.example.firebasetest.data.repo.RoomRepository
import com.example.firebasetest.utils.Injection
import com.example.firebasetest.utils.ResultState
import kotlinx.coroutines.launch

class RoomViewModel : ViewModel() {
    private val _rooms = MutableLiveData<List<Room>>()
    val rooms: LiveData<List<Room>> get() = _rooms
    private val roomRepository: RoomRepository = Injection.instance()
    init {
        loadRooms()
    }

    fun createRoom(name: String) {
        viewModelScope.launch {
            roomRepository.createRoom(name)
            loadRooms()
        }
    }

    fun loadRooms() {
        viewModelScope.launch {
            when (val result = roomRepository.getRooms()) {
                is ResultState.Success -> _rooms.value = result.data
                is ResultState.Error -> {
                    Log.e("LoadRooms", "Error loading rooms: ${result.exception.message}")
                }
                is ResultState.Loading -> {
                    Log.d("LoadRooms", "Loading rooms...")
                }
            }
        }
    }

    fun deleteRoom(roomId: String, onRoomDeleted: () -> Unit) {
        viewModelScope.launch {
            when (val result = roomRepository.deleteRoom(roomId)) {
                is ResultState.Success -> {
                    Log.d("DeleteRoom", "Room deleted successfully: $roomId")
                    onRoomDeleted()
                }
                is ResultState.Error -> {
                    Log.e("DeleteRoom", "Error deleting room: ${result.exception.message}")
                }
                is ResultState.Loading -> {
                    Log.d("DeleteRoom", "Deleting room: $roomId...")
                }
            }
        }
    }

    fun updateRoomName(roomId: String, newName: String, onRoomUpdated: () -> Unit) {
        viewModelScope.launch {
            when (val result = roomRepository.updateRoomName(roomId, newName)) {
                is ResultState.Success -> {
                    Log.d("UpdateRoomName", "Room name updated successfully: $roomId to $newName")
                    onRoomUpdated()
                }
                is ResultState.Error -> {
                    Log.e("UpdateRoomName", "Error updating room name: ${result.exception.message}")

                }
                is ResultState.Loading -> {
                    Log.d("UpdateRoomName", "Updating room name: $roomId to $newName...")
                }
            }
        }
    }


}