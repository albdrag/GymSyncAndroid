package com.example.gymsync.presentation.viewmodel

import androidx.lifecycle.ViewModel
import com.example.gymsync.domain.model.Routine
import com.example.gymsync.domain.repository.GymSyncRepository

/**
 * ViewModel managing workout routines.
 */
class RoutineViewModel(
    private val repository: GymSyncRepository
) : ViewModel() {

    fun getRoutines(): List<Routine> {
        return repository.getRoutines()
    }

    fun addRoutine(routine: Routine) {
        repository.addRoutine(routine)
    }

    fun updateRoutine(routine: Routine) {
        repository.updateRoutine(routine)
    }

    fun deleteRoutine(routineId: String) {
        repository.deleteRoutine(routineId)
    }
}
