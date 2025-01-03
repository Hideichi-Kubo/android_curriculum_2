package com.example.monotodo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TaskDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(task: Task)

    @Delete
    suspend fun delete(task: Task)

    @Query("SELECT * from tasks WHERE is_completed = 0 ORDER BY id DESC")
    fun getIncompleteTasks(): Flow<List<Task>>

    @Query("SELECT * from tasks WHERE is_completed = 1 ORDER BY id DESC")
    fun getCompletedTasks(): Flow<List<Task>>
}