package com.example.monotodo.data

import kotlinx.coroutines.flow.Flow

class DefaultTasksRepository(private val taskDao: TaskDao) : TasksRepository {
    override suspend fun insertTask(task: Task) = taskDao.insert(task)

    override suspend fun updateTask(task: Task) = taskDao.update(task)

    override suspend fun deleteTask(task: Task) = taskDao.delete(task)

    override fun getIncompleteTasks(): Flow<List<Task>> = taskDao.getIncompleteTasks()

    override fun getCompletedTasks(): Flow<List<Task>> = taskDao.getCompletedTasks()
}