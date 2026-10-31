package com.example.gymsync.data.memory

import com.example.gymsync.domain.model.Exercise
import com.example.gymsync.domain.model.GymBranch
import com.example.gymsync.domain.model.MuscleGroup
import com.example.gymsync.domain.model.ProgressPhoto
import com.example.gymsync.domain.model.Routine
import com.example.gymsync.domain.model.UserProfile
import com.example.gymsync.domain.repository.GymSyncRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

/**
 * Thread-safe in-memory implementation of [GymSyncRepository].
 * Pre-populated with realistic initial data for development and testing.
 */
class MemoryDataRepository : GymSyncRepository {

    private val initialExercises = listOf(
        Exercise(
            id = "e1",
            name = "Bench Press",
            description = "Barbell chest press targeting pectoral muscles and triceps.",
            muscleGroup = MuscleGroup.CHEST,
            sets = 4,
            reps = 10,
            weightKg = 70.0
        ),
        Exercise(
            id = "e2",
            name = "Barbell Squat",
            description = "Deep squat exercise targeting quadriceps, glutes, and core.",
            muscleGroup = MuscleGroup.LEGS,
            sets = 4,
            reps = 12,
            weightKg = 90.0
        ),
        Exercise(
            id = "e3",
            name = "Conventional Deadlift",
            description = "Compound lift targeting posterior chain, hamstrings, and lower back.",
            muscleGroup = MuscleGroup.BACK,
            sets = 3,
            reps = 8,
            weightKg = 110.0
        ),
        Exercise(
            id = "e4",
            name = "Pull-Ups",
            description = "Bodyweight upper body pulling exercise targeting latissimus dorsi.",
            muscleGroup = MuscleGroup.BACK,
            sets = 4,
            reps = 10,
            weightKg = 0.0
        ),
        Exercise(
            id = "e5",
            name = "Barbell Bicep Curl",
            description = "Standing bicep isolation curl with straight barbell.",
            muscleGroup = MuscleGroup.ARMS,
            sets = 3,
            reps = 12,
            weightKg = 25.0
        ),
        Exercise(
            id = "e6",
            name = "Overhead Shoulder Press",
            description = "Standing overhead barbell press targeting anterior deltoids.",
            muscleGroup = MuscleGroup.SHOULDERS,
            sets = 4,
            reps = 10,
            weightKg = 40.0
        ),
        Exercise(
            id = "e7",
            name = "Abdominal Plank",
            description = "Isometric core stability exercise.",
            muscleGroup = MuscleGroup.ABS,
            sets = 3,
            reps = 60,
            weightKg = 0.0
        ),
        Exercise(
            id = "e8",
            name = "HIIT Treadmill Sprint",
            description = "High intensity interval running on treadmill.",
            muscleGroup = MuscleGroup.CARDIO,
            sets = 5,
            reps = 1,
            weightKg = 0.0
        )
    )

    private val initialRoutines = listOf(
        Routine(
            id = "r1",
            name = "Legs & Core",
            description = "Lower body power and core stability workout.",
            exercises = listOf(initialExercises[1], initialExercises[6]),
            difficultyLevel = "Intermediate"
        ),
        Routine(
            id = "r2",
            name = "Upper Body Power",
            description = "Chest, back, and arm strength routine.",
            exercises = listOf(initialExercises[0], initialExercises[2], initialExercises[3], initialExercises[4]),
            difficultyLevel = "Advanced"
        )
    )

    private val initialProgressPhotos = listOf(
        ProgressPhoto(
            id = "p1",
            photoUri = "android.resource://com.example.gymsync/drawable/ic_launcher_background",
            weightKg = 75.5,
            notes = "Starting month 1 progress photo."
        ),
        ProgressPhoto(
            id = "p2",
            photoUri = "android.resource://com.example.gymsync/drawable/ic_launcher_background",
            weightKg = 74.0,
            notes = "End of month 1 progress update."
        )
    )

