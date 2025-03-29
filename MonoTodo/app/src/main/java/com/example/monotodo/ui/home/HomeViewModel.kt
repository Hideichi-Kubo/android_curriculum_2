package com.example.monotodo.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.monotodo.data.MeigenRepository
import com.example.monotodo.data.Task
import com.example.monotodo.data.TasksRepository
import com.example.monotodo.data.UserPreferencesRepository
import com.example.monotodo.network.Meigen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    private val tasksRepository: TasksRepository,
    private val meigenRepository: MeigenRepository,
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    val homeUiState: StateFlow<HomeUiState> = tasksRepository.getIncompleteTasks()
        .map { HomeUiState(it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomeUiState()
        )

    private val _homeMeigenUiState = MutableStateFlow<HomeMeigenUiState>(HomeMeigenUiState.Loading)
    val homeMeigenUiState: StateFlow<HomeMeigenUiState> = _homeMeigenUiState.asStateFlow()

    val homePreferencesUiState: StateFlow<HomePreferencesUiState> =
        userPreferencesRepository.isMeigenDisplayEnabled.map { isMeigenDisplayEnabled ->
            HomePreferencesUiState(isMeigenDisplayEnabled)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = HomePreferencesUiState()
        )

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
        _homeMeigenUiState.value = HomeMeigenUiState.Loading
        viewModelScope.launch {
            val meigens: List<Meigen> = meigenRepository.getRandomMeigenFromApiOrLocal()
            val meigen: Meigen? = meigens.firstOrNull()
            if (meigen != null) {
                _homeMeigenUiState.value = HomeMeigenUiState.Success(meigen)
            } else {
                _homeMeigenUiState.value = HomeMeigenUiState.Error
            }
        }
    }

    fun toggleMeigenDisplayPreference(isMeigenDisplayEnabled: Boolean) {
        viewModelScope.launch {
            userPreferencesRepository.setMeigenDisplayEnabled(!isMeigenDisplayEnabled)
        }
    }
}

data class HomeUiState(
    val itemList: List<Task> = listOf()
)

sealed interface HomeMeigenUiState {
    object Loading : HomeMeigenUiState
    data class Success(val meigen: Meigen) : HomeMeigenUiState
    object Error : HomeMeigenUiState
}

data class HomePreferencesUiState(
    val isMeigenDisplayEnabled: Boolean = true
)
