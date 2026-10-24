package com.example.gymsync.domain.repository

import com.example.gymsync.domain.model.Exercise
import com.example.gymsync.domain.model.GymBranch
import com.example.gymsync.domain.model.MuscleGroup
import com.example.gymsync.domain.model.ProgressPhoto
import com.example.gymsync.domain.model.Routine
import com.example.gymsync.domain.model.UserProfile
import kotlinx.coroutines.flow.Flow

/**
 * Domain repository interface defining data operations for GymSync application.
 */
interface GymSyncRepository {

    // --- Exercise Operations ---
    fun getExercises(): Flow<List<Exercise>>
    suspend fun getExerciseById(id: String): Exercise?
    suspend fun addExercise(exercise: Exercise)
    suspend fun updateExercise(exercise: Exercise)
    suspend fun deleteExercise(id: String)
    suspend fun searchExercises(query: String, muscleGroup: MuscleGroup): List<Exercise>

    // --- Routine Operations ---
    fun getRoutines(): Flow<List<Routine>>
    suspend fun getRoutineById(id: String): Routine?
    suspend fun addRoutine(routine: Routine)
    suspend fun updateRoutine(routine: Routine)
    suspend fun deleteRoutine(id: String)

    // --- Progress Photo Operations ---
    fun getProgressPhotos(): Flow<List<ProgressPhoto>>
    suspend fun addProgressPhoto(photo: ProgressPhoto)
    suspend fun deleteProgressPhoto(id: String)

    // --- Gym Branch Operations ---
    fun getGymBranches(): Flow<List<GymBranch>>
    suspend fun getGymBranchById(id: String): GymBranch?

    // --- User Profile Operations ---
    fun getUserProfile(): Flow<UserProfile>
    suspend fun updateUserProfile(profile: UserProfile)
}
