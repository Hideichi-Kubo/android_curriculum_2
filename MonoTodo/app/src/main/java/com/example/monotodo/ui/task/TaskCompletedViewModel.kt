package com.example.monotodo.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monotodo.data.Task
import com.example.monotodo.data.TasksRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TaskCompletedViewModel(private val tasksRepository: TasksRepository) : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val taskCompletedUiState: StateFlow<TaskCompletedUiState> = tasksRepository.getCompletedTasks()
        .map { TaskCompletedUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = TaskCompletedUiState()
        )

    suspend fun deleteTask(task: Task) {
        tasksRepository.deleteTask(task)
    }

    suspend fun toggleTaskCompletion(task: Task, isCompleted: Boolean) {
        val updatedTask = task.copy(isCompleted = isCompleted)
        tasksRepository.updateTask(updatedTask)
    }
}

data class TaskCompletedUiState(
    val taskList: List<Task> = listOf()
)