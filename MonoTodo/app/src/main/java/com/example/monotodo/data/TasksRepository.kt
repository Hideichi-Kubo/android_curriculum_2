package com.example.monotodo.data

import kotlinx.coroutines.flow.Flow

interface TasksRepository {
    suspend fun insertTask(task: Task)

    suspend fun updateTask(task: Task)

    suspend fun deleteTask(task: Task)

    fun getIncompleteTasks(): Flow<List<Task>>

    fun getCompletedTasks(): Flow<List<Task>>
}