    private val initialGymBranches = listOf(
        GymBranch(
            id = "g1",
            name = "GymSync Central",
            address = "Av. Central 123, San José",
            latitude = 9.9333,
            longitude = -84.0833,
            distanceKm = 1.2,
            isOpen = true,
            openingHours = "6:00 AM - 10:00 PM"
        ),
        GymBranch(
            id = "g2",
            name = "GymSync North Branch",
            address = "Calle 45 Norte, Heredia",
            latitude = 9.9981,
            longitude = -84.1197,
            distanceKm = 4.5,
            isOpen = true,
            openingHours = "5:00 AM - 11:00 PM"
        )
    )

    private val initialUserProfile = UserProfile(
        id = "u1",
        name = "Jordan Alban Alvarez Vega",
        email = "jordan@gymsync.com",
        streakDays = 5,
        activeRoutineName = "Legs & Core"
    )

    private val exercisesFlow = MutableStateFlow(initialExercises)
    private val routinesFlow = MutableStateFlow(initialRoutines)
    private val progressPhotosFlow = MutableStateFlow(initialProgressPhotos)
    private val gymBranchesFlow = MutableStateFlow(initialGymBranches)
    private val userProfileFlow = MutableStateFlow(initialUserProfile)

    // --- Exercises ---
    override fun getExercises(): Flow<List<Exercise>> = exercisesFlow.asStateFlow()

    override suspend fun getExerciseById(id: String): Exercise? {
        return exercisesFlow.value.find { it.id == id }
    }

    override suspend fun addExercise(exercise: Exercise) {
        val newExercise = if (exercise.id.isBlank()) exercise.copy(id = UUID.randomUUID().toString()) else exercise
        exercisesFlow.value = exercisesFlow.value + newExercise
    }

    override suspend fun updateExercise(exercise: Exercise) {
        exercisesFlow.value = exercisesFlow.value.map { existing ->
            if (existing.id == exercise.id) exercise else existing
        }
    }

    override suspend fun deleteExercise(id: String) {
        exercisesFlow.value = exercisesFlow.value.filterNot { it.id == id }
    }

    override suspend fun searchExercises(query: String, muscleGroup: MuscleGroup): List<Exercise> {
        return exercisesFlow.value.filter { exercise ->
            val matchesQuery = query.isBlank() ||
                    exercise.name.contains(query, ignoreCase = true) ||
                    exercise.description.contains(query, ignoreCase = true)
            val matchesCategory = muscleGroup == MuscleGroup.ALL || exercise.muscleGroup == muscleGroup
            matchesQuery && matchesCategory
        }
    }

    // --- Routines ---
    override fun getRoutines(): Flow<List<Routine>> = routinesFlow.asStateFlow()

    override suspend fun getRoutineById(id: String): Routine? {
        return routinesFlow.value.find { it.id == id }
    }

    override suspend fun addRoutine(routine: Routine) {
        val newRoutine = if (routine.id.isBlank()) routine.copy(id = UUID.randomUUID().toString()) else routine
        routinesFlow.value = routinesFlow.value + newRoutine
    }

    override suspend fun updateRoutine(routine: Routine) {
        routinesFlow.value = routinesFlow.value.map { existing ->
            if (existing.id == routine.id) routine else existing
        }
    }

    override suspend fun deleteRoutine(id: String) {
        routinesFlow.value = routinesFlow.value.filterNot { it.id == id }
    }

    // --- Progress Photos ---
    override fun getProgressPhotos(): Flow<List<ProgressPhoto>> = progressPhotosFlow.asStateFlow()

    override suspend fun addProgressPhoto(photo: ProgressPhoto) {
        val newPhoto = if (photo.id.isBlank()) photo.copy(id = UUID.randomUUID().toString()) else photo
        progressPhotosFlow.value = progressPhotosFlow.value + newPhoto
    }

    override suspend fun deleteProgressPhoto(id: String) {
        progressPhotosFlow.value = progressPhotosFlow.value.filterNot { it.id == id }
    }

    // --- Gym Branches ---
    override fun getGymBranches(): Flow<List<GymBranch>> = gymBranchesFlow.asStateFlow()

    override suspend fun getGymBranchById(id: String): GymBranch? {
        return gymBranchesFlow.value.find { it.id == id }
    }

    // --- User Profile ---
    override fun getUserProfile(): Flow<UserProfile> = userProfileFlow.asStateFlow()

    override suspend fun updateUserProfile(profile: UserProfile) {
        userProfileFlow.value = profile
    }
}
