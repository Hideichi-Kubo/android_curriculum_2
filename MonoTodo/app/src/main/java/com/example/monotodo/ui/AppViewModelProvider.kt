package com.example.monotodo.ui

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.monotodo.MonoTodoApplication
import com.example.monotodo.ui.home.HomeViewModel
import com.example.monotodo.ui.task.TaskCompletedViewModel
import com.example.monotodo.ui.task.TaskEntryViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            HomeViewModel(
                monoTodoApplication().container.tasksRepository,
                monoTodoApplication().container.meigenRepository,
                monoTodoApplication().container.userPreferencesRepository
            )
        }

        initializer {
            TaskCompletedViewModel(
                monoTodoApplication().container.tasksRepository,
                monoTodoApplication().container.meigenRepository,
                monoTodoApplication().container.userPreferencesRepository
            )
        }

        initializer {
            TaskEntryViewModel(monoTodoApplication().container.tasksRepository)
        }
    }
}

fun CreationExtras.monoTodoApplication(): MonoTodoApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MonoTodoApplication)
