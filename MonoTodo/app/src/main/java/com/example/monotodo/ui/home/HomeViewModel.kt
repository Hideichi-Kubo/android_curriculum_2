package com.example.monotodo.ui.home

import androidx.lifecycle.ViewModel
import com.example.monotodo.data.Task

class HomeViewModel : ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(
    val itemList: List<Task> = listOf()
)