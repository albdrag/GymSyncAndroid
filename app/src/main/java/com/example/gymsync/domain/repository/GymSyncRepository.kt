package com.example.gymsync.domain.repository

import com.example.gymsync.domain.model.Exercise
import com.example.gymsync.domain.model.GymBranch
import com.example.gymsync.domain.model.MuscleGroup
import com.example.gymsync.domain.model.ProgressPhoto
import com.example.gymsync.domain.model.Routine
import com.example.gymsync.domain.model.UserProfile

/**
 * Domain repository interface defining data operations for GymSync application.
 */
interface GymSyncRepository {

    // --- Exercise Operations ---
    fun getExercises(): List<Exercise>
    fun getExerciseById(id: String): Exercise?
    fun addExercise(exercise: Exercise)
    fun updateExercise(exercise: Exercise)
    fun deleteExercise(id: String)
    fun searchExercises(query: String, muscleGroup: MuscleGroup): List<Exercise>

    // --- Routine Operations ---
    fun getRoutines(): List<Routine>
    fun getRoutineById(id: String): Routine?
    fun addRoutine(routine: Routine)
    fun updateRoutine(routine: Routine)
    fun deleteRoutine(id: String)

    // --- Progress Photo Operations ---
    fun getProgressPhotos(): List<ProgressPhoto>
    fun addProgressPhoto(photo: ProgressPhoto)
    fun deleteProgressPhoto(id: String)

    // --- Gym Branch Operations ---
    fun getGymBranches(): List<GymBranch>
    fun getGymBranchById(id: String): GymBranch?

    // --- User Profile Operations ---
    fun getUserProfile(): UserProfile
    fun updateUserProfile(profile: UserProfile)
}
