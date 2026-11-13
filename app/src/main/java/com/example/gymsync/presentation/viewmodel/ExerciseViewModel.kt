package com.example.gymsync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gymsync.domain.model.Exercise
import com.example.gymsync.domain.model.MuscleGroup
import com.example.gymsync.domain.repository.GymSyncRepository

/**
 * ViewModel managing exercises list, search filtering, and CRUD operations.
 */
class ExerciseViewModel(
    private val repository: GymSyncRepository
) : ViewModel() {

    private var currentSearchQuery: String = ""
    private var currentMuscleGroup: MuscleGroup = MuscleGroup.ALL

    fun getExercises(): List<Exercise> {
        return repository.searchExercises(currentSearchQuery, currentMuscleGroup)
    }

    fun setSearchQuery(query: String): List<Exercise> {
        currentSearchQuery = query
        return getExercises()
    }

    fun setSelectedMuscleGroup(muscleGroup: MuscleGroup): List<Exercise> {
        currentMuscleGroup = muscleGroup
        return getExercises()
    }

    fun addExercise(exercise: Exercise) {
        repository.addExercise(exercise)
    }

    fun updateExercise(exercise: Exercise) {
        repository.updateExercise(exercise)
    }

    fun deleteExercise(exerciseId: String) {
        repository.deleteExercise(exerciseId)
    }
}
