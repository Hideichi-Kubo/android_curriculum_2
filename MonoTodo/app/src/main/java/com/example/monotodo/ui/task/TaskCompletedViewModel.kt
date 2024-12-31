package com.example.monotodo.ui.task

import androidx.lifecycle.ViewModel
import com.example.monotodo.data.Task

class TaskCompletedViewModel : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(
    val taskList: List<Task> = listOf()
)