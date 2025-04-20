package com.example.firebasetest.utils

import android.content.Context
import androidx.room.Room
import com.example.firebasetest.data.datasource.FirebaseUserDataSource
import com.example.firebasetest.data.datasource.FirebaseRoomDataSource
import com.example.firebasetest.data.localData.MessageLocalDao
import com.example.firebasetest.data.localData.MessageLocalDatabase
import com.example.firebasetest.data.repo.AuthRepository
import com.example.firebasetest.data.repo.MessageRepository
import com.example.firebasetest.data.repo.RoomRepository
import com.example.firebasetest.data.repo.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

object Injection {
    private var database : MessageLocalDatabase? = null

    fun provideMessageRepository(context: Context): MessageRepository {
        val firestore = FirebaseFirestore.getInstance()
        //val firebaseRoomDataSource = FirebaseRoomDataSource(firestore)
        //val localDao = provideMessageLocalDao(context)
        return MessageRepository(firestore)
    }

    fun provideFirebaseRoomDataSource(): FirebaseRoomDataSource {
        return FirebaseRoomDataSource(FirebaseFirestore.getInstance())
    }

    fun provideMessageLocalDao(context: Context): MessageLocalDao {
        if (database == null) {
            database = Room.databaseBuilder(
                context.applicationContext,
                MessageLocalDatabase::class.java, "app_database"
            ).build()
        }
        return database!!.messageDao()
    }

    fun instance(): RoomRepository {
        val dataSource = FirebaseRoomDataSource(FirebaseFirestore.getInstance())
        val firestore = FirebaseFirestore.getInstance()
        return RoomRepository(dataSource,firestore)
    }

    fun provideAuthRepository(): AuthRepository {
        val auth = FirebaseAuth.getInstance()
        val firestore = FirebaseFirestore.getInstance()
        return AuthRepository(auth, firestore)
    }

    fun provideUserRepository(): UserRepository {
        val dataSource = FirebaseUserDataSource(
            FirebaseAuth.getInstance(),
            FirebaseFirestore.getInstance()
        )
        return UserRepository(dataSource)
    }

    fun provideUserDataStore(): FirebaseUserDataSource{
        return FirebaseUserDataSource(FirebaseAuth.getInstance(), FirebaseFirestore.getInstance())
    }

    fun provideFirestore(): FirebaseFirestore{
        return FirebaseFirestore.getInstance()
    }
}