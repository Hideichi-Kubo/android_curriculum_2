package com.example.monotodo.ui.task

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monotodo.data.MeigenRepository
import com.example.monotodo.data.Task
import com.example.monotodo.data.TasksRepository
import com.example.monotodo.network.Meigen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class TaskCompletedViewModel(
    private val tasksRepository: TasksRepository,
    private val meigenRepository: MeigenRepository
) : ViewModel() {

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

    private val _taskCompletedMeigenUiState = MutableStateFlow<TaskCompletedMeigenUiState>(TaskCompletedMeigenUiState.Loading)
    val taskCompletedMeigenUiState: StateFlow<TaskCompletedMeigenUiState> = _taskCompletedMeigenUiState.asStateFlow()

    init {
        fetchRandomMeigen()
    }

    fun deleteTask(task: Task) {
        viewModelScope.launch {
            tasksRepository.deleteTask(task)
        }
    }

    fun toggleTaskCompletion(task: Task, isCompleted: Boolean) {
        viewModelScope.launch {
            val updatedTask = task.copy(isCompleted = isCompleted)
            tasksRepository.updateTask(updatedTask)
        }
    }

    private fun fetchRandomMeigen() {
        _taskCompletedMeigenUiState.value = TaskCompletedMeigenUiState.Loading
        viewModelScope.launch {
            val meigens: List<Meigen> = meigenRepository.getRandomMeigenFromApiOrLocal()
            val meigen: Meigen? = meigens.firstOrNull()
            if (meigen != null) {
                _taskCompletedMeigenUiState.value = TaskCompletedMeigenUiState.Success(meigen)
            } else {
                _taskCompletedMeigenUiState.value = TaskCompletedMeigenUiState.Error
            }
        }
    }
}

data class TaskCompletedUiState(
    val taskList: List<Task> = listOf()
)

sealed interface TaskCompletedMeigenUiState {
    object Loading : TaskCompletedMeigenUiState
    data class Success(val meigen: Meigen) : TaskCompletedMeigenUiState
    object Error : TaskCompletedMeigenUiState
}
