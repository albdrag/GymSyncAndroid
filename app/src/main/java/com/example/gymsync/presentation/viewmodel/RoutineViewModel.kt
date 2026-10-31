package com.example.gymsync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymsync.domain.model.Routine
import com.example.gymsync.domain.repository.GymSyncRepository
import com.example.gymsync.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel managing workout routines and routine management actions.
 */
class RoutineViewModel(
    private val repository: GymSyncRepository
) : ViewModel() {

    private val _routinesState = MutableStateFlow<UiState<List<Routine>>>(UiState.Loading)
    val routinesState: StateFlow<UiState<List<Routine>>> = _routinesState.asStateFlow()

    init {
        loadRoutines()
    }

    fun loadRoutines() {
        viewModelScope.launch {
            _routinesState.value = UiState.Loading
            try {
                repository.getRoutines().collect { routines ->
                    _routinesState.value = UiState.Success(routines)
                }
            } catch (e: Exception) {
                _routinesState.value = UiState.Error(e.localizedMessage ?: "Failed to load routines.")
            }
        }
    }

    fun addRoutine(routine: Routine) {
        viewModelScope.launch {
            try {
                repository.addRoutine(routine)
                loadRoutines()
            } catch (e: Exception) {
                _routinesState.value = UiState.Error(e.localizedMessage ?: "Failed to add routine.")
            }
        }
    }

    fun updateRoutine(routine: Routine) {
        viewModelScope.launch {
            try {
                repository.updateRoutine(routine)
                loadRoutines()
            } catch (e: Exception) {
                _routinesState.value = UiState.Error(e.localizedMessage ?: "Failed to update routine.")
            }
        }
    }

    fun deleteRoutine(routineId: String) {
        viewModelScope.launch {
            try {
                repository.deleteRoutine(routineId)
                loadRoutines()
            } catch (e: Exception) {
                _routinesState.value = UiState.Error(e.localizedMessage ?: "Failed to delete routine.")
            }
        }
    }
}
