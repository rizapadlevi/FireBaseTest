package com.example.firebasetest.data.localData

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [MessageLocalData::class], version = 1)
abstract class MessageLocalDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageLocalDao

    companion object {
        @Volatile
        private var INSTANCE: MessageLocalDatabase? = null

        fun getDatabase(context: Context): MessageLocalDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MessageLocalDatabase::class.java,
                    "chat_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}