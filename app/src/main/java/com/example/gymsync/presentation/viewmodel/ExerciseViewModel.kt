package com.example.gymsync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.gymsync.domain.model.Exercise
import com.example.gymsync.domain.model.MuscleGroup
import com.example.gymsync.domain.repository.GymSyncRepository
import com.example.gymsync.util.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel managing exercises list, search filtering, muscle group selection, and CRUD operations.
 */
class ExerciseViewModel(
    private val repository: GymSyncRepository
) : ViewModel() {

    private val _exercisesState = MutableStateFlow<UiState<List<Exercise>>>(UiState.Loading)
    val exercisesState: StateFlow<UiState<List<Exercise>>> = _exercisesState.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedMuscleGroup = MutableStateFlow(MuscleGroup.ALL)
    val selectedMuscleGroup: StateFlow<MuscleGroup> = _selectedMuscleGroup.asStateFlow()

    init {
        loadExercises()
    }

    fun loadExercises() {
        viewModelScope.launch {
            _exercisesState.value = UiState.Loading
            try {
                repository.getExercises().collect { exercises ->
                    val filtered = filterExercises(exercises, _searchQuery.value, _selectedMuscleGroup.value)
                    _exercisesState.value = UiState.Success(filtered)
                }
            } catch (e: Exception) {
                _exercisesState.value = UiState.Error(e.localizedMessage ?: "Failed to load exercises.")
            }
        }
    }

    fun setSearchQuery(query: String) {
        _searchQuery.value = query
        loadExercises()
    }

    fun setSelectedMuscleGroup(muscleGroup: MuscleGroup) {
        _selectedMuscleGroup.value = muscleGroup
        loadExercises()
    }

    fun addExercise(exercise: Exercise) {
        viewModelScope.launch {
            try {
                repository.addExercise(exercise)
                loadExercises()
            } catch (e: Exception) {
                _exercisesState.value = UiState.Error(e.localizedMessage ?: "Failed to add exercise.")
            }
        }
    }

    fun updateExercise(exercise: Exercise) {
        viewModelScope.launch {
            try {
                repository.updateExercise(exercise)
                loadExercises()
            } catch (e: Exception) {
                _exercisesState.value = UiState.Error(e.localizedMessage ?: "Failed to update exercise.")
            }
        }
    }

    fun deleteExercise(exerciseId: String) {
        viewModelScope.launch {
            try {
                repository.deleteExercise(exerciseId)
                loadExercises()
            } catch (e: Exception) {
                _exercisesState.value = UiState.Error(e.localizedMessage ?: "Failed to delete exercise.")
            }
        }
    }

    private fun filterExercises(
        exercises: List<Exercise>,
        query: String,
        muscleGroup: MuscleGroup
    ): List<Exercise> {
        return exercises.filter { exercise ->
            val matchesQuery = query.isBlank() ||
                    exercise.name.contains(query, ignoreCase = true) ||
                    exercise.description.contains(query, ignoreCase = true)
            val matchesCategory = muscleGroup == MuscleGroup.ALL || exercise.muscleGroup == muscleGroup
            matchesQuery && matchesCategory
        }
    }
}
