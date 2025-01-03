package com.example.monotodo.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Task::class], version = 1, exportSchema = false)
abstract class MonoTodoDatabase : RoomDatabase() {

    abstract fun taskDao(): TaskDao

    companion object {
        @Volatile
        private var Instance: MonoTodoDatabase? = null

        fun getDatabase(context: Context): MonoTodoDatabase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MonoTodoDatabase::class.java, "task_database")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}