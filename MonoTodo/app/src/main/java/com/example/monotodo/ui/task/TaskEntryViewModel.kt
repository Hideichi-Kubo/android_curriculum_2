package com.example.monotodo.ui.task

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monotodo.data.Task
import com.example.monotodo.data.TasksRepository
import kotlinx.coroutines.launch

class TaskEntryViewModel(private val tasksRepository: TasksRepository) : ViewModel() {

    var taskUiState by mutableStateOf(TaskUiState())
        private set

    private fun validateInput(uiState: TaskDetails = taskUiState.taskDetails): Boolean {
        return with(uiState) {
            title.isNotBlank() && title.length <= 80
        }
    }

    fun updateUiState(taskDetails: TaskDetails) {
        taskUiState =
            TaskUiState(taskDetails = taskDetails, isEntryValid = validateInput(taskDetails))
    }

    fun saveTask() {
        if (validateInput()) {
            viewModelScope.launch {
                tasksRepository.insertTask(taskUiState.taskDetails.toTask())
            }
        }
    }
}

data class TaskUiState(
    val taskDetails: TaskDetails = TaskDetails(),
    val isEntryValid: Boolean = false
)

data class TaskDetails(
    val id: Int = 0,
    val title: String = "",
    val isCompleted: Boolean = false
)

fun TaskDetails.toTask(): Task = Task(
    id = id,
    title = title,
    isCompleted = isCompleted
)
