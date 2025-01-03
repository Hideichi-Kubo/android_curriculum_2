package com.example.monotodo.data

import android.content.Context

interface AppContainer {
    val tasksRepository: TasksRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    override val tasksRepository: TasksRepository by lazy {
        DefaultTasksRepository(MonoTodoDatabase.getDatabase(context).taskDao())
    }
}